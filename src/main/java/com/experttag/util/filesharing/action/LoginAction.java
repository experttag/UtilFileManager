/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.experttag.util.filesharing.action;

import com.experttag.util.filesharing.*;
import com.experttag.util.filesharing.dao.PropertiesDAO;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

/**
 *
 * @author NANDUS
 */
public class LoginAction extends DispatchAction {
    
    private static Logger log = Logger.getLogger(LoginAction.class);
    
    /**
     * This is the Struts action method called on
     * http://.../actionPath?method=myAction1,
     * where "method" is the value specified in <action> element : 
     * ( <action parameter="method" .../> )
     */
    public ActionForward login(ActionMapping mapping, ActionForm  form,
            HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        String user = req.getParameter("user");
        String pass = req.getParameter("pass");

        log.info("start user session for user : " + user);
        Properties fileconfig = (new PropertiesDAO(Constant.FILE_CONFIG)).getFile();
        String root = fileconfig.getProperty("file_root");
        String passwordFile = root + fileconfig.getProperty("file_separator") + Constant.PASS_FILE;

        if (user == null || pass == null) {
            log.error("BAD_USER");
            return mapping.findForward("BAD_USER");
        }

        user = user.trim();
        pass = pass.trim();
        if (user.equals("") || pass.equals("")) {
            log.error("BAD_USER"); 
            return mapping.findForward("BAD_USER");
        }

        // For each request, the user-password map is re-loaded.
        // So, the changes to the user-password map can be reflected without re-starting the server.
        
        PropertiesDAO pdo =new PropertiesDAO(passwordFile,0);
        if(pdo.numberOfKeys()<1)
            pdo = new PropertiesDAO(Constant.PASS_FILE);


        Properties prop = pdo.getFile();

        if (!(prop.containsKey(user))) {
            return mapping.findForward("BAD_USER");
        }
        if (!((prop.getProperty(user)).equals(pass))) {
            return mapping.findForward("BAD_USER");
        }
        

        LoginDir login = null;
        if (user.equalsIgnoreCase("admin")) {
            login = new LoginDir(root);
        } else {
            login = new LoginDir(root, user);
            
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("login", login);
        session.setAttribute("user", user);
        session.setAttribute("userList", prop);

        int maxFile = ParseSave.getMaxFileSize();
        session.setAttribute("maxFile", maxFile + "");

        log.info("end user session for user : " + user);
        return mapping.findForward("LIST_FILE");
    }
    

    /**
     * This is the Struts action method called on
     * http://.../actionPath?method=myAction2,
     * where "method" is the value specified in <action> element : 
     * ( <action parameter="method" .../> )
     */
    public ActionForward logout(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        request.getSession().invalidate();
        return mapping.findForward("index");

    }
}
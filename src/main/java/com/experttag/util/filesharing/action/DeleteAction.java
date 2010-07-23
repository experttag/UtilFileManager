/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.experttag.util.filesharing.action;

import com.experttag.util.filesharing.*;
import java.io.FileNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

/**
 *
 * @author NANDUS
 */
public class DeleteAction extends org.apache.struts.action.Action {

    private static Logger log = Logger.getLogger(DeleteAction.class);
        
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        String file = req.getParameter("file");
        String user = req.getParameter("user");

        log.info("start file deletion (" + file + " by user ("+ user+")");

        if (file == null) {
            return mapping.findForward("INVALID");
        }
        HttpSession session = req.getSession(true);
        if (session.isNew()) {
            return mapping.findForward("NO_SESSION");
        }
        Object obj = session.getAttribute("login");
        if (obj == null) {
            return mapping.findForward("INVALID");
        }
        LoginDir login = (LoginDir) obj;
        try {

            if(user!=null&&!user.equalsIgnoreCase(""))
                login.deleteFile(user,file);
            else
                login.deleteFile(file);

        } catch (FileNotFoundException ex) {
            log.error("Sorry, but the specified file was not found!");
            throw (new ServletException("Sorry, but the specified file was not found!"));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw (new ServletException(ex));
        }

        log.info("end file deletion (" + file + " by user ("+ user+")");

        return mapping.findForward("LIST_FILE");
    }
}

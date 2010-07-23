/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.experttag.util.filesharing.action;

import com.experttag.util.filesharing.*;
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
public class UploadAction extends org.apache.struts.action.Action {
    
    private static Logger log = Logger.getLogger(UploadAction.class);
    
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
    log.info("start file uploadation");

        HttpSession session=req.getSession(true);
	if(session.isNew()){	    
	    return mapping.findForward("NO_SESSION");
	}
	Object obj=session.getAttribute("login");
	if(obj==null){	    
	    return mapping.findForward("INVALID");
	}
	
	// The uploaded files will be saved into the
	// sub-directory under the root directory.
	// And this sub-directory is allocated for each user.
	  
   
	try{

	
        ParseSave.parseSaveFile(req);
	}	
	catch(Exception ex){
	    req.setAttribute("exception",ex.getMessage());
        log.error("Error while uploading : " + ex.getMessage());
	}

    log.info("end file uploadation");
    return mapping.findForward("LIST_FILE");
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.experttag.util.filesharing.action;

import com.experttag.util.filesharing.dao.PropertiesDAOHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

/**
 *
 * @author home
 */
public class SettingAction extends DispatchAction {
    
   private static Logger log = Logger.getLogger(SettingAction.class);
    
    public ActionForward opensetting(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        return mapping.findForward("opensetting");
    }

    
    public ActionForward savesetting(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("start setting update");

        String maxFile = request.getParameter("maxFile");
        PropertiesDAOHelper.updateSetting(maxFile);

        request.getSession().setAttribute("maxFile",maxFile);

        log.info("end setting update");
        return mapping.findForward("LIST_FILE");
    }
}
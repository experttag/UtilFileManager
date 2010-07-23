/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.experttag.util.filesharing.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


/**
 *
 * @author NANDUS
 */
public class UploadActionForm extends org.apache.struts.action.ActionForm {
    

    private FormFile example;
    private String touser;

    public FormFile getExample() {
        return example;
    }

    public void setExample(FormFile example) {
        this.example = example;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

  

    /**
     *
     */
    public UploadActionForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        return errors;
    }
}

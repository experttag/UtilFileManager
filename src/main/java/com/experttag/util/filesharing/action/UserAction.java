/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.experttag.util.filesharing.action;

import com.experttag.util.filesharing.dao.PropertiesDAOHelper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author home
 */
public class UserAction extends HttpServlet {
   private static Logger log = Logger.getLogger(UserAction.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        log.info("start user action");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String operation = request.getParameter("operation");
        if(operation==null) operation="";

        int status = -1;

         try {

        //for add operation
        if(operation.equalsIgnoreCase("1")){

            status = PropertiesDAOHelper.addUser(username,password,request);
            out.print(status);


        }else  //for edit
        if(operation.equalsIgnoreCase("2")){

            status = PropertiesDAOHelper.updateUser(username,password);
            out.print(status);

        }else //for delete
        if(operation.equalsIgnoreCase("3")){

            status = PropertiesDAOHelper.removeUser(username);
            out.print(status);

         }


        } finally {
            out.close();
        }
        log.info("end user action");
    }
        
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

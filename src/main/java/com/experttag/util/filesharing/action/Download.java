/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.experttag.util.filesharing.action;

import com.experttag.util.filesharing.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author home
 */
public class Download extends HttpServlet {

    private static Logger log = Logger.getLogger(Download.class);
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");


        String file = req.getParameter("file");
        String user = req.getParameter("user");

        log.info("start file download (" + file + " by user ("+ user+")");

        if (file == null) {
            (req.getRequestDispatcher(Constant.INVALID)).forward(req, res);
            return;
        }
        HttpSession session = req.getSession(true);
        if (session.isNew()) {
            (req.getRequestDispatcher(Constant.NO_SESSION)).forward(req, res);
            return;
        }
        Object obj = session.getAttribute("login");
        if (obj == null) {
            (req.getRequestDispatcher(Constant.INVALID)).forward(req, res);
            return;
        }
        LoginDir login = (LoginDir) obj;
        try {

            if(user!=null&&!user.equalsIgnoreCase(""))
                doDownload(req, res, file, new File(login.getFile(),user));
            else{
                doDownload(req, res, file, login.getFile());
            }

        } catch (FileNotFoundException ex) {
            log.error("Sorry, but the specified file was not found!");
            throw (new ServletException("Sorry, but the specified file was not found!"));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw (new ServletException(ex));
        }

        log.info("start file download (" + file + " by user ("+ user+")");
       
    }


    private void doDownload( HttpServletRequest req, HttpServletResponse resp,
                             String filename, File root) throws IOException  {
        File                f        = new File(root,filename);
        int                 length   = 0;
        ServletOutputStream op       = resp.getOutputStream();
        ServletContext      context  = getServletConfig().getServletContext();
        String              mimetype = context.getMimeType( filename );

        //
        //  Set the response and go!
        //
        //
        resp.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
        resp.setContentLength( (int)f.length() );
        resp.setHeader( "Content-Disposition", "attachment; filename=\"" + filename + "\"" );

        //
        //  Stream to the requester.
        //
        byte[] bbuf = new byte[1*1024*1024];
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        while ((in != null) && ((length = in.read(bbuf)) != -1))
        {
            op.write(bbuf,0,length);
        }

        in.close();
        op.flush();
        op.close();
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

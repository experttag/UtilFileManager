/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.experttag.util.filesharing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;



/**
 *
 * @author home
 */
public class ParseSave {

  //configuration set by user for file to be uploaded
    public static Properties fileconfig =null;

    private static Logger log = Logger.getLogger(ParseSave.class);

    public static void parseConfiguration(){
        try {
            fileconfig = new Properties();
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            InputStream is = cl.getResourceAsStream(Constant.FILE_CONFIG);
            fileconfig.load(is);
            is.close();
        } catch (IOException ex) {
            log.error("Error in parseCongiguration" + ex.getMessage());
            //Logger.getLogger(ParseSave.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method returns max file size that can be uploaded
     * @return
     */
    public static int getMaxFileSize() {

        if(fileconfig==null)
            parseConfiguration();

        String fileSize = fileconfig.getProperty("max_file");
        int fSize = 0;
        if(fileSize!=null){
            try{
                fSize=Integer.parseInt(fileSize);
            }
            catch(Exception e){
                log.error(e.getMessage());
            }
        }

        return fSize;
    }

    public static void parseSaveFile(HttpServletRequest req) throws Exception{

        if(fileconfig==null)
            parseConfiguration();

        //String touser = req.getParameter("touser");
        String user = (String)req.getSession().getAttribute("user");

       // if(user.equalsIgnoreCase("admin")&&touser!=null) user = touser;


        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(250*1024*1024);
        // the location for saving data that is larger than getSizeThreshold()
        factory.setRepository(new File("/tmp"));

        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum size before a FileUploadException will be thrown
        //upload.setSizeMax(1000000);

        //FileItemIterator iter = upload.getItemIterator(req);

        List fileItemsList = upload.parseRequest(req);
        Iterator iter = fileItemsList.iterator();

        FileItem fileItem = null;
        String fileName=null;

        if(user.equalsIgnoreCase("admin")){

         String formField=null;
         while (iter.hasNext()){
                fileItem = (FileItem)iter.next();
                if (fileItem.isFormField()){
                    formField = fileItem.getFieldName();
                    if(formField!=null&&formField.equalsIgnoreCase("touser")){
                        user = fileItem.getString();
                    }

                }
           }
        }

        String root = fileconfig.getProperty("file_root")+ fileconfig.getProperty("file_separator") +user+fileconfig.getProperty("file_separator");

        File saveTo = new File(root);
        if(!saveTo.exists())
            saveTo.mkdir();

        iter = fileItemsList.iterator();
        while (iter.hasNext()){
                fileItem = (FileItem)iter.next();
                if (!fileItem.isFormField()){
                    /* Save the uploaded file if its size is greater than 0. */

                    if (fileItem.getSize() > 0&&fileItem.getSize()<getMaxFileSize()){
                        fileName = FilenameUtils.getName(fileItem.getName());
                        saveTo = new File(saveTo, fileName);
                        try {
                            fileItem.write(saveTo);
                            log.info("(" + fileName + " ) file saved for user (" + user + ")"); 
                          }
                          catch (Exception e){
                                log.error("error while saving " + e.getMessage());
                          }

                    }
                }
           }

        }
}
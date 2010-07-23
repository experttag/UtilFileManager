/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.experttag.util.filesharing.dao;

import com.experttag.util.filesharing.Constant;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author home
 */
public class PropertiesDAOHelper {
    private static Logger log = Logger.getLogger(PropertiesDAOHelper.class);

    public static int addUser(String userName, String password,HttpServletRequest req){

        int status=0;
        String temp="";

        Properties fileconfig = (new PropertiesDAO(Constant.FILE_CONFIG)).getFile();
        String passwordFile = fileconfig.getProperty("file_root") + fileconfig.getProperty("file_separator") + Constant.PASS_FILE;

        PropertiesDAO pdo =new PropertiesDAO(passwordFile,0);
        if(pdo.numberOfKeys()<1)
            pdo = new PropertiesDAO(Constant.PASS_FILE);

        Properties prop = pdo.getFile();
        temp=prop.getProperty(userName);

        if(temp==null){
            prop.setProperty(userName,password);
            pdo.saveProperties(passwordFile);
            status=1;
            
            //resetting user list with new user
            HttpSession session = req.getSession(true);
            session.setAttribute("userList", prop);

            log.info("User added successfully ("+ userName+")");
        }
        else{
            status=-1;
            log.error("Error in adding user, user already exist ("+ userName+")");
        }


        return status;
    }


    public static int removeUser(String userName){

        int status=0;

        Properties fileconfig = (new PropertiesDAO(Constant.FILE_CONFIG)).getFile();
        String passwordFile = fileconfig.getProperty("file_root") + fileconfig.getProperty("file_separator") + Constant.PASS_FILE;

        PropertiesDAO pdo =new PropertiesDAO(passwordFile,0);
        if(pdo.numberOfKeys()<1)
            pdo = new PropertiesDAO(Constant.PASS_FILE);

        Properties prop = pdo.getFile();
        prop.remove(userName);

        pdo.saveProperties(passwordFile);
        status=1;
        log.info("User removed successfully ("+ userName+")");

        return status;
    }


    public static int updateUser(String userName, String password){

        int status=0;

        Properties fileconfig = (new PropertiesDAO(Constant.FILE_CONFIG)).getFile();
        String passwordFile = fileconfig.getProperty("file_root") + fileconfig.getProperty("file_separator") + Constant.PASS_FILE;

        PropertiesDAO pdo =new PropertiesDAO(passwordFile,0);
        if(pdo.numberOfKeys()<1)
        pdo = new PropertiesDAO(Constant.PASS_FILE);

        Properties prop = pdo.getFile();
        prop.setProperty(userName,password);
            pdo.saveProperties(passwordFile);
            status=1;

        log.info("User updated successfully ("+ userName+")");

        return status;
    }



    /**
     * This method will save generic file upload properties to file
     * @param fileSize
     * @return 1 as success and 0 as false
     */
    public static int updateSetting(String fileSize){

        int status=0;
        PropertiesDAO pdo=new PropertiesDAO(Constant.FILE_CONFIG);
        Properties fileconfig = pdo.getFile();
        String settingFile = fileconfig.getProperty("file_root") + fileconfig.getProperty("file_separator") + Constant.FILE_CONFIG;

        pdo =new PropertiesDAO(settingFile,0);
        if(pdo.numberOfKeys()<1)
        pdo = new PropertiesDAO(Constant.FILE_CONFIG);

        Properties prop = pdo.getFile();
        prop.setProperty("max_file",fileSize);
        pdo.saveProperties(settingFile);
        status=1;

        return status;
    }
}

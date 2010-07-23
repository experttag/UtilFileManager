/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.experttag.util.filesharing.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NANDUS
 */
public class PropertiesDAO {

    // Read properties file.
    Properties prop;
    String filePath;

    public PropertiesDAO(String filePath){

        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            InputStream is = cl.getResourceAsStream(filePath);
            this.filePath=filePath;
            prop = new Properties();
            prop.load(is);
            is.close();
        } catch (Exception ex) {
            Logger.getLogger(PropertiesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PropertiesDAO(String filePath, int defaultItem ){

        try {
            prop = new Properties();
            this.filePath=filePath;
            
            FileInputStream in = new FileInputStream(filePath);
            prop.load(in);
            in.close();
        } catch (Exception ex) {
            Logger.getLogger(PropertiesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public Properties getFile(){
        return prop;
    }

    public int numberOfKeys(){

        if(prop!=null)
            return prop.size();
        else
            return 0;
    }


    public void saveProperties(String passwordFile){

        // Write properties file.
        try {
            FileOutputStream out = new FileOutputStream(passwordFile);
            prop.store(out,null);
            out.close();

        } catch (IOException e) {
            System.out.print(e.getMessage()); 
        }

    }

}

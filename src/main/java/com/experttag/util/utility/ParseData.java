/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.experttag.util.utility;

/**
 * This java class contains methods used in parsing and converting data from one format to other
 * @author NANDUS
 */
public class ParseData {

    /**
     * This method will take a string as parameter and return integer if string
     * conatain valid number else return 0
     */

    public static int parseInt(String string){

        int num=0;

        if(string!=null){
            string.trim();
            try{
            if(string.length()>0)
                num = Integer.parseInt(string);
            }
            catch(Exception e){

            }
        }
        return num;
    }


    /**
     * This method will take a string as parameter and return float if string
     * contain valid float else return 0
     */

    public static float parseFloat(String string){

        float num =0;

        if(string!=null){
            string.trim();
            try{
            if(string.length()>0)
                num = Float.parseFloat(string);
            }
            catch(Exception e){

            }
        }
        return num;
    }

    /**
     * This method will take a string as parameter and return valid string
     */

    public static String parseString(String string){

        if(string!=null){
            string.trim();
        }
        else
            string ="";
        return string;
    }

    /**
     * This method checks a string and returns true if its valide , if it is null or blank it returns false
     * @param string
     * @return true if string not null or blank else false
     */
    public static boolean isValidString(String string){

        if(string==null)
        return false;
        else
            if((string.trim()).equalsIgnoreCase(""))
                return false;
            else
                return true;
        
    }
}

package com.experttag.util.utility;


import java.util.ArrayList;


/**
 * This class will contain helper methods for string to and from conversion and manupulation on string
 * @author NANDUS
 */
public class StringHelper {


    /**
     * This method will create a string of records separates by ',' from data from
     * arraylist
     * @param al
     * @return
     */
    public static String listToString(ArrayList al){

    String list = "";

    if(al.size()>0)
        list = list + al.get(0);

    for(int i=1;i<al.size();i++){
        list = list + "," + al.get(i);
    }

    return list;
    }


    /**
     * THis method will create an URL for for inviting friends
     * @param path
     * @param customerId
     * @return invite url
     */

    

    /**
     * this method will remove all special characters
     * @param orig
     * @return
     */
    public static String removeSpecialCharacters(String orig)
      {
            if(orig==null)
                return "";
            else{
                // replacing with space allows the camelcase to work a little better in most cases.
                orig.replace('\\',' ');
                orig.replace('(',' ');
                orig.replace(')',' ');
                orig.replace('/',' ');
                orig.replace('-',' ');
                orig.replace(',',' ');
                orig.replace('>',' ');
                orig.replace('<',' ');
                orig.replace('-',' ');
                orig.replace('&',' ');

                // single quotes shouldn't result in CamelCase variables like Patient's -> PatientS
                // "smart" forward quote
                orig.replace("'","");

                
                orig.replace("\u2019",""); // smart forward (possessive) quote.

                // make sure to get rid of double spaces.
                orig.replace("   "," ");
                orig.replace("  "," ");

                orig.trim(); // Remove leading and trailing spaces.

                return(orig);
            }
      }


}

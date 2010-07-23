
package com.experttag.util.utility;

import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;

/**
 * This class contain methods to extract and process web resources
 * @author NANDUS
 */
public class WebResourceHelper {


    /**
     * This method returns real path of web application root folder
     * @param sce
     * @return
     */
    public static String getRealPath(ServletContextEvent sce){
        
        return sce.getServletContext().getRealPath("/");
    }

    /**
     * THis method will create an URL for for inviting friends
     * @param path
     * @param customerId
     * @return invite url
     */

    public static String getBaseUrl(HttpServletRequest request) {

        StringBuffer baseUrl = new StringBuffer();

        //if(request.getServerName().equalsIgnoreCase("localhost")){
            baseUrl.append("http://").append(request.getServerName())
                .append(":").append(request.getServerPort()).append(request.getContextPath())
                .append("/");
       /* }else{
            baseUrl.append("http://makeroute.com").append("/");
       }*/


        return baseUrl.toString();
        //return "";
    }


}

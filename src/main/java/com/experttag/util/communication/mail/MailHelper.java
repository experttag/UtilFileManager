
package com.experttag.util.communication.mail;


import java.util.ArrayList;
import org.apache.log4j.Logger;


/**
 *This class contain helper classes for mail functionality
 * @author NANDUS
 */
public class MailHelper {
  
 
private static Logger log = Logger.getLogger(MailHelper.class);

   /**
    * The method will send email to the mailid provided
    * @param emailList
    * @param emailSubjectTxt
    * @param emailMsgTxt
    * @return true for success and false for failure
    */
   public static boolean sendMail(String emailList,String emailSubjectTxt, String emailMsgTxt) {

    boolean status=false;

    try{
        DirectMail mailer = new DirectMail();
        mailer.postMail( emailList, emailSubjectTxt, emailMsgTxt);
        log.info("Mail sending successfull");
        status=true;
    }
    catch(Exception e){
        log.error("Mail sending failed : " +e.getMessage());
    }

    return status;
  }

 /**
  * This method sends bulk mails and sms for PNR alerts
  * @param emailList
  * @param mobileList
  * @param msgTxt
  * @return
  */
 public static boolean sendPNRAlert(ArrayList pnrSubscribers) {

    boolean status=false;
   /* AlertBean alertBean =null;
    int noOfSubscribers=0;

    if(pnrSubscribers!=null)  noOfSubscribers = pnrSubscribers.size();

    try{
        DirectMail mailer = new DirectMail();

        for(int i=0;i<noOfSubscribers;i++){
            alertBean = (AlertBean)pnrSubscribers.get(i);
            mailer.postMail( alertBean.getEmail(), MailConstant.emailSubjectPNRAlert, createPNRMailBody(alertBean.getPnr(),alertBean.getStatus()));

            //mailer.postMail( alertBean.getMobile()+"@airtel.in", MailConstant.emailSubjectPNRAlert, createPNRSMSBody(alertBean.getPnr(),alertBean.getStatus()));
        }
        
        log.info("PNR status sending successfull");
        status=true;
    }
    catch(Exception e){
        log.error("PNR status sending failed : " +e.getMessage());
    }*/

    return status;
  }




   /**
    * This method created mail body for mail to be sent for PNR status
    * @param name
    * @param login
    * @param password
    * @return
    */
   public static String createPNRMailBody(String pnr,String status){
       StringBuffer body = new StringBuffer();

        body.append("Dear Sir,")
               .append("\n\nNamaskar")
               .append("\n\nHope this mail would find you in best of your health and work spirit.")
               .append("\n\nYou have been successfully registered with ghartak.in")
               .append("\nYour current ticket status :")
               .append("\n\t\t\tPNR: ").append(pnr)
               .append("\n\t\t\tStatus: ").append(status)
                .append("\n\nRegards,")
                .append("\nCustomer care Team")
                .append("\nMakeroute.com ")
                .append("\n\n\nLife is beautiful, live it. Don’t just exist.");       

       return body.toString();
   }

   /**
    * This method created mail body for sms to be sent for PNR status
    * @param name
    * @param login
    * @param password
    * @return
    */
   public static String createPNRSMSBody(String pnr,String status){
       StringBuffer body = new StringBuffer();

            body.append("Your current ticket status :")
                .append("\nPNR: ").append(pnr)
                .append("\nStatus: ").append(status)
                .append("\nRegards,")
                .append("\nMakeroute.com ");

       return body.toString();
   }


}

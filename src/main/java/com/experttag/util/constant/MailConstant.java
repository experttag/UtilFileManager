package com.experttag.util.constant;

/**
 * This java class contains contant for mail configuration
 * @author NANDUS
 */
public class MailConstant {

 //constant for mail pooling setup and initialisation
  public static final int SMTP_MAIL_POOL_SIZE = 5;
  public static final String SMTP_MAIL_PROTOCOL = "smtp";
  public static final String SMTP_HOST_NAME = "smtp.gmail.com";
  public static final String SMTP_HOST_PORT = "587";
  public static final String SMTP_AUTH_USER = "solara@gmail.com";
  public static final String SMTP_AUTH_PWD  = "ropanpur";
  public static final int SMTP_MAIL_RETRY = 3;

  //the permanent cc or bcc user
  public static final String PERMANENT_BCC_USER = "solara@gmail.com";
  public static final String NEW_CUSTOMER_BCC = "solara@gmail.com";
  public static final String DEFAULT_FROM_MAILID = "solara@gmail.com";

 
  //constant subject declarations
  public static final String emailSubjectPNRAlert  = "Current PNR Status";
  public static final String emailSubjectTrainAlert  = "Current Train status";
    
}

/*
 * @(#) Debugger 1.0 02/08/01
 */



package com.experttag.util.pool.core;

/**
 * This class is used for debugging, rather just to have a centalized 
 * gateway to standard output and few more things.
 *
 * @author	Sachin Shekar Shetty  
 * @version 1.0, 02/08/01
 *
 */

import java.io.*;

public class Debugger {

	private final boolean DEBUG;
	private static boolean  STATICDEBUG = false;
	private final String CLASSNAME;
	private static PrintStream outStream; 	
	private static PrintWriter out = null;


    public Debugger(String fileName, String className)  {

        System.out.println("Disributed Debugger is initailizing ;)  ....");
        STATICDEBUG = true;
        DEBUG = true;
		CLASSNAME = className;
		try {
			outStream = new PrintStream(new FileOutputStream(
                        fileName, true));
			out = new PrintWriter(outStream, true);
            out.println("");
            out.println("*****Logger initialized at " + new java.util.Date() 
                    + " *****");
            out.println("");
            System.out.println("Enterprise Log file located at: " + fileName);
		}
		catch (Exception exp) {
			System.err.println("Could not open log file:" + fileName);
			System.err.println("Exception: " + exp);
            System.err.println("No Messages will be logged");
            STATICDEBUG = false;
		}

    }
	
	public Debugger(String className ,boolean DEBUG)  {

		this.DEBUG = DEBUG;
		CLASSNAME = className;

	}

    
	public void writeException(Exception exp) {

        if (DEBUG && STATICDEBUG) {
            out.println("<message class='" + CLASSNAME + "' time-stamp='"
                    + new java.util.Date() + "'>");
            out.println("<exception>" + exp + "</exception>");
            out.println("<stack-trace>");
            exp.printStackTrace(outStream);
            out.println("</stack-trace>");
            out.println("</message>");
            outStream.flush();

        }

	}

	public void print(String msg) {
        
        if (DEBUG && STATICDEBUG) {
            out.println("<message class='" + CLASSNAME + "' time-stamp='"
                    + new java.util.Date() + "'>");
            out.println(msg); 
            out.println("</message>");
            out.flush();
        }

	}



	void waitHere() {

		try {
			System.in.read();
		}
		catch (IOException  ie) {
			System.err.println("DEBUGGER:Failed while waiting for input " + ie);
		}

	}



}




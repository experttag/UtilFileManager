package com.experttag.util.pool.core;

///*
// * @(#) ConfigFileParser.java 1.0 02/08/01
// */
//
//package org.smartlib.pool.core;
//
//import java.io.*;
//import java.util.*;
//
//import org.xml.sax.*;
//import org.xml.sax.helpers.DefaultHandler;
//
//import javax.xml.parsers.SAXParserFactory;
//import javax.xml.parsers.SAXParser;
//
///**
// * This Class parses the configuration file. The configuration file should be
// * as per the pool-config.dtd.
// *
// * @author	Sachin Shekar Shetty
// * @version 1.0, 02/08/01
// * @deprecated No longer user, we use DOM parsers in ConfigFileParser
// *
// */
//
//// I am  not very good at XML, if this is the way an XML file is
//// parsed than i think i guessed it right as usual, if not then as usual
//// I always defy conventional stuff.
//
//public class ConfigFileParser extends DefaultHandler {
//
//	private ArrayList ar = new ArrayList();
//	private static final String className = "ConfigFileParser";
//	private HashMap hs = new HashMap();
//	private boolean defaultPool = false;
//	private static Debugger debug = null;
//    private String lastElement = null;
//    private Stack stack = new Stack();
//    private String contents = null;
//
//	/**
//	 * This method returns a collection of PoolConfig objects.
//	 *
//	 * @param fileName Absolute file path.
//	 *
//	 * @return Collection of PoolConfig objects
//	 */
//	Collection getPoolConfig(String fileName)
//			throws ConnectionPoolException {
//
//		if  (fileName == null || fileName.equals(""))
//			throw new IllegalArgumentException("File Name cannot be null/empty");
//		return getPoolConfig(new File(fileName));
//
//	}
//
//	/**
//	 * This method returns a collection of PoolConfig objects.
//	 *
//	 * @param file file object representing the file to be parsed.
//	 *
//	 * @return Collection of PoolConfig objects
//	 */
//	Collection getPoolConfig(File file)
//			throws ConnectionPoolException {
//
//		SAXParserFactory factory = SAXParserFactory.newInstance();
//		try {
//			// Parse the input file
//			SAXParser saxParser = factory.newSAXParser();
//			saxParser.parse(file ,this);
//			return ar;
//		}
//		catch (Exception t) {
//			throw new ConnectionPoolException("Could not parse file:"+file,t);
//		}
//
//	}
//
//	/**
//	 *Sax callback method for parsing
//	 */
//	 public void startElement(String namespaceURI,
//                             String lName, // local name
//                             String qName, // qualified name
//                             Attributes attrs)  throws SAXException {
//
//
//        stack.push(qName);
//
//		if (!qName.equals("data-source")) return;
//
//		// Checking for distint poolName
//		String poolName = attrs.getValue("pool-name");
//		if (hs.get(poolName) != null )
//			throw new SAXException("Duplicate connection pool name:" +poolName);
//		hs.put(poolName , "");
//
//
//		// Checking for more than one default pools
//		String temp =attrs.getValue("default-pool");
//		boolean newDefaultPool = false;
//		if (temp != null)
//			newDefaultPool= temp.equals("true")?true:false;
//		else
//			newDefaultPool = false;
//		if ( defaultPool && newDefaultPool)
//			throw new SAXException("More than one pools with default-pool setting");
//		if (newDefaultPool)
//			defaultPool = newDefaultPool;
//
//		// Checking for detect-leaks
//		temp = attrs.getValue("detect-leaks");
//		boolean detectLeaks = false;
//		if (temp != null )
//			detectLeaks =	temp.equals("true")?true:false;
//		else
//			detectLeaks = false;
//
//		// Checking for leak-time-out
//		temp = attrs.getValue("leak-time-out");
//		long leakTimeOut = 0;
//		if (temp != null )
//			leakTimeOut = Long.parseLong(temp) * 1000;
//		else
//			leakTimeOut = 300 * 1000 ; // leakTimeOut
//
//		// Checking for poll-thread-time
//		temp = attrs.getValue("poll-thread-time");
//		long pollThreadTime = 0;
//		if (temp != null )
//			pollThreadTime = Long.parseLong(temp) * 1000;
//		else
//			pollThreadTime = 300 * 1000 ; // leakTimeOut
//
//		// Checking for auto-close
//		temp =attrs.getValue("auto-close");
//		boolean autoClose = false;
//		if (temp != null)
//			autoClose = temp.equals("true")?true:false;
//		else
//			autoClose = false;
//
//		// Getting default listener
//		String defaultListener =attrs.getValue("default-listener");
//
//		// Getting max-free-connections-for-release
//		int maxConnectionsForRelease = -1;
//		temp =attrs.getValue("max-free-connections-for-release");
//		if (temp != null) {
//			try {
//				maxConnectionsForRelease = Integer.parseInt(temp);
//			}
//			catch (NumberFormatException ne) {
//				throw new SAXException(
//						"max-free-connections-for-release is non numeric" , ne);
//			}
//		}
//
//		// Getting allow-annonymous connections
//		temp =attrs.getValue("allow-anonymous-connections");
//		boolean allowAnonymousConnections = false;
//		if (temp != null)
//			allowAnonymousConnections = temp.equals("true")?true:false;
//		else
//			allowAnonymousConnections = false;
//
//		// Getting the connection loader class
//		String connectionLoaderClass = null;
//		temp =attrs.getValue("connection-loader-class");
//		if (temp != null) {
//			connectionLoaderClass = temp;
//		}
//
//		// Getting the connection-wait-time-out
//		long connectionWaitTimeOut = 60*1000;
//		temp =attrs.getValue("connection-wait-time-out");
//		if (temp != null) {
//			connectionWaitTimeOut = Long.parseLong(temp) * 1000;
//		}
//
//		// Getting the Validator Query
//		String validatorQuery = "";
//		temp =attrs.getValue("validator-query");
//		if (temp != null) {
//			validatorQuery = temp;
//		}
//
//		// Getting the max-connection-idle-time
//		long maxConnectionIdleTime  = -1;
//		temp =attrs.getValue("max-connection-idle-time");
//		if (temp != null) {
//		    maxConnectionIdleTime = Long.parseLong(temp) * 1000;
//		}
//
//		PoolConfig config = new PoolConfig (
//				attrs.getValue("pool-name"),
//				Integer.parseInt(attrs.getValue("max-connections")),
//				Integer.parseInt(attrs.getValue("min-connections")),
//				attrs.getValue("username"),
//				attrs.getValue("password"),
//				attrs.getValue("connect-string"),
//				Integer.parseInt(attrs.getValue("increment-by")),
//				attrs.getValue("connection-driver")
//				);
//		config.setValidatorQuery(validatorQuery);
//		config.setDefaultPool(newDefaultPool);
//		config.setDetectLeaks(detectLeaks);
//		config.setLeakTimeOut(leakTimeOut);
//		config.setDefaultListener(attrs.getValue("default-listener"));
//		config.setPollThreadTime(pollThreadTime);
//		config.setAutoClose(autoClose);
//		if (defaultListener != null)
//			config.setDefaultListener(defaultListener);
//		config.setMaxConnectionsForRelease(maxConnectionsForRelease);
//		config.setAllowAnonymousConnections(allowAnonymousConnections);
//		config.setConnectionLoaderClass(connectionLoaderClass);
//		config.setConnectionWaitTimeOut(connectionWaitTimeOut);
//		config.setMaxConnectionIdleTime(maxConnectionIdleTime);
//		ar.add(config);
//
//	}
//
//	/**
//	 *Sax Callback method for parsing
//	 */
//    public void endElement(String namespaceURI,
//                           String sName, // simple name
//                           String qName  // qualified name
//                          ) throws SAXException    {
//
//        if (qName.equals("log-file")) {
//            String fileName = (String)stack.pop();
//            debug = new Debugger(contents, className );
//        }
//
//    }
//
//	// Main method, once upon a time this used to be the only method in
//	// my java programs. That is how you do procedural programming in java.
//	public static void main (String args[]) throws ConnectionPoolException  {
//
//		Collection s = new ConfigFileParser().
//				getPoolConfig("/home/sssachin/org.smartlib.pool.test.xml");
//		Iterator it = s.iterator();
//		while (it.hasNext()){
//
//		}
//
//	}
//
//    // Sax Method
//    public void characters(char[] ch, int start, int length)
//        throws SAXException {
//
//        contents = new String(ch, start, length);
//
//    }
//
//
//}

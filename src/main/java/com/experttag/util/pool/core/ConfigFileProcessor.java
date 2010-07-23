package com.experttag.util.pool.core;


import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.xml.sax.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;


/**
 * Created by IntelliJ IDEA.
 * User: kerneldebugger
 * Date: Sep 28, 2005
 * Time: 8:05:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigFileProcessor {

    Logger logger = Logger.getLogger(ConfigFileProcessor.class);

    PoolConfig[] getPoolConfig(String fileName) throws SAXException, SAXNotRecognizedException, IOException, ConnectionPoolException {

        if (fileName == null)
            throw new IllegalArgumentException("Filename cannot be null");

        File file = new File(fileName);
        if (!file.exists()) {
            throw new IllegalArgumentException("File: " + file + " does not exist");
        }

        return process(file);


    }

    PoolConfig[] getPoolConfig() throws SAXException, SAXNotRecognizedException, IOException, ConnectionPoolException {

        String fileName = System.getProperty(PoolConstants.CONFIG_FILE_SYSTEM_PROPERTY);
        if (logger.isDebugEnabled()) {
            logger.debug("Config File System Property:" + fileName);
        }
        return getPoolConfig(fileName);

    }


    private PoolConfig[] process(File file) throws SAXException, SAXNotRecognizedException, IOException, ConnectionPoolException {

        DOMParser xmlParser = new DOMParser();
        //xmlParser.setFeature("http://xml.org/sax/features/validation", true);
        xmlParser.setFeature("http://apache.org/xml/features/validation/schema", true);
        //xmlParser.setProperty( "http://apache.org/xml/properties/schema/external-schemaLocation",
        //        "D:\\sachin\\work\\smartpool\\project\\conf\\pool-config.xsd");
        //xmlParser.setProperty( "http://apache.org/xml/properties/schema/external-schemaLocation",
        //           "http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");



        xmlParser.setErrorHandler(new MyErrorHandler());
        if (logger.isDebugEnabled()) {
            logger.debug("Reading file: " + file.getAbsolutePath());
        }
        xmlParser.parse(file.getAbsolutePath());
        Document dom = xmlParser.getDocument();

        NodeList nodeList = dom.getElementsByTagName("pool");
        PoolConfig[] configs = new PoolConfig[nodeList.getLength()];
        for (int i=0; i<nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            configs[i] = getPoolConfig(node);
        }
        validatePoolConfigs(configs);
        return configs;

    }

    private PoolConfig getPoolConfig(Node node) throws ConnectionPoolException {
        PoolConfig config = new PoolConfig();
        NodeList list = node.getChildNodes();
        for (int i=0; i<list.getLength(); i++) {
            Node childNode = list.item(i);
            String nodeName = childNode.getNodeName();
            if (nodeName != null && nodeName.equals("basic")) {
                populateBasic(config, childNode);
            }
            if (nodeName != null && nodeName.equals("connect-strings")) {
                populateConnectString(config, childNode);
            }
            if (nodeName != null && nodeName.equals("pool-sizing")) {
                populatePoolSizing(config, childNode);
            }
            if (nodeName != null && nodeName.equals("leak-detection")) {
                populateLeakDetection(config, childNode);
            }
            if (nodeName != null && nodeName.equals("external-pooling")) {
                populateExternalPooling(config, childNode);
            }
        }
        return config;

    }

    private void populateExternalPooling(PoolConfig config, Node node) throws ConnectionPoolException {

        NodeList list = node.getChildNodes();
        List connList = new ArrayList();
        for (int i=0; i<list.getLength(); i++) {
            Node childNode = list.item(i);
            String nodeName = childNode.getNodeName();
            if (nodeName != null) {
                if (nodeName != null) {
                    if (nodeName.equals("connection-loader-class")) {
                        PoolConfig.ConnectionLoaderClass connectLoaderClasses = new PoolConfig.ConnectionLoaderClass();
                        connectLoaderClasses.setConnectionLoaderClass(getTagValue(childNode));
                        connectLoaderClasses.setName(config.getMultiPoolName() + "." + getAttributeValue(childNode, "name"));
                        connList.add(connectLoaderClasses);
                    }
                    if (nodeName.equals("thread-stickiness")) {
                        config.setThreadStickiness(Boolean.valueOf(getTagValue(childNode)));
                    }
                }

            }
        }
        config.setConnectionLoaderClass((PoolConfig.ConnectionLoaderClass[])connList.toArray(new PoolConfig.ConnectionLoaderClass[connList.size()]));
    }

    private void populateConnectString(PoolConfig config, Node node) throws ConnectionPoolException {

        NodeList list = node.getChildNodes();
        List connList = new ArrayList();

        for (int i=0; i<list.getLength(); i++) {
            Node childNode = list.item(i);
            String nodeName = childNode.getNodeName();
            if (nodeName != null) {
                if (nodeName.equals("connect-string")) {
                    PoolConfig.ConnectionString connectString = new PoolConfig.ConnectionString();
                    connectString.setConnectString(getTagValue(childNode));
                    connectString.setName(config.getMultiPoolName() + "." + getAttributeValue(childNode, "name"));
                    connList.add(connectString);
                }
                if (nodeName.equals("thread-stickiness")) {
                    config.setThreadStickiness(Boolean.valueOf(getTagValue(childNode)));
                }
            }
        }
        config.setConnectionString((PoolConfig.ConnectionString[])connList.toArray(new PoolConfig.ConnectionString[connList.size()]));

    }

    private void populatePoolSizing(PoolConfig config, Node node) {

        NodeList list = node.getChildNodes();
        for (int i=0; i<list.getLength(); i++) {
            Node childNode = list.item(i);
            String nodeName = childNode.getNodeName();
            if (nodeName != null) {
                if (nodeName.equals("min-connections")) {
                    config.setMinConnections(Integer.valueOf(getTagValue(childNode)));
                }
                if (nodeName.equals("max-connections")) {
                    config.setMaxConnections(Integer.valueOf(getTagValue(childNode)));
                }
                if (nodeName.equals("increment-by")) {
                    config.setIncrement(Integer.valueOf(getTagValue(childNode)));
                }
                if (nodeName.equals("increment-by")) {
                    config.setIncrement(Integer.valueOf(getTagValue(childNode)));
                }
                if (nodeName.equals("max-free-connections-for-release")) {
                    config.setMaxConnectionsForRelease(Integer.valueOf(getTagValue(childNode)));
                }
                if (nodeName.equals("connection-wait-time-out")) {
                    config.setConnectionWaitTimeOut(Integer.valueOf(getTagValue(childNode)));
                }
                if (nodeName.equals("max-connection-idle-time")) {
                    config.setMaxConnectionIdleTime(Integer.valueOf(getTagValue(childNode)));
                }
            }
        }
    }

    private void populateLeakDetection(PoolConfig config, Node node) {

        NodeList list = node.getChildNodes();
        for (int i=0; i<list.getLength(); i++) {
            Node childNode = list.item(i);
            String nodeName = childNode.getNodeName();
            if (nodeName != null) {
                if (nodeName.equals("detect-leaks")) {
                    config.setDetectLeaks(Boolean.valueOf(getTagValue(childNode)));
                }
                if (nodeName.equals("leak-time-out")) {
                    config.setLeakTimeOut(Integer.valueOf(getTagValue(childNode)));
                }
                if (nodeName.equals("poll-thread-time")) {
                    config.setPollThreadTime(Integer.valueOf(getTagValue(childNode)));
                }
                if (nodeName.equals("default-listener")) {
                    config.setDefaultListener(getTagValue(childNode));
                }
            }

        }


    }

    private void populateBasic(PoolConfig config, Node node) {


        NodeList list = node.getChildNodes();
        for (int i=0; i<list.getLength(); i++) {
            Node childNode = list.item(i);
            String nodeName = childNode.getNodeName();
            if (nodeName != null) {
                if (nodeName.equals("pool-name")) {
                    config.setMultiPoolName(getTagValue(childNode));
                }
                if (nodeName.equals("default-pool")) {
                    config.setDefaultPool(Boolean.valueOf(getTagValue(childNode)));
                }
                if (nodeName.equals("connection-driver")) {
                    config.setDriver(getTagValue(childNode));
                }
                if (nodeName.equals("username")) {
                    config.setUserName(getTagValue(childNode));
                }
                if (nodeName.equals("password")) {
                    config.setPassword(getTagValue(childNode));
                }
                if (nodeName.equals("auto-close")) {
                    config.setAutoClose(Boolean.valueOf(getTagValue(childNode)));
                }
                if (nodeName.equals("allow-anonymous-connections")) {
                    config.setAllowAnonymousConnections(Boolean.valueOf(getTagValue(childNode)));
                }
                if (nodeName.equals("validator-query")) {
                    config.setValidatorQuery(getTagValue(childNode));
                }
            }
        }

    }

    public  class MyErrorHandler implements ErrorHandler {


        public void warning(SAXParseException exception) throws SAXException {
            logger.fatal("Warning:", exception);
        }

        public void error(SAXParseException exception) throws SAXException {
            logger.fatal("Warning:", exception);
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            logger.fatal("Warning:", exception);
        }

    }


    private String getTagValue(Node nod) {

        // Some crazy xml hack
        if (nod.getChildNodes() != null && nod.getChildNodes().getLength() != 0)
            return nod.getChildNodes().item(0).getNodeValue();
        else
            return nod.getNodeValue();

    }

    private String getAttributeValue(Node nod, String attrName) throws ConnectionPoolException {

        // Some crazy xml hack
        NamedNodeMap map = nod.getAttributes();
        Node attrNode = map.getNamedItem(attrName);
        if (attrNode == null)
            throw new ConnectionPoolException("Attribute '" + attrName + "' of node '" + nod.getNodeName() + "' is null");
        return attrNode.getNodeValue();

    }

    private void validatePoolConfigs(PoolConfig[] configs) throws ConnectionPoolException {
        for (int i=0; i<configs.length; i++) {
            PoolConfig config = configs[i];
            if (config.getConnectionLoaderClass() != null && config.getConnectionLoaderClass().length == 0)
                throw new ConnectionPoolException("Please provide a valid connection provider for pool:" + config.getMultiPoolName());
            if (config.getConnectionString() !=null && config.getConnectionString().length == 0)
                throw new ConnectionPoolException("Please provide a valid connection string for pool:" + config.getMultiPoolName());
            if (config.getConnectionString() != null && config.getConnectionLoaderClass() != null)
                throw new ConnectionPoolException("You can specify either external-pooling or connect-strings but not both.");

            HashMap distinctMap = new HashMap();
            PoolConfig.ConnectionString[] connectStrings = config.getConnectionString();
            if (connectStrings != null) {
                for (int j=0; j <connectStrings.length; j++ ) {
                    if (connectStrings[j].getName() == null || connectStrings[j].getName().trim().length() == 0)
                        throw new ConnectionPoolException("Connect String name attribute cannot be null");
                    if (connectStrings[j].getConnectString() == null || connectStrings[j].getConnectString().trim().length() == 0)
                        throw new ConnectionPoolException("Connect String cannot be null");
                    if (distinctMap.get(connectStrings[j].getName()) != null)
                        throw new ConnectionPoolException("Connection string name is not unique: " + connectStrings[j].getName());
                    distinctMap.put(connectStrings[j].getName(), "hello");
                }
            }

            distinctMap.clear();
            PoolConfig.ConnectionLoaderClass[] connectionLoaderClasses = config.getConnectionLoaderClass();
            if (connectionLoaderClasses != null) {
                for (int j=0; j <connectionLoaderClasses.length; j++ ) {
                    if (connectionLoaderClasses[j].getName() == null || connectionLoaderClasses[j].getName().trim().length() == 0)
                        throw new ConnectionPoolException("Connect String name attribute cannot be null");
                    if (connectionLoaderClasses[j].getConnectionLoaderClass() == null || connectionLoaderClasses[j].getConnectionLoaderClass().trim().length() == 0)
                        throw new ConnectionPoolException("Connect String cannot be null");
                    if (distinctMap.get(connectionLoaderClasses[j].getName()) != null)
                        throw new ConnectionPoolException("Connection provider name is not unique: " + connectionLoaderClasses[j].getName());
                    distinctMap.put(connectionLoaderClasses[j].getName(), "hello");
                }
            }


        }
    }


}

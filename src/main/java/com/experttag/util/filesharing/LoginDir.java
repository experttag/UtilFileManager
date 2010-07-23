package com.experttag.util.filesharing;

import java.io.*;

/**
 * @author nandus
 */
public class LoginDir{

    //private static final String LS=System.getProperty("line.separator");
    private static final FilesOnly FILTER=new FilesOnly();
    private static final DirectoryOnly DIR_FILTER=new DirectoryOnly();

    private File dir=null;
    private String user=null;

    public LoginDir(String root,String user)
	throws IOException{
	dir=new File(root,user);
	if(!dir.exists()){
	    // The root directory must already be created.
	    dir.mkdir();
	}
	this.user=user;
    }

    public LoginDir(String root) throws IOException{
	dir=new File(root);
    if(!dir.exists()){
	    // The root directory must already be created.
	    dir.mkdir();
        (new File(dir,"admin")).mkdir();
	}
	this.user="admin";
    }

    public String getUser(){
	return user;
    }

    public File getFile(){
	return dir;
    }

    public String getFileList(){
	File[] files=dir.listFiles(FILTER);
	if(files.length==0){
	    return "<ul><li>Empty directory!</li></ul>";
	}
	String name;
	StringBuffer list=new StringBuffer("<table>");
	for(int i=0; i<files.length; i++){
	    name=files[i].getName();
        list.append("<tr><td>")
               .append(i+1).append(".")
               .append("</td><td>")
               .append(name)
               .append("</td><td>")
               .append("<a href=\"javascript:downloadbyuser('").append(name)
               .append("')\" >Download</a>")
               .append("</td><td>")
               .append("<a href=\"javascript:deletefilebyuser('").append(name).append("')\">[Delete]</a>")
               .append("</td></tr>");
    }
	list.append("</table>");
	return list.toString();
    }


    public String getFileList(File dir){
	File[] files=dir.listFiles(FILTER);
	if(files.length==0){
	    return "<ul><li>Empty directory!</li></ul>";
	}
    String name;
	StringBuffer list=new StringBuffer("<table>");
	for(int i=0; i<files.length; i++){
	    name=files[i].getName();
        list.append("<tr><td>")
               .append(i+1).append(".")
               .append("</td><td>")
               .append(name)
               .append("</td><td>")
               .append("<a href=\"javascript:download('").append(name)
               .append("','").append(dir.getName())
               .append("')\" >Download</a>")
               .append("</td><td>")
               .append("<a href=\"javascript:deletefile('").append(name)
               .append("','").append(dir.getName())
               .append("')\">[Delete]</a>")
               .append("</td></tr>");
    }
	list.append("</table>");
	return list.toString();

    }

    public String getAllList(){
	File[] dirs=dir.listFiles(DIR_FILTER);
	if(dirs.length==0){
	    return "<ul><li>Empty directory!</li></ul>";
	}

	StringBuffer list = new StringBuffer("<div id=>");
	for(int i=0; i<dirs.length; i++){
        if(dirs[i].getName().equalsIgnoreCase("admin"))
            list.append("<div id='").append(dirs[i].getName()).append("'>");
        else
            list.append("<div id='").append(dirs[i].getName()).append("' style=\"display:none\">");

        list.append("<br><h4>Files shared from user: ")
            .append(dirs[i].getName()).append("</h4>");
        list.append(getFileList(dirs[i]));
	    list.append("</div>");
	}

	list.append("</ul>");

	return list.toString();
    }


    public void deleteFile(String file)	throws FileNotFoundException{
	File fileObj=new File(dir,file);
	if(!fileObj.exists()){
	    throw (new FileNotFoundException("File not found!"));
	}
	File parent=fileObj.getParentFile();
	if(!(parent.equals(dir))){
	    throw (new IllegalArgumentException("We don't allow to delete the files beyond your directory."));
	}
	fileObj.delete();
    }


    public void deleteFile(String user , String file) throws FileNotFoundException{

    File dir=new File(this.dir , user);
    File fileObj=new File(dir,file);

	if(!fileObj.exists()){
	    throw (new FileNotFoundException("File not found!"));
	}
	File parent=fileObj.getParentFile();
	if(!(parent.equals(dir))){
	    throw (new IllegalArgumentException("We don't allow to delete the files beyond your directory."));
	}
	fileObj.delete();
    }

    
} 

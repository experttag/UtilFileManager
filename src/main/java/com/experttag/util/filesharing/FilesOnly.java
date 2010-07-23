
package com.experttag.util.filesharing;

import java.io.*;


public class FilesOnly implements FileFilter{

    public FilesOnly(){
    }

    public boolean accept(File file){
	return file.isFile();
    }

} 

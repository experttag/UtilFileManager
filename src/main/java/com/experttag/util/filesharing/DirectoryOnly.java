/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.experttag.util.filesharing;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author home
 */
public class DirectoryOnly implements FileFilter{

    public DirectoryOnly(){
    }

    public boolean accept(File file){
	return file.isDirectory();
    }

}

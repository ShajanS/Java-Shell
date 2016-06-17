package commands;

import java.util.HashMap;

public class Directory {
  
  //This directory's parent directory
  private Directory parent;
  //Map of directory names to subdirectories
  private HashMap<String, Directory> subdirs;
  //Map of filenames to files in this directory
  //TODO: Implement file class. Decomment this when this is done.
  //private HashMap<String, File> files;
  
  //Create a new directory which is not within another directory
  public Directory(){
    subdirs = new HashMap<String, Directory>();
    //files = new HashMap<String, File>();
  }
  
  //Create a new directory having a parent directory
  public Directory(Directory parent){
    new Directory();
    this.parent = parent;    
  }
  
  //Adds a directory to this one as a subdirectory
  public void insertDirectory(Directory dirToAdd, String name){
    subdirs.put(name, dirToAdd);
  }
  
  //Returns the subdirectory with the given name, null otherwise
  public Directory getDirectory(String name){
    if (subdirs.containsKey(name)){
      return subdirs.get(name);
    } else {
      return null;
    }
  }
}

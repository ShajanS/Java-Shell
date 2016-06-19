package data;

import java.util.HashMap;

public class Directory {
  
  //This directory's parent directory
  private Directory parent;
  public Directory getParent() {
    return parent;
  }

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
    //This directory will now be the parent of the new one
    dirToAdd.parent = this;
  }
  
  //Returns the subdirectory with the given name, null otherwise
  public Directory getDirectory(String name){
    if (subdirs.containsKey(name)){
      return subdirs.get(name);
    } else {
      return null;
    }
  }
    
  //Creates a new directory with the given name inside of this one
  public void createDirectory(String name){
    //Make the new directory
    Directory newDir = new Directory();
    //Insert the new directory with the given name
    insertDirectory(newDir, name);
  }
  
  //Removes the specified directory if it exists
  public void removeDirectory(String name){
    if (subdirs.containsKey(name)){
      subdirs.remove(name);
    }
  }
  
  //Returns the parent directory of the specified relative path, raises an
  //error if it cannot be found
  public Directory navigateToParent(String dest){
    Directory result;
    //If the destination contains no slashes, the starting directory is the
    //parent.
    if (!dest.contains("/")){
      result = this;
   //If the destination starts with ../, search for the rest of the path
   //starting from this directory's parent
    } else if (dest.startsWith("../")){
      result = parent.navigateToParent(dest.substring(3));
    //If the destination starts with ./, search for the rest of the path
    //starting from this directory
    } else if (dest.startsWith("./")){
      result = navigateToParent(dest.substring(2));
    } else{
    //Otherwise, look for the directory with the name matching the the
    //substring from the start of the destination to the first slash.
      String nextDirName = dest.substring(0, dest.indexOf('/'));
      Directory nextDir = getDirectory(nextDirName);
      //If it exists, search for the rest of the path starting with that dir
      if (nextDir != null){
        result = nextDir.navigateToParent(dest.substring(dest.indexOf('/')+1));
      } else {
      //If it does not, return null.
        result = null;
      }
    }
    //Return the resulting directory
    return result;
  }
  
}

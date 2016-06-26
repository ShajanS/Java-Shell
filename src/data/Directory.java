package data;

import java.util.Arrays;
import java.util.HashMap;

public class Directory {
  
  //This directory's parent directory
  private Directory parent;
  public Directory getParent() {
    return parent;
  }
  //This directory's name
  private String name;
  public String getName(){
    return name;
  }

  //Map of directory names to subdirectories
  private HashMap<String, Directory> subdirs;
  //Map of filenames to files in this directory
  private HashMap<String, File> files;
  
  //Create a new directory which is not within another directory
  public Directory(String name){
    this.name = name;
    subdirs = new HashMap<String, Directory>();
    files = new HashMap<String, File>();
  }
  
  //Create a new directory having a parent directory
  public Directory(String name, Directory parent){
    new Directory(name);
    this.parent = parent;    
  }
  
  //Adds a directory to this one as a subdirectory
  public void insertDirectory(Directory dirToAdd, String name)
      throws InvalidPathException{
    //Check if a file or directory with this name exists. If so, throw an
    //exception
    if (subdirs.containsKey(name)||files.containsKey(name)){
      throw new InvalidPathException("A file or directory with "
          + "this name already exists");
    }
    subdirs.put(name, dirToAdd);
    //This directory will now be the parent of the new one
    dirToAdd.parent = this;
  }
  
  //Creates a new directory with the given name inside of this one
  public void createDirectory(String name) throws InvalidPathException{
    //Make the new directory
    Directory newDir = new Directory(name);
    //Try to insert the directory. If this fails, throw the relevant exception
    try{
      insertDirectory(newDir, name);
    } catch(InvalidPathException e){
      throw e;
    }
  }
  
  //Returns the subdirectory with the given name, throws an exception otherwise
  //An empty string means this directory.
  public Directory getDirectory(String name) throws InvalidPathException{
    if (subdirs.containsKey(name)){
      return subdirs.get(name);
    } else {
      throw new InvalidPathException("No such directory");
    }
  }
    
    
  //Removes the specified directory if it exists
  public void removeDirectory(String name){
    if (subdirs.containsKey(name)){
      subdirs.remove(name);
    }
  }
  
  //Inserts a file into this directory
  public void insertFile(String name, File file) throws InvalidPathException{
    //Check if a file or directory with this name already exists
  //If not, add it to the file list. If so, throw an exception
    if (files.containsKey(name)){
      throw new InvalidPathException("A file or directory with this name"
          + "already exists");
    } else{
      files.put(name, file);
    } 
  }
  
  //Removes a file from this directory
  public void removeFile(String name) throws InvalidPathException{
    //If this file exists, remove it. If not, throw an exception.
    if (files.containsKey(name)){
      files.remove(name);
    } else{
      throw new InvalidPathException("No such file.");
    }
  }
  
  //Returns a file from this directory
  public File getFile(String name) throws InvalidPathException{
    //If there is a file with this name, return it.
    if (files.containsKey(name)){
      return files.get(name);
    } else{
    //If not, throw an exception.
      throw new InvalidPathException("No such file");
    }
  }
  
  //Returns this directory's absolute path
  public String getPath(){
    //Initialise the result string to an empty string
    String result = "";
    //If this directory has a parent, add its path to the result string
    if (parent != null){
      result += parent.getPath();
    }
    //Add this directory's name followed by a slash
    result += name + "/";
    
    return result;
  }
  
  //Returns the absolute path corresponding to a relative path
  //from this directory
  public String absolutePath(String relativePath){
	String result;
	//Start in the current directory
	Directory curr= this;
	//If the relative path starts with a /, it is the absolute path
	//It should have a slash at the end, add one if it is not there
	if (relativePath.startsWith("/")){
		result = relativePath;
		if (!relativePath.endsWith("/")){
			result += "/";
		}
	} else{
		//Otherwise, while the relative path starts with ./ or ../,
		while (relativePath.startsWith("./") || relativePath.startsWith("../")){
		  //If it starts with 2 dots and slash, move to the parent directory
		  // if it exists and remove these characters
		  if (relativePath.startsWith("../")){
		    if (curr.parent != null){
		      curr = curr.parent;
		    }
		    relativePath = relativePath.substring(3);
		  } else{
			//If it starts with one dot and a slash, remove these 
			relativePath = relativePath.substring(2);
		  }
		}
		//Return the relavant directory's absolute path plus what remains of
		//the relative path
		return curr.getPath() + relativePath + "/";
	}
  return result;
  }
  
  //Returns the parent directory of the specified absolute path starting at
  //root, or throws an exception if it does not exist
  public static Directory navigateToParent(String dest, Directory root)
    throws InvalidPathException{
    Directory result;
    //If the destination contains only two slashes, the starting directory is the
    //parent.
    if (dest.indexOf("/", 1) == dest.lastIndexOf("/")){
      result = root;
   } else{
   //Otherwise, get the string between the first slash and the second, or the
   //end of the string, if there isn't one
   int slashIndex = dest.indexOf('/', 1);
   if (slashIndex == -1){
	   slashIndex = dest.length();
   }
   String nextLevelName = dest.substring(1, slashIndex);
   //Look for the directory with this name in root
   Directory nextLevel = root.getDirectory(nextLevelName);
   //If it exists, look for the rest of the path starting in that directory
   if (nextLevel != null){
	   try{
	     result = navigateToParent(dest.substring(slashIndex+1), nextLevel);
   } catch(InvalidPathException e){
	   throw e;
   }
   } else{
   //If not, throw an exception
     throw new InvalidPathException("This path could not be found");
   }
   }
   //Return the resulting directory
   return result;
  }
  
  //Returns an array of this directory's contents, sorted alphabetically
  public String[] getContents(){
    //Create a new array to hold the combined contents
    int resultLength = subdirs.size() + files.size();
    String[] result = new String[resultLength];
    //Add the subdirectory and file names to this list
    int i = 0;
    for (String key : subdirs.keySet()){
      result[i++] = key;
    }
    for (String key : files.keySet()){
      result[i++] = key;
    }
    //Sort the list
    Arrays.sort(result);
    
    return result;
  }
}

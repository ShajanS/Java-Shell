package test;

import java.util.ArrayList;

import data.Directory;
import data.FileSystem;
import data.InvalidPathException;

public class MockFileSystem implements FileSystem {
  
  public java.util.HashMap<String, String> files;
  public java.util.HashMap<String, Directory> directories;
  public ArrayList<String> commandHistory;
  public java.util.Stack<String> dirStack;
  
  public String currDir;
  
  /**
   * The constructor, simply initializes the instance attributes
   */
  public MockFileSystem(){
    // Initialize the attributes
    files = new java.util.HashMap<String, String>();
    directories = new java.util.HashMap<String, Directory>();
    commandHistory = new ArrayList<String>();
    dirStack = new java.util.Stack<String>();
    currDir = "";
  }

  /**
   * @see data.FileSystem#getDirectory(java.lang.String)
   */
  @Override
  public Directory getDirectory(String path) throws InvalidPathException {
    //Get the directory with the given name if it exists, otherwise throw an
    //exception.
    if (directories.containsKey(path)){
      return directories.get(path);
    } else{
      throw new InvalidPathException("No such directory");
    }
  }

  /**
   * @see data.FileSystem#makeCurrentDirectory(java.lang.String)
   */
  @Override
  public void makeCurrentDirectory(String path) throws InvalidPathException {
    // If this directory exists, make its name the current directory
    if (directories.containsKey(path)){
      currDir = path;
    } else{
    //If not, throw an exception
      throw new InvalidPathException("No such directory");
    }

  }

  /**
   * @see data.FileSystem#getCurrentDirectoryPath()
   */
  @Override
  public String getCurrentDirectoryPath() {
    //Return the current directory
    return currDir;
  }

  /**
   * @see data.FileSystem#getFileContents(java.lang.String)
   */
  @Override
  public String getFileContents(String path) throws InvalidPathException {
    // If given a valid path (the string "VALID PATH"), return some sting
    if (path.equals("VALID PATH")){
      return "A winner is you!";
    } else{
    // Otherwise, throw an exception
      throw new InvalidPathException("No such file");
    }
  }

  /**
   * @see data.FileSystem#overwriteFile(java.lang.String, java.lang.String)
   */
  @Override
  public void overwriteFile(String path, String newContents)
      throws InvalidPathException {
    // If given an invalid path (i.e. not the string "VALID PATH"),
    // throw an exception
    if (!path.equals("VALID PATH")){
      throw new InvalidPathException("Could not resolve path");
    }
  }

  /**
   * @see data.FileSystem#appendToFile(java.lang.String, java.lang.String)
   */
  @Override
  public void appendToFile(String path, String newContents)
      throws InvalidPathException {
    // This should essentially do what overwriteFile does
    overwriteFile(path, newContents);
  }

  /**
   * @see data.FileSystem#addCommandToHistory(java.lang.String)
   */
  @Override
  public void addCommandToHistory(String command) {
    // Add the command to the history
    commandHistory.add(command);
  }

  /**
   * @see data.FileSystem#getCommandHistory()
   */
  @Override
  public ArrayList<String> getCommandHistory() {
    // Return the command history
    return commandHistory;
  }

  /**
   * @see data.FileSystem#pushToDirStack(java.lang.String)
   */
  @Override
  public void pushToDirStack(String path) {
    // Push the string to the stack
    dirStack.push(path);
  }

  /**
   * @see data.FileSystem#popFromDirStack()
   */
  @Override
  public String popFromDirStack() {
    return dirStack.pop();
  }

}

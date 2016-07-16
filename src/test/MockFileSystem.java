package test;

import java.util.ArrayList;

import data.Directory;
import data.File;
import data.FileSystem;
import data.InvalidPathException;

/**
 * @author Kirill Lossev
 * A mock file system meant to make testing of commands easier
 */
public class MockFileSystem implements FileSystem {

  public java.util.HashMap<String, Directory> directories;
  public java.util.HashMap<String, String> files;
  public ArrayList<String> commandHistory;
  public java.util.Stack<String> dirStack;

  public String currDir;

  /**
   * The constructor, simply initializes the instance attributes
   */
  public MockFileSystem() {
    // Initialize the attributes
    directories = new java.util.HashMap<String, Directory>();
    // There should be a directory with a blank name, acting as the root dir
    directories.put("", new data.Directory(""));
    files = new java.util.HashMap<String, String>();
    commandHistory = new ArrayList<String>();
    dirStack = new java.util.Stack<String>();
    currDir = "";
  }

  /**
   * Returns the Directory object at the specified path
   * 
   * @param path The path of the directory
   * @return The Directory object at this path
   * @throws InvalidPathException
   */
  @Override
  public Directory getDirectory(String path) throws InvalidPathException {
    // If the path starts with .., replace it with the path of currDir's parent
    if (path.startsWith("..")) {
      path = currDir.substring(0, currDir.lastIndexOf('/') + 1)
          + path.substring(2);
    }
    // If it starts with ., remove it
    if (path.startsWith(".")) {
      path = path.substring(1);
    }
    // Get the directory with the given name if it exists, otherwise throw an
    // exception.
    if (directories.containsKey(path)) {
      return directories.get(path);
    } else {
      throw new InvalidPathException("No such directory");
    }
  }

  /**
   * Sets the directory at the specified path as the current directory
   * 
   * @param path The path of the directory to switch to
   * @throws InvalidPathException
   */
  @Override
  public void makeCurrentDirectory(String path) throws InvalidPathException {
    // If the path starts with .., replace it with the path of currDir's parent
    if (path.startsWith("..")) {
      path = currDir.substring(0, currDir.lastIndexOf('/') + 1)
          + path.substring(2);
    }
    // If it starts with ., remove it
    if (path.startsWith(".")) {
      path = path.substring(1);
    }
    // If this directory exists, make its name the current directory
    if (directories.containsKey(path)) {
      currDir = path;
    } else {
      // If not, throw an exception
      throw new InvalidPathException("No such directory");
    }

  }

  /**
   * @return The absolute path of the current working directory
   */
  @Override
  public String getCurrentDirectoryPath() {
    // Return the current directory
    return currDir;
  }

  /**
   * Returns the contents of the file at the specified path
   * 
   * @param path The path of the file
   * @return The contents of this file
   * @throws InvalidPathException
   */
  @Override
  public String getFileContents(String path) throws InvalidPathException {
    // If given a valid path, return the contents
    if (files.containsKey(path)) {
      return files.get(path);
    } else {
      // Otherwise, throw an exception
      throw new InvalidPathException("No such file");
    }
  }

  /**
   * Overwrites the file at the given path, or creates a new one if it does not
   * exist
   * 
   * @param path The path of the file
   * @param newContents The string to overwrite this file with
   * @throws InvalidPathException If the file cannot be reached
   */
  @Override
  public void overwriteFile(String path, String newContents)
      throws InvalidPathException {
    // Map the path to the contents in the file list
    files.put(path, newContents);
  }

  /**
   * Appends to the file at the given path, or creates a new one if it does not
   * exist
   * 
   * @param path The path of the file
   * @param newContents The string to append to this file
   * @throws InvalidPathException If the file cannot be reached
   */
  @Override
  public void appendToFile(String path, String newContents)
      throws InvalidPathException {
    // If the file exists already, get its contents, put a newline
    // after them, then append the new contents to them
    if (files.containsKey(path)){
      newContents = files.get(path) + "\n" + newContents;
    }
    // Overwrite the file with the result
    overwriteFile(path, newContents);
  }

  /**
   * Adds a command to the command history
   * 
   * @param command The command to add
   */
  @Override
  public void addCommandToHistory(String command) {
    // Add the command to the history
    commandHistory.add(command);
  }

  /**
   * Returns the command history, a list of all commands entered this session
   * 
   * @return The command history
   */
  @Override
  public ArrayList<String> getCommandHistory() {
    // Return the command history
    return commandHistory;
  }

  /**
   * Pushes a directory to the directory stack
   * 
   * @param path The path of the directory stack
   */
  @Override
  public void pushToDirStack(String path) {
    // Push the string to the stack
    dirStack.push(path);
  }

  /**
   * Gets the directory path at the top of the directory stack
   * 
   * @return The top directory of the directory stack
   */
  @Override
  public String popFromDirStack() {
    return dirStack.pop();
  }

  /**
   * Gets a File object at given path
   * 
   * @param path of the File
   * @throws InvalidPathException If the file cannot be reached
   */
  @Override
  public File getFile(String path) throws InvalidPathException {
    // If a file with this name exists, return a file with the
    // same contents
    if (files.containsKey(path)){
      return new data.File(path, null, files.get(path));
    } else{
    // If not, throw the exception
      throw new InvalidPathException();
    }
  }

  /**
   * Checks if path is a Directory or not
   * 
   * @param path the path that will be checked
   * @throws InvalidPathException If path cannot be reached
   */
  @Override
  public boolean isDirectory(String path) throws InvalidPathException {
    // If the path is in the directory list, return true. If not, return false
    return directories.containsKey(path);
  }

  /**
   * Checks if path is a File or not
   * 
   * @param path the path that will be checked
   * @throws InvalidPathException If path cannot be reached
   */
  @Override
  public boolean isFile(String path) throws InvalidPathException {
    // If the path is in the file list, return true. If not, return false
    return files.containsKey(path);
  }

}

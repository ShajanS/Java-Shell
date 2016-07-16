package data;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Kirill Lossev An implementation of FileSystem to be used with JShell
 */
public class JFileSystem implements FileSystem {

  // The instance variables, to keep track of session data
  private ArrayList<String> commandHistory;
  private Directory rootDir;
  private Directory currDir;
  private Stack<String> dirStack;

  // This class is a singleton
  private static JFileSystem fileSysRef;

  public JFileSystem() {
    commandHistory = new ArrayList<String>();
    rootDir = new Directory("");
    currDir = rootDir;
    dirStack = new Stack<String>();
  }

  /**
   * Returns a reference to an instance of this class, as a singleton
   * 
   * @return An instance of this class
   */
  public static JFileSystem getFileSysReference() {
    if (fileSysRef == null) {
      fileSysRef = new JFileSystem();
    }
    return fileSysRef;
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
    Directory resultDir;
    // If the path starts with a forward slash, remove it and start at the
    // root directory
    if (path.startsWith("/")) {
      path = path.substring(1);
      resultDir = rootDir;
    } else {
      // Otherwise, start in the current directory
      resultDir = currDir;
    }
    // Split the string by slashes
    String[] pathArray = path.split("/");
    // For each string in the resulting array,
    for (String nextDir : pathArray) {
      // If the next dir is . or empty, do nothing.
      if (!nextDir.equals(".") && !nextDir.isEmpty()) {
        // If it is .., move to the parent if possible, or throw an exception
        if (nextDir.equals("..")) {
          resultDir = resultDir.getParent();
          if (resultDir == null) {
            throw new InvalidPathException("Path is unreachable");
          }
        } else {
          // Otherwise, get the directory with that name and make it the current
          // one. If this cannot be done, throw an exception
          try {
            resultDir = resultDir.getDirectory(nextDir);
          } catch (InvalidPathException e) {
            throw e;
          }
        }
      }
    }
    // Return the current directory
    return resultDir;
  }

  /**
   * Sets the directory at the specified path as the current directory
   * 
   * @param path The path of the directory to switch to
   * @throws InvalidPathException
   */
  @Override
  public void makeCurrentDirectory(String path) throws InvalidPathException {
    // Try to get the directory specified by the path
    // If this is possible, make it the current directory.
    try {
      Directory newCurrDir = getDirectory(path);
      currDir = newCurrDir;
    } catch (InvalidPathException e) {
      // If not, throw an exception
      throw e;
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
    // Try to get the directory specified by the path
    // If this is possible, make it the current directory.
    try {
      getDirectory(path);
      return true;
    } catch (InvalidPathException e) {
      // If not, throw an exception
      return false;
    }
  }

  /**
   * Checks if path is a File or not
   * 
   * @param path the path that will be checked
   * @throws InvalidPathException If path cannot be reached
   */
  @Override
  public boolean isFile(String path) throws InvalidPathException {
    // Try to get the directory specified by the path
    // If this is possible, make it the current directory.
    try {
      getFile(path);
      return true;
    } catch (InvalidPathException e) {
      // If not, throw an exception
      return false;
    }
  }

  /**
   * @return The absolute path of the current working directory
   */
  @Override
  public String getCurrentDirectoryPath() {
    // Return the path of the current directory
    return currDir.getPath();
  }

  /**
   * Gets the file object with the given name
   * 
   * @param path The path to the desired file
   * @return The file at this path
   * @throws InvalidPathException if the file is unreachable
   */
  public File getFile(String path) throws InvalidPathException {
    Directory dirToCheck;
    // If there are slashes in the path, get the dir the file should be in
    if (path.contains("/")) {
      String parentDirName = path.substring(0, path.lastIndexOf('/'));
      dirToCheck = getDirectory(parentDirName);
      path = path.substring(path.lastIndexOf('/') + 1);
    } else {
      // If not, get the current directory
      dirToCheck = currDir;
    }
    // Get the file from the relevant directory if it exists
    try {
      return dirToCheck.getFile(path);
    } catch (InvalidPathException e) {
      // If it doesn't, throw an exception
      throw e;
    }
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
    // Get the file with the given name if possible, and return its contents
    try {
      File wantedFile = getFile(path);
      return wantedFile.getContents();
    } catch (InvalidPathException e) {
      // If it cannot be found, throw an exception
      throw e;
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
    // Get the desired file if possible, and overwrite its contents
    try {
      File wantedFile = getFile(path);
      wantedFile.overwriteContents(newContents);
    } catch (InvalidPathException e) {
      // If it cannot be found, try to get the parent directory,
      // create the file with the correct contents, and insert it into the dir
      // If there are slashes in the path, get the dir the file should be in
      try {
        Directory dirToCheck;
        if (path.contains("/")) {
          String parentDirName = path.substring(0, path.lastIndexOf('/'));
          dirToCheck = getDirectory(parentDirName);
          path = path.substring(path.lastIndexOf('/') + 1);
        } else {
          // If not, get the current directory
          dirToCheck = currDir;
        }
        File newFile = new data.File(path, dirToCheck, newContents);
        dirToCheck.insertFile(path, newFile);
      } catch (InvalidPathException f) {
        // If its parent cannot be found, throw the exception.
        throw e;
      }
    }
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
    // Get the desired file if possible, and append to its contents
    try {
      File wantedFile = getFile(path);
      wantedFile.appendContents(newContents);
    } catch (InvalidPathException e) {
      // If it cannot be found, try to get the parent directory,
      // create the file with the correct contents, and insert it into the dir
      // If there are slashes in the path, get the dir the file should be in
      try {
        Directory dirToCheck;
        if (path.contains("/")) {
          String parentDirName = path.substring(0, path.lastIndexOf('/'));
          dirToCheck = getDirectory(parentDirName);
          path = path.substring(path.lastIndexOf('/') + 1);
        } else {
          // If not, get the current directory
          dirToCheck = currDir;
        }
        File newFile = new data.File(path, dirToCheck, newContents);
        dirToCheck.insertFile(path, newFile);
      } catch (InvalidPathException f) {
        // If its parent cannot be found, throw the exception.
        throw e;
      }
    }
  }

  /**
   * Adds a command to the command history
   * 
   * @param command The command to add
   */
  @Override
  public void addCommandToHistory(String command) {
    // Add the command to the history list
    commandHistory.add(command);
  }

  /**
   * Returns the command history, a list of all commands entered this session
   * 
   * @return The command history
   */
  @Override
  public ArrayList<String> getCommandHistory() {
    // Return a copy of the history list, so that this class's does not
    // get mutated by other classes.
    ArrayList<String> result = new ArrayList<String>();
    for (String command : commandHistory) {
      result.add(command);
    }
    return result;
  }

  /**
   * Pushes a directory to the directory stack
   * 
   * @param path The path of the directory stack
   */
  @Override
  public void pushToDirStack(String path) {
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

}

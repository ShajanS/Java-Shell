package data;

/**
 * @author Kirill Lossev
 * The interface for a file system, a group of files and directories
 * organised in a tree structure.
 */
public interface FileSystem {

  /**
   * Returns the Directory object at the specified path
   * 
   * @param path The path of the directory
   * @return The Directory object at this path
   * @throws InvalidPathException
   */
  public Directory getDirectory(String path) throws InvalidPathException;

  /**
   * Sets the directory at the specified path as the current directory
   * 
   * @param path The path of the directory to switch to
   * @throws InvalidPathException
   */
  public void makeCurrentDirectory(String path) throws InvalidPathException;

  /**
   * @return The absolute path of the current working directory
   */
  public String getCurrentDirectoryPath();

  /**
   * Returns the contents of the file at the specified path
   * 
   * @param path The path of the file
   * @return The contents of this file
   * @throws InvalidPathException
   */
  public String getFileContents(String path) throws InvalidPathException;

  /**
   * Overwrites the file at the given path, or creates a new one if it does not
   * exist
   * 
   * @param path The path of the file
   * @param newContents The string to overwrite this file with
   * @throws InvalidPathException If the file cannot be reached
   */
  public void overwriteFile(String path, String newContents)
      throws InvalidPathException;

  /**
   * Appends to the file at the given path, or creates a new one if it does not
   * exist
   * 
   * @param path The path of the file
   * @param newContents The string to append to this file
   * @throws InvalidPathException If the file cannot be reached
   */
  public void appendToFile(String path, String newContents)
      throws InvalidPathException;

  /**
   * Adds a command to the command history
   * 
   * @param command The command to add
   */
  public void addCommandToHistory(String command);

  /**
   * Returns the command history, a list of all commands entered this session
   * 
   * @return The command history
   */
  public java.util.ArrayList<String> getCommandHistory();

  /**
   * Pushes a directory to the directory stack
   * 
   * @param path The path of the directory stack
   */
  public void pushToDirStack(String path);

  /**
   * Gets the directory path at the top of the directory stack
   * 
   * @return The top directory of the directory stack
   */
  public String popFromDirStack();

  /**
   * Gets a File object at given path
   * 
   * @param path of the File
   * @throws InvalidPathException If the file cannot be reached
   */
  public File getFile(String path) throws InvalidPathException;

  /**
   * Checks if path is a Directory or not
   * 
   * @param path the path that will be checked
   * @throws InvalidPathException If path cannot be reached
   */
  boolean isDirectory(String path) throws InvalidPathException;

  /**
   * Checks if path is a File or not
   * 
   * @param path the path that will be checked
   * @throws InvalidPathException If path cannot be reached
   */
  boolean isFile(String path) throws InvalidPathException;
}

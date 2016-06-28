package data;

import java.util.Arrays;
import java.util.HashMap;

public class Directory {

  // This directory's parent directory
  private Directory parent;

  /**
   * Getter for this directory's parent
   * 
   * @return This directory's parent
   */
  public Directory getParent() {
    return parent;
  }

  // This directory's name
  private String name;

  /**
   * Getter for this directory's name
   * 
   * @return This directory's name
   */
  public String getName() {
    return name;
  }

  // Map of directory names to subdirectories
  private HashMap<String, Directory> subdirs;
  // Map of filenames to files in this directory
  private HashMap<String, File> files;

  /**
   * Constructor for a new directory which is not within another directory
   * 
   * @param name The name of the new directory
   */
  public Directory(String name) {
    this.name = name;
    subdirs = new HashMap<String, Directory>();
    files = new HashMap<String, File>();
  }

  /**
   * Constructor for a new directory having a parent directory
   * 
   * @param name The name of the new directory
   * @param parent The parent of the new directory
   */
  public Directory(String name, Directory parent) {
    this.name = name;
    subdirs = new HashMap<String, Directory>();
    files = new HashMap<String, File>();
    this.parent = parent;
  }

  /**
   * Adds a directory to this one as a subdirectory
   * 
   * @param dirToAdd The directory to add to this one
   * @param name The name for the directory
   */
  public void insertDirectory(Directory dirToAdd, String name)
      throws InvalidPathException {
    // Check if a file or directory with this name exists. If so, throw an
    // exception
    if (subdirs.containsKey(name) || files.containsKey(name)) {
      throw new InvalidPathException(
          "A file or directory with " + "this name already exists");
    }
    subdirs.put(name, dirToAdd);
    // This directory will now be the parent of the new one
    dirToAdd.parent = this;
  }

  /**
   * Creates a new directory with the given name inside of this one
   * 
   * @param name The name of the new directory to create
   * @throws InvalidPathException
   */
  public void createDirectory(String name) throws InvalidPathException {
    // Make the new directory
    Directory newDir = new Directory(name);
    // Try to insert the directory. If this fails, throw the relevant exception
    try {
      insertDirectory(newDir, name);
    } catch (InvalidPathException e) {
      throw e;
    }
  }

  /**
   * Returns the subdirectory with the given name, throws an exception
   * otherwise. An empty string means this directory.
   * 
   * @param name The name of the directory to retrieve
   * @return The subdirectory with this name
   * @throws InvalidPathException
   */
  public Directory getDirectory(String name) throws InvalidPathException {
    if (subdirs.containsKey(name)) {
      return subdirs.get(name);
    } else {
      throw new InvalidPathException("No such directory");
    }
  }


  /**
   * Removes the specified directory if it exists
   * 
   * @param name The name of the subdirectory to remove
   */
  public void removeDirectory(String name) {
    if (subdirs.containsKey(name)) {
      subdirs.remove(name);
    }
  }

  /**
   * Inserts a file into this directory
   * 
   * @param name The name of the file to add
   * @param file The File object to add
   * @throws InvalidPathException
   */
  public void insertFile(String name, File file) throws InvalidPathException {
    // Check if a file or directory with this name already exists
    // If not, add it to the file list. If so, throw an exception
    if (files.containsKey(name)) {
      throw new InvalidPathException(
          "A file or directory with this name" + "already exists");
    } else {
      files.put(name, file);
    }
  }

  /**
   * Removes a file from this directory
   * 
   * @param name The name of the file to remove
   * @throws InvalidPathException
   */
  public void removeFile(String name) throws InvalidPathException {
    // If this file exists, remove it. If not, throw an exception.
    if (files.containsKey(name)) {
      files.remove(name);
    } else {
      throw new InvalidPathException("No such file.");
    }
  }

  /**
   * Returns a file from this directory
   * 
   * @param name The name of the file to return
   * @return The File object with the given name
   * @throws InvalidPathException
   */
  public File getFile(String name) throws InvalidPathException {
    // If there is a file with this name, return it.
    if (files.containsKey(name)) {
      return files.get(name);
    } else {
      // If not, throw an exception.
      throw new InvalidPathException("No such file");
    }
  }

  /**
   * Returns this directory's absolute path
   * 
   * @return The absolute path of this directory, relative to the highest
   *         ancestor directory with no parent.
   */
  public String getPath() {
    // Initialise the result string to an empty string
    String result = "";
    // If this directory has a parent, add its path to the result string
    if (parent != null) {
      result += parent.getPath();
    }
    // Add this directory's name followed by a slash
    result += name + "/";

    return result;
  }

  /**
   * Returns the absolute path corresponding to a relative path from this
   * directory
   * 
   * @param relativePath A path relative to this directory
   * @return
   */
  public String absolutePath(String relativePath) {
    String result;
    // Start in the current directory
    Directory curr = this;
    // If the relative path starts with a /, it is the absolute path
    // It should have a slash at the end, add one if it is not there
    if (relativePath.startsWith("/")) {
      result = relativePath;
      if (!relativePath.endsWith("/")) {
        result += "/";
      }
    } else {
      // Otherwise, while the relative path starts with ./ or ../,
      while (relativePath.startsWith("./") || relativePath.startsWith("../")) {
        // If it starts with 2 dots and slash, move to the parent directory
        // if it exists and remove these characters
        if (relativePath.startsWith("../")) {
          if (curr.parent != null) {
            curr = curr.parent;
          }
          relativePath = relativePath.substring(3);
        } else {
          // If it starts with one dot and a slash, remove these
          relativePath = relativePath.substring(2);
        }
      }
      // Return the relavant directory's absolute path plus what remains of
      // the relative path
      return curr.getPath() + relativePath + "/";
    }
    return result;
  }


  /**
   * Returns an array of this directory's contents, sorted alphabetically
   * 
   * @return An array of the names of all files and directories in this one
   */
  public String[] getContents() {
    // Create a new array to hold the combined contents
    int resultLength = subdirs.size() + files.size();
    String[] result = new String[resultLength];
    // Add the subdirectory and file names to this list
    int i = 0;
    for (String key : subdirs.keySet()) {
      result[i++] = key;
    }
    for (String key : files.keySet()) {
      result[i++] = key;
    }
    // Sort the list
    Arrays.sort(result);

    return result;
  }
}

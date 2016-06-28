package commands;

import data.Directory;
import data.InvalidPathException;

public class Ls implements Command {

  /**
   * Lists all directories and files under current or given directory
   * 
   * @param fs The filesystem
   * @param params The path of the directory or file
   * @return the formatted string, or an error if the directory or file cannot
   *         be found
   */
  public String execute(data.FileSystem fs, String params) {
    String result = "\n";
    // if no parameters are given then use the current directory or file
    if (params.isEmpty()) {
      result = runWithoutParams(fs);
    } else {
      // assume that the parameter is a directory
      boolean directory = true;
      try {
        // name then elements in the directory
        result += fs.getDirectory(params).getName() + ":"
            + runWithParams(fs, params) + "\n";
      } catch (InvalidPathException e) {
        directory = false;
      }
      // if it isn't a directory then check if it's a file
      if (directory == false) {
        try {
          // check if it is a file else throw and error
          fs.getFileContents(params);
          // split the path and get last element as the name of the file
          result += params.substring(params.lastIndexOf('/') + 1) + "\n";
          // if not a valid path for directory or file then return error message
        } catch (InvalidPathException e) {
          result = "No such path exists";
        }
      }
    }

    return result;
  }

  /**
   * Formats directory's elements without begin given a parameter
   * 
   * @param fs The filesystem
   * @param params The path of the directory
   * @return The formatted string or, an error if the directory cannot be found
   */
  private String runWithoutParams(data.FileSystem fs) {
    // get current directory's path into string
    String path = fs.getCurrentDirectoryPath();
    // function that formats the directory at given path
    return getFormattedContents(fs, path);
  }

  /**
   * Formats the directory's elements given a parameter
   * 
   * @param fs The filesystem
   * @param params The path of the directory
   * @return The formatted string or, an error if the directory cannot be found
   */
  private String runWithParams(data.FileSystem fs, String params) {
    return getFormattedContents(fs, params);
  }

  /**
   * Given the current file system and path of directory, format to string
   * 
   * @param fs The filesystem
   * @param path the path of the specified directory
   * @return The formatted string or, an error if the directory cannot be found
   */
  private String getFormattedContents(data.FileSystem fs, String path) {
    String result = "\n";
    // to catch any errors in path or type of path
    try {
      // get directory object
      Directory d = fs.getDirectory(path);
      // get contents of the object
      String[] contents = d.getContents();
      // add them to the string
      for (String element : contents) {
        result += element + "\n";
      }
    } catch (InvalidPathException e) {
      result = "Error - Directory does not exist\n";
    }
    return result;
  }

}

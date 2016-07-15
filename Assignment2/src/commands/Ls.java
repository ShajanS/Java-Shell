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
    if (params.isEmpty()) {
      result = runWithoutParams(fs);
    } else {
      String [] param = params.split("\\s+");
      if (param.length == 1) {
        if (!checkParamsForR(param[0])) {
          if (checkIfDirectory(fs, param[0])) {
            result += runWithParams(fs, param[0]);
          } else if (checkIfFile(fs, params)) {
            result += getLastElement(param[0]);
          } else {
            return "\n Error: No such path exists\n";
          }
        } else if (checkParamsForR(param[0])) {
          //RECURSIVE PART HERE
            
          
        }
      } else if (param.length > 1) {
        if (!checkParamsForR(param[0])) {
          for (String par : param) {
            result += execute(fs, par);
          }
        } else {
          for (String par : param) {
            if (par != param[0]) {
              result += execute(fs, "-r" + par);
            }
          }
        } 
      }
    }
    
    
    return result + "\n";
  }

  private boolean checkIfDirectory(data.FileSystem fs, String path) {
    boolean typeD = true;
    try {
      fs.getDirectory(path);
    } catch (InvalidPathException e) {
      typeD = false;
    }
    return typeD;
  }

  private boolean checkIfFile(data.FileSystem fs, String path) {
    boolean typeF = true;
    try {
      fs.getFileContents(path);
    } catch (InvalidPathException e) {
      typeF = false;
    }
    return typeF;
  }

  private String getLastElement(String path) {
    String result = path.substring(path.lastIndexOf('/') + 1) + "\n";
    return result;
  }

  private boolean checkParamsForR(String param) {
    boolean assume = false;
    if (param.equals("-R") || param.equals("-r")) {
      assume = true;
    }
    return assume;
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

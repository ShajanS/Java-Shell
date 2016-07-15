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
            return "\nNo such path exists";
          }
        } else if (checkParamsForR(param[0])) {
          
            
          
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
    
    
    return result;
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
    if (param == "-R" || param == "-r") {
      assume = true;
    }
    return assume;
  }

  /*
   * public String execute(data.FileSystem fs, String params) { String result =
   * "\n"; // if no parameters are given then use the current directory or file
   * if (params.isEmpty()) { result = runWithoutParams(fs); } else { String[]
   * param = params.split("\\s+"); if (param.length == 1 && (param[0] != "-R" ||
   * param[0] != "-r")) { // begin // assume that the parameter is a directory
   * boolean directory = true; try { // name then elements in the directory
   * result += fs.getDirectory(params).getName() + ":" + runWithParams(fs,
   * params); } catch (InvalidPathException e) { directory = false; } // if it
   * isn't a directory then check if it's a file if (directory == false) { try {
   * // check if it is a file else throw and error fs.getFileContents(params);
   * // split the path and get last element as the name of the file result +=
   * params.substring(params.lastIndexOf('/') + 1) + "\n"; // if not a valid
   * path for directory or file then return error // message } catch
   * (InvalidPathException e) { result = "No such path exists"; } // end }
   * 
   * } else if (param.length == 1 && (param[0] == "-R" || param[0] == "-r")) {
   * 
   * } }
   * 
   * return result; }
   */

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

 /* private String recursive(data.FileSystem fs) {

  }*/
}

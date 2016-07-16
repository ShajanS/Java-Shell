package commands;

import java.util.ArrayList;
import java.util.List;
import data.Directory;
import data.InvalidArgumentException;
import data.InvalidPathException;

public class Ls implements Command {

  /**
   * Lists all directories and files under current or given directory
   * 
   * @param fs The filesystem
   * @param params The path of the directory or file
   * @return the formatted string, or an error if the directory or file cannot
   *         be found
   * @throws InvalidArgumentException
   */
  public String execute(data.FileSystem fs, String params)
      throws InvalidArgumentException {
    String result = "\n";
    List<String> temp = test(fs, params);
    for (String apple : temp) {
      result += apple + "\n";
    }
    return result;

  }

  public List<String> test(data.FileSystem fs, String params)
      throws InvalidArgumentException {
    List<String> dirAndFil = new ArrayList<>();
    if (params.isEmpty()) {
      dirAndFil.addAll(runWithoutParams(fs));
    } else {
      String[] param = params.split("\\s+");
      if (!checkParamsForR(param[0])) {
        if (param.length == 1) {
          if (checkIfDirectory(fs, param[0])) {
            dirAndFil.addAll(runWithParams(fs, param[0]));
          } else if (checkIfFile(fs, params)) {
            dirAndFil.add(getLastElement(param[0]));
          }
        } else {
          for (String par : param) {
            dirAndFil.addAll(test(fs, par));
          }
        }
      } else {
        if (param.length == 2) {
          
        } else {
          for (String par : param) {
            if (par != param[0])
            dirAndFil.addAll(test(fs, "-r" + par));
          }
        }

      }
    }


    return dirAndFil;
  }

  /*
   * public List <String> test(data.FileSystem fs, String params) throws
   * InvalidArgumentException { List <String> dirAndFil = new ArrayList<>(); if
   * (params.isEmpty()) { dirAndFil.addAll(runWithoutParams(fs)); } else {
   * String [] param = params.split("\\s+"); if (param.length == 1) { if
   * (!checkParamsForR(param[0])) { if (checkIfDirectory(fs, param[0])) {
   * dirAndFil.addAll(runWithParams(fs, param[0])); } else if (checkIfFile(fs,
   * params)) { dirAndFil.add(getLastElement(param[0])); } else if
   * (checkParamsForR(param[0])) { } } else if (param.length > 1) { if
   * (!checkParamsForR(param[0])) { for (String par : param) {
   * dirAndFil.addAll(test(fs, par)); } } else { for (String par : param) { if
   * (par != param[0]) { dirAndFil.addAll(test(fs, "-r" + par)); } } } } } }
   * return dirAndFil; }
   */



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
   * @throws InvalidArgumentException
   */
  private List<String> runWithoutParams(data.FileSystem fs)
      throws InvalidArgumentException {
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
   * @throws InvalidArgumentException
   */
  private List<String> runWithParams(data.FileSystem fs, String params)
      throws InvalidArgumentException {
    return getFormattedContents(fs, params);
  }

  /**
   * Given the current file system and path of directory, format to string
   * 
   * @param fs The filesystem
   * @param path the path of the specified directory
   * @return The formatted string or, an error if the directory cannot be found
   * @throws InvalidArgumentException
   */
  private List<String> getFormattedContents(data.FileSystem fs, String path)
      throws InvalidArgumentException {
    List<String> result = new ArrayList<>();
    // to catch any errors in path or type of path
    try {
      // get directory object
      Directory d = fs.getDirectory(path);
      // get contents of the object
      String[] contents = d.getContents();
      // add them to the string
      for (String element : contents) {
        result.add(element);
      }
    } catch (data.InvalidPathException e) {
      // If an exception is thrown, return an error
      throw new data.InvalidArgumentException("Error - invalid path\n");
    }
    return result;
  }

}

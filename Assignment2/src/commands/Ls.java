package commands;

import data.Directory;
import data.InvalidPathException;

public class Ls implements Command {

  /**
   * 
   * specified by the parameter
   * 
   * @param fs The filesystem whose current directory to change
   * @param params The path of the directory to change to
   * @return An error if the directory cannot be found, a newline otherwise
   */
  public String execute(data.FileSystem fs, String params) {
    String result = "\n";
    if (params.isEmpty()) {
      result = runWithoutParams(fs);
    } else {
      boolean directory = true;
      try {
        fs.getDirectory(params);
        result += fs.getDirectory(params).getName() + ":" + runWithoutParams(fs)
            + "\n";
      } catch (InvalidPathException e) {
        directory = false;
      }
      if (directory == false) {
        try {
          fs.getFileContents(params);
          result += params.substring(params.lastIndexOf('/') + 1) + "\n";
        } catch (InvalidPathException e) {
          result = "No such path exists";
        }
      }
      result = runWithParams(fs, params);
    }

    return result;
  }

  /**
   * Changes the current working directory of the filesystem to the directory
   * specified by the parameter
   * 
   * @param fs The filesystem whose current directory to change
   * @param params The path of the directory to change to
   * @return An error if the directory cannot be found, a newline otherwise
   */
  private String runWithoutParams(data.FileSystem fs) {
    String path = fs.getCurrentDirectoryPath();
    return getFormattedContents(fs, path);
  }

  /**
   * Changes the current working directory of the filesystem to the directory
   * specified by the parameter
   * 
   * @param fs The filesystem whose current directory to change
   * @param params The path of the directory to change to
   * @return An error if the directory cannot be found, a newline otherwise
   */
  private String runWithParams(data.FileSystem fs, String params) {
    return getFormattedContents(fs, params);
  }

  /**
   * Changes the current working directory of the filesystem to the directory
   * specified by the parameter
   * 
   * @param fs The filesystem whose current directory to change
   * @param params The path of the directory to change to
   * @return An error if the directory cannot be found, a newline otherwise
   */
  private String getFormattedContents(data.FileSystem fs, String path) {
    String result = "\n";
    try {
      Directory d = fs.getDirectory(path);
      String[] contents = d.getContents();
      for (String element : contents) {
        result += element + "\n";
      }
    } catch (InvalidPathException e) {
      // TODO Auto-generated catch block
      result = "Error - Directory does not exist\n";
    }
    return result;
  }

}

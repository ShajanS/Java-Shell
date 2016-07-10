package commands;

import data.Directory;
import data.InvalidPathException;

public class Cp implements Command {

  /**
   * Copies a item, given it's path, to a new path specified by the parameter
   * 
   * @param fs The filesystem whose current directory to change
   * @param params A string containing both the oldpath of the file/Directory
   *        and the newpath
   * @return An error if the file or directory cannot be found, a newline
   *         otherwise
   */
  public String execute(data.FileSystem fs, String params) {
    String result = "\n";
    String[] path = params.split("\\s+");
    try {
      if (fs.isDirectory(path[0]))
        ;
      {
        // Create a copy of oldpath to place inside newpath
        Directory temp = fs.getDirectory(path[0]);
        temp.setParent(fs.getDirectory(path[1]));
      }
      // Insert the file in oldpath into the directory in newpath
      if (fs.isFile(path[0])) {
        fs.getDirectory(path[1]).insertFile(fs.getFile(path[0]).getName(),
            fs.getFile(path[0]));
      }
    } catch (InvalidPathException e) {
      // If it cannot be found, return an error message
      result = "Error - File/Directory does not exist\n";
    }
    return result;
  }
}

package commands;

import data.InvalidPathException;

public class Mv implements Command {

  /**
   * Moves a item, given it's path, to a new path specified by the parameter
   * 
   * @param fs The filesystem whose current directory to change
   * @param params A string containing both the oldpath of the file and the
   *        newpath
   * @return An error if the file or directory cannot be found, a newline
   *         otherwise
   */
  public String execute(data.FileSystem fs, String params) {
    String result = "\n";
    String[] paths = params.split("\\s+");
    try {
      // Insert the file in oldpath into the directory in newpath
      fs.getDirectory(paths[1]).insertFile(fs.getFile(paths[0]).getName(),
          fs.getFile(paths[0]));
      // Then delete the original file in oldpath
      fs.getFile(paths[0]).getParent()
          .removeFile(fs.getFile(paths[0]).getName());
    } catch (InvalidPathException e) {
      // If it cannot be found, return an error message
      result = "Error - File/Directory does not exist\n";
    }
    return result;
  }
}

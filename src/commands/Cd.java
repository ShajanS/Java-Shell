package commands;

import data.InvalidPathException;

public class Cd implements Command {

  /**
   * Changes the current working directory of the filesystem to the directory
   * specified by the parameter
   * 
   * @param fs The filesystem whose current directory to change
   * @param params The path of the directory to change to
   * @return An error if the directory cannot be found, a newline otherwise
   */
  public String execute(data.FileSystem fs, String params) {
    String result = "\n";
    // Make the given parameter the current directory of the filesystem
    try {
      fs.makeCurrentDirectory(params);
    } catch (InvalidPathException e) {
      // If it cannot be found, return an error message
      result = "Error - Directory does not exist\n";
    }
    return result;
  }
}

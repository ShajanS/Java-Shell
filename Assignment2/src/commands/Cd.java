package commands;

import data.InvalidArgumentException;
import data.InvalidPathException;

/**
 * @author Kirill Lossev
 * The class for the cd command, which changes the working directory.
 */
public class Cd implements Command {

  /**
   * Changes the current working directory of the filesystem to the directory
   * specified by the parameter
   * 
   * @param fs The filesystem whose current directory to change
   * @param params The path of the directory to change to
   * @return A newline on success
   * @throws InvalidArgumentException if the path is invalid
   */
  public String execute(data.FileSystem fs, String params)
      throws InvalidArgumentException {
    String result = "\n";
    // Make the given parameter the current directory of the filesystem
    try {
      fs.makeCurrentDirectory(params);
    } catch (InvalidPathException e) {
      // If it cannot be found, return an error message
      throw new data.InvalidArgumentException("Error - "
          + "Directory does not exist\n");
    }
    return result;
  }
}

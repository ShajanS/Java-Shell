package commands;

import data.InvalidArgumentException;

/**
 * @author Kirill Lossev
 * The class for the mkdir command, which creates a new directory on the
 * filesystem.
 */
public class Mkdir implements Command {

  /**
   * Creates a directory at the desired (relative or absolute) path
   * 
   * @param fs The filesystem in which to make the directory
   * @param params The list of directories to create
   * @return A newline on success
   * @throws InvalidArgumentException if creating a directory fails
   */
  public String execute(data.FileSystem fs, String params) throws InvalidArgumentException {
    // Get the list of directory names to make
    java.util.ArrayList<String> args = CommandHandler.getPaths(params);
    // Loop over the elements of the list,
    try {
      for (String dirToMake : args) {
        // If the string contains a slash, get the substring up to the last one
        // This is the parent directory
        String parentName;
        if (dirToMake.contains("/")) {
          parentName = dirToMake.substring(0, dirToMake.lastIndexOf('/'));
        } else {
          // If not, the parent directory will be the current one (empty string)
          parentName = "";
        }
        // Everything after the last slash is the name of the new dir
        String dirName = dirToMake.substring(dirToMake.lastIndexOf('/') + 1);
        // Get the parent directory if possible
        data.Directory parentDir = fs.getDirectory(parentName);
        // Create a new directory with the given name inside it
        parentDir.createDirectory(dirName);
      }
    } catch (data.InvalidPathException e) {
      // If an exception is thrown, return an error
      throw new data.InvalidArgumentException("Error - invalid path\n");
    }
    // If everything goes smoothly, return a newline
    return "\n";
  }

}

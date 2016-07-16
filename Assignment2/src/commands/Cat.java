package commands;

import data.InvalidArgumentException;
import data.InvalidPathException;

/**
 * 
 * @author Shajan Sivarjah This class if for the Cat command and it Outputs the
 *         contents of desired text files
 */
public class Cat implements Command {
  // error message string
  // cat command output string
  String result = "";

  /**
   * Outputs the contents of desired text files (absolute path)
   * 
   * @param fs The filesystem in which to make the directory
   * @param params The list of file names to output (can be in a diff dir)
   * @return An error if a file cannot be found, and if found output the the
   *         content
   */
  public String execute(data.FileSystem fs, String params)
      throws InvalidArgumentException {
    String argNames = params;
    // split the input string at every whitespace and store each word(filename)
    // in an array
    String[] fileNames = argNames.split("\\s+");

    // if no file names are given raise error message
    if (fileNames.length == 0) {
      throw new InvalidArgumentException("Error - File(s) not found.\n");

    } else {
      // Obtain the object under path in fileSystem
      // If it's null then it would fall into the else block
      try {
        // Formating of output (# of line breaks)
        // iterate through the file names given
        for (int file = 0; file < fileNames.length; file++) {
          // get the file contents for each file
          if (fs.isFile(fileNames[file]) == true) {
            String currentFile = fs.getFileContents(fileNames[file]);
            int length = fileNames.length;
            // add one new line break if its the last file
            if (fileNames.length > 1) {
              if (file == length - 1) {
                result = result.concat(currentFile) + "\n";
              }
              // add three new line break in between files
              else {
                result = result.concat(currentFile) + "\n\n\n";
              }
            }
            // add one new line break if its only one file being displayed
            else {
              result = result.concat(currentFile) + "\n";
            }
          } else {
            // if file doesn't exist return error message on the specific file
            System.out.println(
                "Error - Invalid File #" + (file + 1) + ": " + fileNames[file]);
          }

        }
      } catch (InvalidPathException e) {
        // TODO Auto-generated catch block
        // raise error if file name given is not present
        throw new InvalidArgumentException("Error - File(s) not found.\n");

      }
    }
    // return result
    return "\n" + result;
  }

}

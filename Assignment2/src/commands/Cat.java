package commands;


import data.InvalidPathException;

public class Cat implements Command {
  // error message string
  private String emptyArgs = "File(s) not found\n";
  // cat command output string
  String result = "";

  /**
   * Outputs the contents of desired text files in the current directory
   * 
   * @param fs The filesystem in which to make the directory
   * @param params The list of file names to output
   * @return An error if a file cannot be found, and if found output the the
   *         content
   */
  public String execute(data.FileSystem fs, String params) {
    String argNames = params;
    // split the input string at every whitespace and store each word(filename)
    // in an array
    String[] fileNames = argNames.split("\\s+");

    // if no file names are given raise error message
    if (fileNames.length == 0) {
      // raise error
      result = emptyArgs;
    } else {
      // Obtain the object under path in fileSystem
      // If it's null then it would fall into the else block
      try {
        // Formating of output (# of line breaks)
        // iterate through the file names given
        for (int file = 0; file < fileNames.length; file++) {
          // get the file contents for each file
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

        }
      } catch (InvalidPathException e) {
        // TODO Auto-generated catch block
        // raise error if file name given is not present
        result = emptyArgs;
      }
    }
    // return result
    return result;
  }

}

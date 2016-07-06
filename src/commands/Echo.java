package commands;

import java.lang.reflect.InvocationTargetException;

import data.InvalidArgumentException;

public class Echo implements Command {

  /**
   * Outputs a string either to the console or to a file
   * 
   * @param fs The filesystem on which the command is executed
   * @param params The parameters for the command, including the string to
   *        output and the file to output to
   * @return The string to output if it is not output into a file
   */
  public String execute(data.FileSystem fs, String params) {
    String result = "\n";
    params = params.trim();
    // If the (trimmed) parameters contain a >, handle them
    // with a separate method that will deal with the files
    if (params.contains(">")) {
      try {
        echoToFile(fs, params);
      } catch (InvalidArgumentException e) {
        result = "Error - Invalid path\n";
      }
    } else {
      // Otherwise, the parameters themselves will be returned (+ a newline)
      result = params + "\n";
    }
    return result;
  }

  /**
   * Handles echoing to files
   * 
   * @param fs The filesystem to use
   * @param params The parameters given to the command
   * @throws InvalidArgumentException
   * @see execute
   */
  private void echoToFile(data.FileSystem fs, String params)
      throws InvalidArgumentException {
    // Get the index of the first >
    int redirectIndex = params.indexOf('>');
    // Everything up to it is the string to be echoed (trim it as well)
    String strToEcho = params.substring(0, redirectIndex).trim();
    // Store the substring starting with it as well.
    String redirectInfo = params.substring(redirectIndex);
    // If this last string starts with >>, we will append to the file.
    // Also, remove these and trim the substring to get the file path.
    String methodNameToUse;
    if (redirectInfo.startsWith(">>")) {
      methodNameToUse = "appendToFile";
      redirectInfo = redirectInfo.substring(2).trim();
    } else {
      // If it starts with >, we will overwrite the file.
      // Also, remove it and trim the substring to get the file path.
      methodNameToUse = "overwriteFile";
      redirectInfo = redirectInfo.substring(1).trim();
    }
    // Call the relevant method on the filesystem using the new contents
    try {
      java.lang.reflect.Method methodToUse = fs.getClass()
          .getMethod(methodNameToUse, "".getClass(), "".getClass());
      methodToUse.invoke(fs, redirectInfo, strToEcho);
    } catch (IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException
        | SecurityException e) {
      throw new data.InvalidArgumentException("Could not create file");
    }

  }
}

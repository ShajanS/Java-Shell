package commands;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class CommandHandler {

  // These constants, the outputMode attribute, and determineOutputDirection
  // are used to determine what to do with a command's output when it runs
  private final char OVERWRITE_MODE = 'o';
  private final char APPEND_MODE = 'a';
  private final char STDOUT_MODE = 's';

  private char outputMode;
  private String outputLocation;

  // See if there are any output redirection tokens in the parameters.
  // If so, change to the appropriate output mode and return the parameters
  // without the redirection.
  public String determineOutputDirection(String params) {
    // Check if there are any > in the parameters.
    if (params.contains(">")) {
      // If so, split the string in two at the first one's index
      int firstBracketLocation = params.indexOf('>');
      // The first part will be the parameters without redirection,
      // The second will be the redirection type and output location.
      String redirectInfo = params.substring(firstBracketLocation).trim();
      params = params.substring(0, firstBracketLocation).trim();
      int pathStart;
      // If the second part starts with >>,
      if (redirectInfo.startsWith(">>")) {
        // Set to append mode
        outputMode = APPEND_MODE;
        // Note where the rest of the string starts
        pathStart = 2;
      } else {
        // If not, it must start with >.
        // Set to overwrite mode
        outputMode = OVERWRITE_MODE;
        // Note where the rest of the string starts
        pathStart = 1;
      }
      // Get the rest of the string, trim it, and
      // set the output location to it
      outputLocation = redirectInfo.substring(pathStart).trim();
    } else {
      // If not, set to standard output mode,
      outputMode = STDOUT_MODE;
      // and there should be no output location.
      outputLocation = null;
    }
    // Return the parameters without the redirection info
    return params;
  }

  // Handling the output. Returns anything that needs to be printed to the
  // shell.
  public String handleOutput(data.FileSystem fs, String output) {
    // If in standard output mode, simply return the string.
    if (outputMode == STDOUT_MODE) {
      return output;
    } else {
      // Otherwise, select the appropriate FileSystem method for the current
      // mode
      String methodNameToUse;
      if (outputMode == APPEND_MODE) {
        methodNameToUse = "appendToFile";
      } else {
        methodNameToUse = "overwriteFile";
      }
      // If the output string is not empty or entirely whitespace,
      if (!output.trim().isEmpty()) {
        // Call the relevant method to put the output string into the given
        // path.
        // If this succeeds, return an empty string.
        // If this fails, return an error message.
        try {
          java.lang.reflect.Method methodToUse = fs.getClass()
              .getMethod(methodNameToUse, "".getClass(), "".getClass());
          methodToUse.invoke(fs, outputLocation, output);
        } catch (NoSuchMethodException | SecurityException
            | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e) {
          return "Error - Could not create file.";
        }
      }
      return "";
    }
  }

  // Getting paths from the parameters
  public static ArrayList<String> getPaths(String params) {
    // Create the result list
    ArrayList<String> result = new java.util.ArrayList<String>();
    // Loop until the parameters are empty
    while (!params.isEmpty()) {
      // If the first character is a quote, look for the next quote.
      if (params.startsWith("\"")) {
        int nextQuote = params.indexOf('"', 1);
        // If there is one, add the substring between them to the list
        if (nextQuote != -1) {
          result.add(params.substring(1, nextQuote));
          // Remove this substing + the quotes and trim the string
          params = params.substring(nextQuote + 1).trim();
        }
      }
      // Look for the next space
      int nextSpace = params.indexOf(' ');
      // If there is one, add everything up to it to the list
      if (nextSpace != -1) {
        result.add(params.substring(0, nextSpace));
        // Remove what was just added from the params and trim them
        params = params.substring(nextSpace + 1).trim();
      } else {
        // If not, add the remainder of params to the list and clear the params
        result.add(params);
        params = "";
      }
    }
    return result;
  }

  public static String[] splitInputIntoCommandAndParams(String input) {
    // Get the substring from the start of the input to the 1st space,
    // or the whole string if there are no spaces
    // Also, get the string of parameters. This is everything after the
    // first space, or an empty string if there isn't one
    int firstSpaceIndex = input.indexOf(' ');
    int paramStart = firstSpaceIndex + 1;
    if (firstSpaceIndex == -1) {
      firstSpaceIndex = input.length();
      paramStart = input.length();
    }
    String firstToken = input.substring(0, firstSpaceIndex);
    String params = input.substring(paramStart);
    // Put these pieces in an array and return them
    String[] result = {firstToken, params};
    return result;
  }


}

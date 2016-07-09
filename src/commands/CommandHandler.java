package commands;

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
  public String determineOutputDirection(String params){
    // Check if there are any > in the parameters.
      // If so, split the string in two at the first one's index
      // The first part will be the parameters without redirection,
      // The second will be the redirection type and output location.
      // If the second part starts with >>,
        // Set to append mode
        // Note where the rest of the string starts
      // If not, it must start with >.
        // Set to overwrite mode
        // Note where the rest of the string starts 
      // Get the rest of the string, trim it, and
      // set the output location to it
      // If not, set to standard output mode,
      // and there should be no output location.
    // Return the parameters without the redirection info
    
    
  }
  
  //Getting paths from the parameters
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

}

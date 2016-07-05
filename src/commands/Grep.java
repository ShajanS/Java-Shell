package commands;

import data.FileSystem;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class Grep implements Command {

  @Override
  public String execute(FileSystem fs, String params) {
    String result = "";
    // Get the first token from the parameters. This will either be the
    // -R flag or the regex.
    params = params.trim();
    String firstToken = params.substring(0, params.indexOf(' '));
    // Create a regex Pattern from the given regex
    Pattern pattern = Pattern.compile(firstToken);
    // Get the list of file paths from the parameters
    String pathsFromParams = params.substring(params.indexOf(' ')).trim();
    ArrayList<String> paths = getPaths(pathsFromParams);
    // For each path,
    for (String path : paths){
      // Try to get the contents of that path
      try{
        String contents = fs.getFileContents(path);
        // Split this by newlines
        String[] lines = contents.split("\n");
        // For each line,
        for (String line : lines){
          // See if the line matches the regex. If so,
          // add it to the result string + a newline
          Matcher matcher = pattern.matcher(line);
          if (matcher.matches()){
            result += line + "\n";
          }
        }
      } catch (data.InvalidPathException e){        
      // If this fails, return an error message
        return "Error - Invalid path\n";
      }
    }
    return result;
  }
  
  // Getting paths from the parameters
  private ArrayList<String> getPaths(String params) {
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

package commands;

import data.InvalidArgumentException;

/**
 * @author Kirill Lossev
 * The class for the echo command, which prints the string it
 * is given.
 */
public class Echo implements Command {

  /**
   * Outputs a string either to the console or to a file
   * 
   * @param fs The filesystem on which the command is executed
   * @param params The string to output
   * @return The string to output
   * @throws InvalidArgumentException If the string is not properly formatted
   */
  public String execute(data.FileSystem fs, String params) throws InvalidArgumentException {
    // If the parameter is valid, return it without the quotes and with
    // a newline. If not, return an error message.
    if (validateParams(params)){
      return params.substring(1, params.length()-1) + "\n";
    } else{
      throw new data.InvalidArgumentException("Error - Malformed parameter\n");
    }
  }
  
  
  /**
   * Checks if the parameters given to echo are valid
   * @param params The parameters given to echo
   * @return true iff params starts and ends with a double quote
   */
  private boolean validateParams(String params){
    //The parameter must be surrounded by single quotes to be valid
    return (params.startsWith("\"") && params.endsWith("\""));
  }
}

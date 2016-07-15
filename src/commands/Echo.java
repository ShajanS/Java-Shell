package commands;

import data.InvalidArgumentException;

public class Echo implements Command {

  /**
   * Outputs a string either to the console or to a file
   * 
   * @param fs The filesystem on which the command is executed
   * @param params The parameters for the command, including the string to
   *        output and the file to output to
   * @return The string to output if it is not output into a file
   * @throws InvalidArgumentException If the string is not properly formatted
   */
  public String execute(data.FileSystem fs, String params) throws InvalidArgumentException {
    // If the parameter is valid, return it without the quotes and with
    // a newline. If not, return an error message.
    if (validateParams(params)){
      return params.substring(1, params.length()-1) + "\n";
    } else{
      throw new data.InvalidArgumentException("Error - Malformed parameter");
    }
  }
  
  //Validate input
  private boolean validateParams(String params){
    //The parameter must be surrounded by single quotes to be valid
    return (params.startsWith("\"") && params.endsWith("\""));
  }
}

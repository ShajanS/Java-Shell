package commands;

import java.util.ArrayList;

import data.InvalidArgumentException;

/**
 * 
 * @author Shajan Sivarajh This class if for the !number command and it recalls
 *         any of previous history by its number
 */
public class HistoryRecall implements Command {

  // set up command map with the names that run them for the method to use
  java.util.HashMap<String, String> commandMap;

  /**
   * recalls any of previous history by its number
   * 
   * @param fs The filesystem to excute commands
   * @param params The wanted command from history list numerical value
   * @return An error if the wanted history value cannot be found, and if found
   *         output the desired commands output
   */
  public String execute(data.FileSystem fs, String params)
      throws InvalidArgumentException {
    // Return string
    String commandreturnString = "";
    // populate the hash map with commands
    commandMap = populateCommandMap();
    // split the input string at every whitespace and store each word
    // in an array
    String[] argNames = params.split("\\s+");
    // get the list of command history
    ArrayList<String> cmdNames = fs.getCommandHistory();
    String searchArg = argNames[0];

    // try and test to see if argument given is a valid number
    // if no return error message
    try {
      @SuppressWarnings("unused")
      int numberCheck = Integer.parseInt(searchArg);
    } catch (NumberFormatException e) {
      throw new InvalidArgumentException("Error - Invalid arguments.\n");
    }

    int number = Integer.parseInt(searchArg);

    // if argument is not with in the history list
    if (number > cmdNames.size()) {
      // return error message
      throw new InvalidArgumentException(
          "Error - Method value not found in history.\n");
    }
    // if argument is with in the values for the history list
    // get the desired command entered at that time
    else {
      String reqMethod = cmdNames.get(number - 1);

      // add the recall of the command to the shell's history
      fs.addCommandToHistory(reqMethod);
      // split the input into command key name and command argument
      // using helper function splitInputIntoCommandAndParams
      String[] exParams =
          CommandHandler.splitInputIntoCommandAndParams(reqMethod);
      // use the parameters and call the desired command
      if (commandMap.containsKey(exParams[0])) {
        String commandName = commandMap.get(exParams[0]);
        commands.Command commObj;
        try {
          commObj = (Command) Class.forName(commandName).newInstance();
          commandreturnString = commObj.execute(fs, exParams[1]);
        } catch (InstantiationException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      // return output of the desired command
      return commandreturnString;
    }

  }

  /**
   * Populates the map of command keywords to command classes
   * 
   * @return The map from command keywords to classes
   */
  private java.util.HashMap<String, String> populateCommandMap() {
    // Add all the mappings from command name to command class to
    // a hashmap and return it
    commandMap = new java.util.HashMap<String, String>();
    commandMap.put("echo", "commands.Echo");
    commandMap.put("mkdir", "commands.Mkdir");
    commandMap.put("history", "commands.History");
    commandMap.put("pwd", "commands.Pwd");
    commandMap.put("cd", "commands.Cd");
    commandMap.put("man", "commands.Man");
    commandMap.put("cat", "commands.Cat");
    commandMap.put("pushd", "commands.Pushd");
    commandMap.put("ls", "commands.ls");
    commandMap.put("popd", "commands.Popd");
    commandMap.put("ls", "commands.Ls");
    commandMap.put("grep", "commands.Grep");
    commandMap.put("Mv", "commands.Mv");
    commandMap.put("!", "commands.HistoryRecall");
    commandMap.put("Cp", "commands.Cp");
    commandMap.put("curl", "commands.Curl");
    return commandMap;
  }

}

package commands;


import java.util.ArrayList;

import data.InvalidArgumentException;
import data.InvalidPathException;


public class Recall implements Command {
  
  public String execute(data.FileSystem fs, String params) throws InvalidArgumentException, InvalidPathException {
    String commandreturnString = "";
    commandMap = populateCommandMap();
    // split the input string at every whitespace and store each word(filename)
    // in an array
    String[] argNames = params.split("\\s+");
    ArrayList<String> cmdNames = fs.getCommandHistory();
    
    String searchArg = argNames[0];
    int number = Integer.parseInt(searchArg);
    String reqMethod = cmdNames.get(number - 1);
    fs.addCommandToHistory(reqMethod);
    String [] exParams = splitInputIntoCommandAndParams(reqMethod);
    
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

    return commandreturnString;
    
  }
  
  java.util.HashMap<String, String> commandMap;

  private java.util.HashMap<String, String> populateCommandMap() {

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
    commandMap.put("![0-9]*", "commands.Recall");
    commandMap.put("Cp", "commands.Cp");
    
    return commandMap;
  }
  
  private String[] splitInputIntoCommandAndParams(String input) {
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
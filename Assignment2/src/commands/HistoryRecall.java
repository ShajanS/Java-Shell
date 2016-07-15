package commands;

import java.util.ArrayList;

import data.InvalidArgumentException;


public class HistoryRecall implements Command {
  
  public String execute(data.FileSystem fs, String params) throws InvalidArgumentException {
    String commandreturnString = "";
    commandMap = populateCommandMap();
    // split the input string at every whitespace and store each word(filename)
    // in an array
    String[] argNames = params.split("\\s+");
    ArrayList<String> cmdNames = fs.getCommandHistory();
    String searchArg = argNames[0];
    
    try {
      @SuppressWarnings("unused")
      int numberCheck = Integer.parseInt(searchArg);
    } catch (NumberFormatException e) {
      return "Invalid argument error\n";
    }
    
    int number = Integer.parseInt(searchArg);
    
    if (number > cmdNames.size()){
      return "Method value not found in history\n";
    }
    else{
      String reqMethod = cmdNames.get(number - 1);

      
      fs.addCommandToHistory(reqMethod);
      String [] exParams = CommandHandler.splitInputIntoCommandAndParams(reqMethod);
      
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
    commandMap.put("!", "commands.Recall");
    commandMap.put("Cp", "commands.Cp");
    commandMap.put("curl", "commands.Curl");
    
    return commandMap;
  }
  
}
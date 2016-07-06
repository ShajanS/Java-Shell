package commands;


import java.util.ArrayList;


public class Recall implements Command {
  
  public String execute(data.FileSystem fs, String params) {
    String argNames = params;
    // split the input string at every whitespace and store each word(filename)
    // in an array
    String[] fileNames = argNames.split("\\s+");
    ArrayList<String> cmdNames = fs.getCommandHistory();
    
    String method = fileNames[0];
    int number = Integer.parseInt(method);

    return cmdNames.get(number -1);
    
  }
  private String recall(data.FileSystem fs, String params){
    return params;
    
  }
  
}
package commands;

import data.File;
import data.InvalidArgumentException;

public class Cat implements Command {
  
  private String emptyArgs = "File not indicated";
  String result = "";
  
  // still in development phase
  
  public String execute(data.FileSystem fs, String params){
    if (params.length() == 0) {
      // raise error
    }
    if (File.getPath() != null) {
      // Obtain the object under path in fileSystem
      // If it's null then it would fall into the else block
      result = File.getContents();
    }
    return result;
  }
}
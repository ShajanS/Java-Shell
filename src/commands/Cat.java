package commands;


import data.InvalidPathException;

public class Cat implements Command {
  
  private String emptyArgs = "File not found";
  String result = "";
  String output = "";
  
  // still in development phase
  
  public String execute(data.FileSystem fs, String params) {

    if (params.length() == 0) {
      // raise error
      output = "Invalid";
    }
    else {
      // Obtain the object under path in fileSystem
      // If it's null then it would fall into the else block
      String current = fs.getCurrentDirectoryPath();
      try {
        result = fs.getFileContents(current) + "\n";
      } catch (InvalidPathException e) {
        // TODO Auto-generated catch block
        result = emptyArgs;
      }
    }
    return result;    
  }

}
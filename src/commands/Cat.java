package commands;


import data.InvalidPathException;

public class Cat implements Command {
  
  private String emptyArgs = "File(s) not found\n";
  String result = "";
  
  // still in development phase
  
  public String execute(data.FileSystem fs, String params) {
    String argNames = params;
    // split the input string at every whitespace and store each word(filename)
    // in an array
    String[] fileNames = argNames.split("\\s+");

    if (fileNames.length == 0) {
      // raise error
      result = emptyArgs;
    }
    else {
      // Obtain the object under path in fileSystem
      // If it's null then it would fall into the else block
      try {
        //Formating of output (# of line breaks)
        for (int file = 0; file < fileNames.length; file++){
          String currentFile = fs.getFileContents(fileNames[file]);
          int length = fileNames.length;
          if (fileNames.length > 1) {
            if (file == length - 1){
              result = result.concat(currentFile) + "\n";
            }
            else{
              result = result.concat(currentFile) + "\n\n\n";
            }
          }
          else{
            result = result.concat(currentFile) + "\n";
          }
          
        }
      } catch (InvalidPathException e) {
        // TODO Auto-generated catch block
        result = emptyArgs;
      }
    }
    return result;    
  }

}
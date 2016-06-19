package commands;

public class Mkdir implements Command{

  public String execute(driver.JShell shell, String params){
    String result = "\n";
    boolean noErrors = true;
    //Grab the current working directory
    data.Directory pwd;
    //Tokenize the parameters
    java.util.StringTokenizer args = new java.util.StringTokenizer(params);
    //While there are still tokens, and there have been no errors,
    while (args.hasMoreTokens() && noErrors){
      //Get the next token
      String currArg = args.nextToken();
      //If the first char is a slash, remove it and start in the shell's root
      //dir
      if (currArg.charAt(0) == '/'){
        pwd = shell.rootDir;
      } else{
      //If not, start in its current dir
        pwd = shell.currDir;
      }
      //Try to navigate to the directory immediately above the name in the token
      data.Directory parent = shell.rootDir.navigateToParent(currArg);
      //If this is not possible (result is null), return an error message
      if (parent == null){
        noErrors = false;
        result = "Error - Specified path is unreachable";
      } else{
      //Otherwise,
        //Get the name of the directory to make. This will be all the
        //chars after the last / (the whole thing if there are none)
        int nameStart = currArg.lastIndexOf('/') + 1;
        String name = currArg.substring(nameStart);
        //If a directory being made already exists, return an error
        if (pwd.getDirectory(name) != null){
          noErrors = false;
          result = "Error - This directory already exists";
        } else{
        //If not, create the directory.    
          pwd.createDirectory(name);
        }
      }
    }
    return result;
  }
  
  
}

package commands;

public class Cd implements Command{

  public String execute(driver.JShell shell, String params){
    String result = "\n";
    //Attempt to navigate to the parent directory of the parameter
    data.Directory base;
    //If the first character is a slash, start from the root.
    if (params.startsWith("/")){
      base = shell.rootDir;
      params = params.substring(1);
    } else{
    //Otherwise, start from the current directory
      base = shell.currDir;
    }
    data.Directory parent = base.navigateToParent(params);
    //If this is possible, look for the specified dir
    if (parent != null){
      //If it exists, set the shell's current dir to that one
      String deepest = params.substring(params.lastIndexOf('/')+1);
      if (parent.getDirectory(deepest) != null){
        shell.currDir = parent.getDirectory(deepest);
      } else{
      //If not, return an error message
        result = "Error - No such directory\n";
      }
    } else{
    //If not, return an error message
      result = "Error - No such directory\n";
    }
    return result;
  }
}

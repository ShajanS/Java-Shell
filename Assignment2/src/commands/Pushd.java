package commands;

import java.util.List;

public class Pushd implements Command{

  public String execute(driver.JShell shell, String params){
    String result = "\n";
    //Get the absolute path of the parameter if possible
    String absPath = shell.currDir.absolutePath(params);
    stack.add(0, shell.currDir.getPath());
    //Navigate to the parent directory of the param if possible
    try{
      data.Directory parent = data.Directory.navigateToParent(absPath,
        shell.rootDir);
      //Look for the specified dir
      //If it exists, set the shell's current dir to that one
      String deepest = params.substring(params.lastIndexOf('/')+1);
      if (parent.getDirectory(deepest) != null){
        shell.currDir = parent.getDirectory(deepest);
      } else{
      //If not, return an error message
        result = "Error - No such directory\n";
      }
	} catch (data.InvalidPathException e){
	  // If this is not possible, return an error message
	  result = "Error - no such directory\n";
	}
    
    return result;
  }
}

package commands;

public class Mkdir implements Command{

  public String execute(driver.JShell shell, String params){
    String result = "\n";
    boolean noErrors = true;
    //Grab the current working directory
    data.Directory pwd;
    //Get the list of directory names to make
    java.util.ArrayList<String> args = names(params);
    //Loop over the length of the list as long as there are no errors,
    for (int i = 0; i < args.size() && noErrors; i++){
      //Get the next dir to make
      String currArg = args.get(i);
      //If the first char is a slash, remove it and start in the shell's root
      //dir
      if (currArg.charAt(0) == '/'){
        pwd = shell.rootDir;
      } else{
      //If not, start in its current dir
        pwd = shell.currDir;
      }
      //Try to navigate to the directory immediately above the name in the token
      data.Directory parent = pwd.navigateToParent(currArg);
      //If this is not possible (result is null), return an error message
      if (parent == null){
        noErrors = false;
        result = "Error - Specified path is unreachable\n";
      } else{
      //Otherwise,
        //Get the name of the directory to make. This will be all the
        //chars after the last / (the whole thing if there are none)
        int nameStart = currArg.lastIndexOf('/') + 1;
        String name = currArg.substring(nameStart);
        //If a directory being made already exists, return an error
        if (pwd.getDirectory(name) != null){
          noErrors = false;
          result = "Error - This directory already exists\n";
        } else{
        //If not, create the directory in the appropriate parent.
          parent.createDirectory(name);
        }
      }
    }
    return result;
  }
  
  //Converts parameters to an arraylist of directory names to be created
  private java.util.ArrayList<String> names (String params){
    //Create the result list
    java.util.ArrayList<String> result = new java.util.ArrayList<String>();
    //Loop until the parameters are empty
    while (!params.isEmpty()){
      //If the first character is a quote, look for the next quote.
      if (params.startsWith("\"")){
        int nextQuote = params.indexOf('"', 1);
        // If there is one, add the substring between them to the list
        if (nextQuote != -1){
          result.add(params.substring(1, nextQuote));
        // Remove this substing + the quotes and trim the string
          params = params.substring(nextQuote+1).trim();
        }
      }
      //Look for the next space
      int nextSpace = params.indexOf(' ');
      //If there is one, add everything up to it to the list
      if (nextSpace != -1){
        result.add(params.substring(0, nextSpace));
      //Remove what was just added from the params and trim them
        params = params.substring(nextSpace+1).trim();
      } else{
      //If not, add the remainder of params to the list and clear the params
        result.add(params);
        params = "";
      }
    }
    return result;
  }
  
}

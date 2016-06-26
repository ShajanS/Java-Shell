package commands;

public class Mkdir implements Command{

  public String execute(data.FileSystem fs, String params){ 
    //Get the list of directory names to make
    java.util.ArrayList<String> args = names(params);
    //Loop over the elements of the list,
    try{
      for (String dirToMake : args){
        //If the string contains a slash, get the substring up to the last one
        //This is the parent directory
        String parentName;
        if (dirToMake.contains("/")){
          parentName = dirToMake.substring(0, dirToMake.lastIndexOf('/'));
        } else {
        //If not, the parent directory will be the current one (empty string)
          parentName = "";
        }
        //Everything after the last slash is the name of the new dir
        String dirName = dirToMake.substring(dirToMake.lastIndexOf('/')+1);
        //Get the parent directory if possible
        data.Directory parentDir = fs.getDirectory(parentName);
        //Create a new directory with the given name inside it
        parentDir.createDirectory(dirName);
              }
    } catch(data.InvalidPathException e){
    //If an exception is thrown, return an error
      return "Error - invalid path\n";
    }
    //If everything goes smoothly, return a newline
    return "\n";
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

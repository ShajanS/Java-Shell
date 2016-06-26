package commands;

public class History implements Command{
  
  /**
   *  Returns a list of the last n commands given to the filesystem, where n
   *  is given by the user.
   *  
   *  @param fs     The filesystem whose commands will be returned
   *  @param params How many commands to return, or all of them if this is
   *                empty
   *  @return       A list of the last n commands given to the filesystem
   */
  public String execute(data.FileSystem fs, String params){
    //Create the result string
    String result = "";
    //Get the command history from the shell
    java.util.ArrayList<String> hist = fs.getCommandHistory();
    
    //Trim the parameter string and try to get the start index from it
    int startIndex = startIndexFromParam(params.trim(), hist.size());
    
    //If the start index is not -1,
    if (startIndex != -1){
    //Loop over the length of the history array, starting at the start index
      for (int i = startIndex; i < hist.size(); i++){
        //Append the counter with the relevant command and a newline to the
        //result string
        result += (i+1) + ". " + hist.get(i) + "\n";      
      }
    } else{
      //Otherwise, return an error message
      result = "Error - Invalid Parameter";
    }
    return result;
     
  }
  
  
  /**
   * Returns the start index specified by the parameter, or -1 if invalid
   * @param param     The parameter given to the history command
   * @param arraySize How large the list of commands given is
   * @return          The index in the command list to start at in order to
   *                  output as many commands as desired
   */
  private int startIndexFromParam(String param, int arraySize){
    int startIndex;
    //If the string is empty, the start index is 0
    if (param.isEmpty()){
      startIndex = 0;
    } else{
    //If not, try to parse it to an int
      try{
        int paramInt = Integer.parseInt(param);
    //If this succeeds, the start index is the greater of 0 and
    //the arraylist size minus the parameter
        startIndex = Math.max(0, arraySize - paramInt);
      } catch(NumberFormatException e){
    //If this fails, return -1.
        startIndex = -1;
      }
    }
    return startIndex;
  }

}

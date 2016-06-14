// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name:
// UT Student #:
// Author:
//
// Student2:
// UTORID user_name:
// UT Student #:
// Author:
//
// Student3:
// UTORID user_name:
// UT Student #:
// Author:
//
// Student4:
// UTORID user_name:
// UT Student #:
// Author:
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import java.util.Scanner;

public class JShell {
  /*TODO: The methods associated with commands should take a String as their
  *parameter, and return a string to be printed. This will be done with
  *System.out.print(), so if there needs to be a newline at the end
  *it should be added by by the method.*/
  
  //An arraylist to hold command history
  java.util.ArrayList<String> commands = new java.util.ArrayList<String>();
  
  private JShell(){
    String outputString;
    //Get input from the user
    //Trim the input string
    Scanner in = new Scanner(System.in);
    String inputString = in.nextLine().trim();
    //Check if the input string starts with a currently implemented command
    //If so, call the relevant method with the rest of the string as its
    //parameter and store its output.
    if (inputString.startsWith("echo")){
      outputString = echo(inputString.substring("echo".length()));
    }
    else if (inputString.startsWith("history")){
      outputString = history(inputString.substring("history".length()));
    }
    //If not, output an error message
    else{
      outputString = "Error: Invalid command\n";
    }
    //Print the string returned by the method.
    System.out.print(outputString);
    
    //Close the Scanner. Note: once the loop is implemented, this must be
    //outside of it.
    in.close();
  }
  
  private String echo(String params){
    //Trim the string and return it, with a newline at the end.
    return params.trim() + "\n";
  }
  
  private String history(String params){
    //The starting index for command history will be 0 by default.
    int startIndex = 0;
    String result = "";
    //If there is a parameter, trim it and attempt to convert it to an int.
    //If this cannot be done, the parameters are invalid. Return an
    //error message.
    if (!params.trim().isEmpty()){
      //If the parameter can be converted to an nonnegative int n, and is
      //less than the length of the ArrayList, set the start index such that
      //the last n elements are printed.
      try{
        int paramInt = Integer.parseInt(params.trim());
        if (paramInt >= 0 && paramInt < commands.size()){
          startIndex = commands.size() - paramInt;
        }
      } catch (NumberFormatException e){
        return "Error - Invalid parameter";
      }
    }
    //Combine the relevant elements of the command history into a list and
    //return it.
    for (int i = startIndex; i < commands.size(); i++){
      result += i + ". " + commands.get(i) + "\n";
    }
    return result;
  }
  
  public static void main(String[] args) {
    new JShell();

  }

}

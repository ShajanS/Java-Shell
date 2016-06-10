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
      outputString = echo(inputString.substring(4)); //since echo is 4 chars
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
  
  public static void main(String[] args) {
    new JShell();

  }

}

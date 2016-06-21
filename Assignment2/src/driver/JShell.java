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

import commands.Command;

public class JShell {
  
  // A hashmap to map commands to the names of the classes that run them
  java.util.HashMap<String, String> commandMap;
  // An arraylist to hold command history
  public java.util.ArrayList<String> commands;
  // The current working directory
  public data.Directory currDir;
  public data.Directory rootDir;
  
  public boolean continueLoop;

  private JShell() {
    // Initializing instance attributes
    commands = new java.util.ArrayList<String>();
    rootDir = new data.Directory();
    currDir = rootDir;
    continueLoop = true;
    Scanner in = new Scanner(System.in);
    // Populate the command map
    commandMap =
        new java.util.HashMap<String, String>();
    commandMap.put("echo", "commands.Echo");
    commandMap.put("mkdir","commands.Mkdir");
    commandMap.put("history", "commands.History");
    
    String outputString = "";
    // Until the exit command is used,
    while (continueLoop){
      // Get input from the user
      // Trim the input string
      String inputString = in.nextLine().trim();
      // Add the input to the command history
      commands.add(inputString);
      // Get the substring from the start of the input to the 1st space,
      // or the whole string if there are no spaces
      // Also, get the string of parameters. This is everything after the
      // first space, or an empty string if there isn't one
      int firstSpaceIndex = inputString.indexOf(' ');
      int paramStart = firstSpaceIndex + 1;
      if (firstSpaceIndex == -1){
        firstSpaceIndex = inputString.length();
        paramStart = inputString.length();
      }
      String firstToken = inputString.substring(0, firstSpaceIndex);
      String params = inputString.substring(paramStart);
      
      // If this substring is in the command map, return its output with the
      // rest of the string as its parameter
      if (commandMap.containsKey(firstToken)){
         String commandName = commandMap.get(firstToken);
         commands.Command commObj;
        try {
          commObj = (Command) Class.forName(commandName).newInstance();
          outputString = commObj.execute(this, params);
        } catch (InstantiationException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      // If the substring starts with exit 
      // ask user if they wanted to exit 
      // end loop for searching for users input and terminate if yes
      // continue search if no
      else if (inputString.equals("exit")){
        Scanner exit = new Scanner(System.in);
        System.out.print("Are you sure you want to terminate this session? [Y/N]\n");
        String exitString = exit.nextLine().trim();
        if (exitString.equals("Y") || exitString.equals("y")){
          outputString = "Terminated";
          continueLoop = false;
        }
        else{
          continue;
        }
        exit.close();
        
      }
      else {
      // If not, return an error message.
        outputString = "Error - Invalid Command\n";
      }
      // Print the string returned by the method.
      System.out.print(outputString);
    }

    // Close the Scanner. Note: once the loop is implemented, this must be
    // outside of it.
    in.close();
  }


  public static void main(String[] args) {
    new JShell();

  }

}

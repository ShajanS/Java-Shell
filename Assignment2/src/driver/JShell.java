// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: lossevki
// UT Student #: 1002475001
// Author: Kirill Lossev
//
// Student2:
// UTORID user_name: sivara57
// UT Student #: 1001732002
// Author: Shajan Sivarajah
//
// Student3:
// UTORID user_name: odurilak
// UT Student #: 1002487800
// Author: Sai Oduri
//
// Student4:
// UTORID user_name: kabaniza
// UT Student #: 1002343092
// Author: Zain Kabani
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
import java.util.regex.Pattern;

import javax.activation.CommandMap;

import commands.Command;
import data.InvalidArgumentException;

/**
 * The JShell, which allows a user to manipulate a file system by
 * typing in commands.
 */
@SuppressWarnings("unused")
public class JShell { 

  // The filesystem to use
  private data.FileSystem fs;
  // A hashmap to map commands to the names of the classes that run them
  java.util.HashMap<String, String> commandMap;
  // The CommandHandler to control where output ends up
  commands.CommandHandler ch = new commands.CommandHandler();

  public boolean continueLoop;
  public boolean checkType;

  /**
   * The constructor for the JShell
   */
  private JShell() {
    continueLoop = true;
    checkType = true;

    Scanner in = new Scanner(System.in);

    // Get the filesystem
    fs = data.JFileSystem.getFileSysReference();

    // Populate the command map
    commandMap = populateCommandMap();

    String outputString = "";
    // Until the exit command is used,
    while (continueLoop) {
      // Get input from the user
      // Trim the input string
      String inputString = in.nextLine().trim();
      // Add the input to the command history
      if (inputString.startsWith("!")) {
      }
      else{
        fs.addCommandToHistory(inputString);
      }
      // Split the input into the command and parameters
      String[] splitInput = splitInputIntoCommandAndParams(inputString);
      // Give the parameters to the command handler to determine where its
      // output should go
      splitInput[1] = ch.determineOutputDirection(splitInput[1]);
      
      // Regex for !number method
      // If user input starts with a !
      if (inputString.startsWith("!")){
        String [] parts = inputString.split("!");
        splitInput[0] = "!";
        splitInput[1] = parts[1];
        if (splitInput[1].startsWith(" ") == true){
          fs.addCommandToHistory(inputString);
          splitInput[1] = "unkown";
          splitInput[0] = "unknow";
        }
      }
      
      // If the command is in the command map, return its output with the
      // parameters given as its parameter
      if (commandMap.containsKey(splitInput[0])) {
        String commandName = commandMap.get(splitInput[0]);
        commands.Command commObj;
        try {
          commObj = (Command) Class.forName(commandName).newInstance();
          outputString = commObj.execute(fs, splitInput[1]);
          // Give the string to the output handler, and print whatever it
          // returns
          System.out.print(ch.handleOutput(fs, outputString));
        } catch (InvalidArgumentException e) {
          // If the command throws an exception, print its message.
          System.out.println(e.getMessage());
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
      // break the loop
      else if (inputString.equals("exit")) {
        outputString = "";
        continueLoop = false;
        in.close();
      } else {
        // If not, print an error message.
        System.out.println(
            "ERROR: Invalid Command Name -> " + "'" + inputString + "'" + "\n");
      }
      
    }
    // Close the Scanner. Note: once the loop is implemented, this must be
    // outside of it.
    in.close();
  }

  /**
   * Populates the map of command keywords to command classes
   * 
   * @return The map from command keywords to classes
   */
  private java.util.HashMap<String, String> populateCommandMap() {
    // Add all the mappings from command name to command class to
    // a hashmap and return it
    commandMap = new java.util.HashMap<String, String>();
    commandMap.put("echo", "commands.Echo");
    commandMap.put("mkdir", "commands.Mkdir");
    commandMap.put("history", "commands.History");
    commandMap.put("pwd", "commands.Pwd");
    commandMap.put("cd", "commands.Cd");
    commandMap.put("man", "commands.Man");
    commandMap.put("cat", "commands.Cat");
    commandMap.put("pushd", "commands.Pushd");
    commandMap.put("ls", "commands.ls");
    commandMap.put("popd", "commands.Popd");
    commandMap.put("ls", "commands.Ls");
    commandMap.put("grep", "commands.Grep");
    commandMap.put("Mv", "commands.Mv");
    commandMap.put("!", "commands.HistoryRecall");
    commandMap.put("Cp", "commands.Cp");
    commandMap.put("curl", "commands.Curl");
    return commandMap;
  }

  /**
   * Splits the string entered by the user into the command keyword and the
   * command parameters into an array of length 2
   * 
   * @param input The user's input
   * @return The array containing the keyword and parameters
   */
  private String[] splitInputIntoCommandAndParams(String input) {
    // Get the substring from the start of the input to the 1st space,
    // or the whole string if there are no spaces
    // Also, get the string of parameters. This is everything after the
    // first space, or an empty string if there isn't one
    int firstSpaceIndex = input.indexOf(' ');
    int paramStart = firstSpaceIndex + 1;
    if (firstSpaceIndex == -1) {
      firstSpaceIndex = input.length();
      paramStart = input.length();
    }
    String firstToken = input.substring(0, firstSpaceIndex);
    String params = input.substring(paramStart);
    // Put these pieces in an array and return them
    String[] result = {firstToken, params};
    return result;
  }

  private static boolean whiteSpaceCheck(String inputPart){
    if(inputPart != null){
      for(int i = 0; i < inputPart.length(); i++){
        if(Character.isWhitespace(inputPart.charAt(i))){
          return true;
        } 
      }
    }
    return false;
  }

  /**
   * The main function for JShell and the entry point into the program, simply
   * creates a new JShell.
   * 
   * @param args Command-line arguments
   */
  public static void main(String[] args) {
    new JShell();

  }

}

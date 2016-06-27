package data;

// A class to handle when a user calls on a command that doesn't exist.
@SuppressWarnings("serial")
public class InvalidCommandException extends Exception{
  
  public InvalidCommandException(String message){
    super(message);
  }
    
}
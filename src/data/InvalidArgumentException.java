package data;

// A class to handle when a user calls on a command accompanied by an
// invalid argument.
@SuppressWarnings("serial")
public class InvalidArgumentException extends Exception {

  public InvalidArgumentException(String message) {
    super(message);
  }

}

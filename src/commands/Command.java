package commands;

public interface Command {

  /*
   * Each command will need a method that will execute it. This method will need
   * access to data stored by the filesystem, and any parameters passed in. It
   * will return a string containing the command's output.
   */
  /**
   * Executes a command on the filesystem, with params as its parameters
   * 
   * @param fs The filesystem on which the command is executed
   * @param params The parameters for the command
   * @return The output of the command
   */
  public String execute(data.FileSystem fs, String params)
      throws data.InvalidArgumentException;
}

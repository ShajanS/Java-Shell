package commands;

import data.InvalidPathException;

/**
 * @author Kirill Lossev The interface for the command classes, which all take a
 *         filesystem and command arguments as its parameters, and either
 *         returns the output of the command or an error message if it fails
 */
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
   * @throws data.InvalidArgumentException if the command cannot run with the
   *         given arguments.
   * @throws InvalidPathException
   */
  public String execute(data.FileSystem fs, String params)
      throws data.InvalidArgumentException;
}

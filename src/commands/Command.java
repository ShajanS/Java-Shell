package commands;

public interface Command {

  /*Each command will need a method that will execute it.
  *This method will need access to data stored by the shell, and any
  *parameters passed in. It will return a string containing the
  *command's output.*/
  public String execute(driver.JShell shell, String params);
}

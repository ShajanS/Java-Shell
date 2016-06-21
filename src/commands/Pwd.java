package commands;

public class Pwd implements Command{

  public String execute(driver.JShell shell, String params){
    //Get the path of the shell's current directory and return it
    return shell.currDir.getPath() + "\n";
  }
}

package commands;

public class Pwd implements Command{

  public String execute(data.FileSystem fs, String params){
    //Get the path of the shell's current directory and return it
    return fs.getCurrentDirectoryPath() + "\n";
  }
}

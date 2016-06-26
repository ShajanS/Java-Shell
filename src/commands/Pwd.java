package commands;

public class Pwd implements Command{

  /**
   * Returns the current working directory of the filesystem
   * @param fs     The filesystem whose current working directory to return
   * @param params The parameters for the command, though it does not need any
   * @return       The absolute path of the current working directory
   */
  public String execute(data.FileSystem fs, String params){
    //Get the path of the shell's current directory and return it
    return fs.getCurrentDirectoryPath() + "\n";
  }
}

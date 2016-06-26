package commands;

import data.InvalidPathException;

public class Cd implements Command{

  public String execute(data.FileSystem fs, String params){
    String result = "\n";
    //Make the given parameter the current directory of the filesystem
    try {
      fs.makeCurrentDirectory(params);
    } catch (InvalidPathException e) {
    //If it cannot be found, return an error message
      result = "Error - Directory does not exist";
    }
    return result;
  }
}

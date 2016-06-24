package commands;

import data.File;
import data.InvalidArgumentException;

public class Cat implements Command {
  
  private String emptyArgs = "File not indicated";
  
  // still in development phase
  
  public String execute(driver.JShell shell, String params) throws InvalidArgumentException {
    params = params.trim();
    
    if (params.length() == 0) {
      // raise error
      throw new InvalidArgumentException(emptyArgs);
    }
    else {
      return File.getContents();
    }
  }
}
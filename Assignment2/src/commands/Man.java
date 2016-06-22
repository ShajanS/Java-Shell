package commands;

import java.util.Hashtable;

public class Man implements Command{
  //temp setup 
  //
  public String execute(driver.JShell shell, String params){
    params = params.trim();
    String result = "";
    
    String n = cmdman.get(params);
    if (n!= null) {
      result = (params + n + "\n");
    }
    else{
      result = ("Invalid\n");
    }
    

    return result;
    
  }
  private Hashtable<String, String> cmdman = new Hashtable<String, String>();
  
  public Man() {
    cmdman.put("exit", ": sample output.");
    cmdman.put("cd", ": sample output.");
    cmdman.put("echo", ": sample output.");
    cmdman.put("history", ": sample output.");
    cmdman.put("mkdir", ": sample output.");
    cmdman.put("pwd", ": sample output.");
    cmdman.put("ls", ": sample output.");
    cmdman.put("cat", ": sample output.");
    cmdman.put("man", ": sample output.");
    
  }
  
  
}






package commands;

import java.util.Hashtable;

public class Man implements Command{
  /** 
   * Outputs man pages(documentation) for the desired command
   * @param fs     The filesystem in which to make the directory
   * @param params The command name
   * @return       An error if the command cannot be found, and if 
   *               found output the documentation
   */
  public String execute(data.FileSystem fs, String params){
    params = params.trim();
    String result = "";
    
    String n = cmdman.get(params);
    if (n!= null) {
      result = (params+":" + n + "\n");
    }
    else{
      result = ("Invalid\n");
    }
    

    return result;
    
  }
  private Hashtable<String, String> cmdman = new Hashtable<String, String>();
  
  /** 
   * Hashtable holding all documentation linked to each command 
   */
  public Man() {
    cmdman.put("exit", "\n\texits the program");
    
    cmdman.put("cd", "\n\tChange directory to a requested directory");
    
    cmdman.put("echo", "\n\tIf OUTFILE is not given: Prints the provided string on the JavaShell"
        +"\n\tIf OUTFILE is given accompanied by >, (> OUTFILE): Exports the provided string to the OUTFILE"
        +"\n\t\t- If the OUTFILE already exist it erases the old contents and adds the new"
        +"\n\t\t- If the OUTFILE does not exist it creates a new OUTFILE"
        +"\n\tIf If OUTFILE is given accompanied by >>, (>> OUTFILE): Same concept as (> OUTFILE) but appends instead of overwriting");
    
    cmdman.put("history", "\n\tPrints out the recent commands a user has entered");
    
    cmdman.put("mkdir", "\n\tCreates directories named as the given arguments");
    
    cmdman.put("pwd", "\n\tPrints the current working direcotry");
    
    cmdman.put("ls", "\n\t If P(PATH) is not given: Prints the contents allocated in the current directory. "
        + "\n\t\t- If P(PATH) is a file: Prints the path"
        + "\n\t\t- If P(PATH) is a directory: Prints a the path followed by the contents allocated to that directory");
    
    cmdman.put("cat", "Outputs the contents of desired file(s) in series");
    
    cmdman.put("pushd", "\n\tSaves the current working directory by +"
        + "pushing onto directory stack and then changes the new current "
        + "working directory to DIR. ");
    
    cmdman.put("popd", ": Remove the top entry from the directory stack, and cd into it.");
    
    cmdman.put("man", "\n\tPrints the man pages(documentation) for commands");
    
    
  }
  
  
}





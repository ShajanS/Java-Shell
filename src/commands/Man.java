package commands;

import java.util.Hashtable;

import data.InvalidArgumentException;

/**
 * 
 * @author Shajan Sivarajah This class is for the Man command and it Outputs man
 *         pages(documentation) for the desired command
 */
public class Man implements Command {
  /**
   * Outputs man pages(documentation) for the desired command
   * 
   * @param fs The filesystem in which to output documentation
   * @param params The command name
   * @return An error if the command cannot be found, and if found output the
   *         documentation
   */
  public String execute(data.FileSystem fs, String params)
      throws InvalidArgumentException {
    params = params.trim();
    String result = "";
    // Formating of the output result
    String commandName = cmdman.get(params);
    if (commandName != null) {
      result = (params + ":" + commandName + "\n");
    }
    // if no arguments are given raise error
    else {
      throw new InvalidArgumentException("Error - Invalid arguments.\n");
    }

    return result;
  }

  // Create a hashmap to store all the documentation linked to each command
  private Hashtable<String, String> cmdman = new Hashtable<String, String>();

  /**
   * Hashtable holding all documentation linked to each command
   */
  // populate the hashmap will the commands
  public Man() {
    // exit command
    cmdman.put("exit", "\n\texits the program");
    // cd command
    cmdman.put("cd", "\n\tChange directory to a requested directory"
        + "\n\n\tArgument REQ: cd[DIR]");
    // echo command
    cmdman.put("echo",
        "\n\tIf OUTFILE is not given: Prints the provided string on the "
            + "JavaShell"
            + "\n\tIf OUTFILE is given accompanied by >, (> OUTFILE): "
            + "Exports the provided string to the OUTFILE"
            + "\n\t\t- If the OUTFILE already exist it erases the old contents"
            + " and adds the new"
            + "\n\t\t- If the OUTFILE does not exist it creates a new OUTFILE"
            + "\n\tIf If OUTFILE is given accompanied by >>, (>> OUTFILE): "
            + "Same concept as (> OUTFILE) but appends instead of overwriting"
            + "\n\n\tArgument REQ: echo[STRING] || echo[STRING][OUTFILE] ");
    // history command
    cmdman.put("history",
        "\n\tPrints out the recent commands a user has entered"
            + "\n\tIf command is followed by a number, the "
            + "output is truncated accordingly" + "\n\n\tSample Output: history"
            + "\n\t\t1. cd .." + "\n\t\t2. mkdir textfolder"
            + "\n\t\t3. echo \"hello world\""
            + "\n\t\t4. echo \"hello world\" > textfile" + "\n\t\t5. history"
            + "\n\n\tSample Output: history 4" + "\n\t\t3. echo \"hello world\""
            + "\n\t\t4. echo \"hello world\" > textfile" + "\n\t\t5. history"
            + "\n\t\t6. hisotry 4");
    // mkdir command
    cmdman.put("mkdir", "\n\tCreates directories named as the given arguments"
        + "\n\n\tArgument REQ: mkdir[DIR]");
    // pwd command
    cmdman.put("pwd", "\n\tPrints the current working direcotry");
    // ls command
    cmdman.put("ls",
        "\n\t If P(PATH) is not given: Prints the contents "
            + "allocated in the current directory. "
            + "\n\t\t- If P(PATH) is a file: Prints the path"
            + "\n\t\t- If P(PATH) is a directory: Prints a the path "
            + "followed by the contents allocated to that directory"
            + "\n\n\tArgument REQ: ls[PATH...]");
    // cat command
    cmdman.put("cat",
        "\n\tOutputs the contents of desired file(s) in series"
            + "\n\n\tArgument REQ: cat[FILE1]"
            + "\n\tArgument REQ (File(s)): cat[FILE1][FILE2]..."
            + "\n\tArgument REQ (File(s)): cat[FILE1][DIR]...");
    // pushd command
    cmdman.put("pushd",
        "\n\tSaves the current working directory by +"
            + "pushing onto directory stack and then changes the new current "
            + "working directory to DIR. " + "\n\n\tArgument REQ: pushd[DIR]");
    // pop command
    cmdman.put("popd",
        ": Remove the top entry from the directory stack, and cd into it."
            + "\n\n\tArgument REQ: popd[DIR]");
    // man command
    cmdman.put("man", "\n\tPrints the man pages(documentation) for commands"
        + "\n\n\tArgument REQ: man[CMD]");
    // !number command
    cmdman.put("!",
        "\n\tRecalls any previous history entries by its number "
            + "preceded by an exclamation point (!)"
            + "\n\n\tArgument REQ: ![NUMBER]");
    // Grep command
    cmdman.put("grep",
        "\n\t Searches files for lines matching with inputed "
            + "regex. If -R is not provided print any lines associated with the "
            + "regex. If -R is provided restrict search to the parameter given "
            + "\n\n\tArgument REQ: grep[-R] REGEXPATH");
    // Mv command
    cmdman.put("mv", "\n\tMoves item from OLDPATH to NEWPATH, if NEWPATH is "
        + "DIR; move to DIR" + "\n\n\tArgument REQ: mv[OLDPATH][NEWPATH]");
    // Curl command
    cmdman.put("curl",
        "\n\tRetrieve the file at that URL and add "
            + "it to the current working directory."
            + "\n\n\tArgument REQ: curl[URL]");
    // Cp command
    cmdman.put("cp",
        "\n\tCopies item from OLDPATH to NEWPATH, if OLDPATH is "
            + "DIR; Copy items recursivley"
            + "\n\n\tArgument REQ: cp[OLDPATH][NEWPATH]");

  }

}

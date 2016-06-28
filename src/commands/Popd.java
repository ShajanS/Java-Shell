package commands;

import data.InvalidPathException;
import java.util.EmptyStackException

public class Popd implements Command {

  /**
   * Changes the current working directory of the filesystem to the directory
   * that is at the top of the Directory Stack
   * 
   * @param fs The filesystem whose current directory to change
   * @return An error if the directory cannot be found or the directory stack
   * is empty, a newline otherwise
   */
  public String execute(data.FileSystem fs, String params){
    String result = "\n";
    // Pop from the directory stack and store the path
    String path = new String();
    try{
    	path = fs.popFromDirStack();
    } catch (EmptyStackException e){
    	// If popping from the stack fails, return an error message
    	result = "Error - Cannot pop from Empty Stack\n";
    }
    // Make the stored path the current directory of the filesystem
    try {
    	fs.makeCurrentDirectory(path);
    } catch (InvalidPathException e) {
      // If it cannot be found, return an error message
      result = "Error - Directory does not exist\n";
    }
    return result;
  }
}

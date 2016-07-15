package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import data.FileSystem;
import data.InvalidArgumentException;
import data.InvalidPathException;

/**
 * @author Kirill Lossev
 * The class for the curl command, which retrieves a file at a specified URL
 */
public class Curl implements Command {

  /** (non-Javadoc)
   * @param fs The filesystem to operate on
   * @param params The URL to get the contents at
   * @return A newline on success
   * @throws data.InvalidArgumentException if the file cannot be
   *                                        retrieved.
   */
  @Override
  public String execute(FileSystem fs, String params)
      throws InvalidArgumentException {
    // If the URL is properly formatted (and if it isn't, the URL object
    // will complain at some point), the filename will be everything after the
    // last slash.
    String filename = params.substring(params.lastIndexOf('/')+1);
    // Create a URL object from the parameter
    URL fileToRead;
    try {
      fileToRead = new URL(params);
    } catch (MalformedURLException e) {
    // If there is a problem reading from the URL, return an error message.
      throw new data.InvalidArgumentException("Error - Invalid URL.\n");
    }
    // Read the contents at that URL as a string,
    InputStreamReader stream;
    try {
      stream = new InputStreamReader(fileToRead.openStream());
    } catch (IOException e) {
      // If there is a problem reading from the URL, return an error message.
      throw new data.InvalidArgumentException("Error - Could not read from "
          + "URL.\n");
    }
    BufferedReader in = new BufferedReader(stream);
    String fileContents = "";
    String nextLine;
    try {
      while ((nextLine = in.readLine()) != null){
        fileContents += nextLine + "\n";
      }
    } catch (IOException e) {
      // If there is a problem reading from the URL, return an error message.
      throw new data.InvalidArgumentException("Error - Could not "
          + "read from URL.\n");
    }
    // and write that string to a file with the previously found
    // filename.
    try {
      fs.overwriteFile(filename, fileContents);
    } catch (InvalidPathException e) {
      // The file should be created in the current directory, so this should
      // never cause an error. If it does, the problem is likely in FileSystem
      throw new data.InvalidArgumentException("I just don't know what went "
          + "wrong :(\n");
    }
    // If everything goes smoothly, return a newline.
    return "\n";
  }

}

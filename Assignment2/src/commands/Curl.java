package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import data.FileSystem;
import data.InvalidPathException;

public class Curl implements Command {

  @Override
  public String execute(FileSystem fs, String params) {
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
      return "Error - Invalid URL.\n";
    }
    // Read the contents at that URL as a string,
    InputStreamReader stream;
    try {
      stream = new InputStreamReader(fileToRead.openStream());
    } catch (IOException e) {
      // If there is a problem reading from the URL, return an error message.
      return "Error - Could not read from URL.\n";
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
      return "Error - Could not read from URL.\n";
    }
    // and write that string to a file with the previously found
    // filename.
    try {
      fs.overwriteFile(filename, fileContents);
    } catch (InvalidPathException e) {
      // The file should be created in the current directory, so this should
      // never cause an error. If it does, the problem is likely in FileSystem.
      return "I just don't know what went wrong :(\n";
    }
    // If everything goes smoothly, return a newline.
    return "\n";
  }

}

package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import data.InvalidPathException;
import data.JFileSystem;

/**
 * @author Shajan Sivarajah Tests for the cat command
 */

public class CatTest {

  commands.Cat cat = new commands.Cat();
  commands.Echo echo = new commands.Echo();
  JFileSystem sys;


  @Before
  public void setUp() {
    // access the JFileSystem allocated in data to accommodate the
    // command calls and command setup for this unit testing
    sys = new JFileSystem();
    // populate the the filesystem with files linked to content
    try {
      sys.appendToFile("file1.txt", "Contents in file 1");
    } catch (InvalidPathException e) {
      // TODO Auto-generated catch block
      fail();
    }
    try {
      sys.appendToFile("file2.txt", "Contents in file 2");
    } catch (InvalidPathException e) {
      // TODO Auto-generated catch block
      fail();
    }
    try {
      sys.appendToFile("file3.txt", "Contents in file 3");
    } catch (InvalidPathException e) {
      // TODO Auto-generated catch block
      fail();
    }

  }

  @Test
  public void testExecuteSingleFile() {
    // passing the command to retrieve a file which is present in the directory
    try {
      assertEquals(sys.getFileContents("file1.txt"), "Contents in file 1");
    } catch (InvalidPathException e) {
      // TODO Auto-generated catch block
      fail();
    }
  }

  @Test
  public void testExecuteMultipleFile() {
    // passing the command to retrieve multiple files which is present
    // in the directory
    // format between the two files should be spaced out appropriately
    try {
      assertEquals(sys.getFileContents("file3.txt"), "Contents in file 3");
      assertEquals(sys.getFileContents("file2.txt"), "Contents in file 2");
    } catch (InvalidPathException e) {
      // TODO Auto-generated catch block
      fail();
    }


  }

  @Test
  public void testExecuteNoFile() {
    // passing the command to retrieve a files which is not present
    // in the directory, and error message should be displayed accordingly
    try {
      sys.getFileContents(" ");
      fail();
    } catch (InvalidPathException e) {
      // TODO Auto-generated catch block
      assertEquals(e.getMessage(), "No such file");
    }

  }

  @Test
  public void testExecuteNewAppendedFile() {
    // passing the command to retrieve a file which is present but also
    // has been appended new content to add to the file
    try {
      sys.overwriteFile("file1.txt", "New line Hello World");
    } catch (InvalidPathException e) {
      // TODO Auto-generated catch block
      fail();
    }
    try {
      assertEquals(sys.getFileContents("file1.txt"), "New line Hello World");
    } catch (InvalidPathException e) {
      fail();
    }
  }

}

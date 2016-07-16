package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.InvalidArgumentException;

/**
 * @author Kirill Lossev
 * Test cases for the Cd class
 */
public class CdTest {

  commands.Cd cd = new commands.Cd();
  MockFileSystem fs;

  /**
   * The set up method, creates a mockfilesystem with some directories inside
   */
  @Before
  public void setUp() {
    // Create a new mock filesystem with a directory named testdir,
    // whose parent is the root directory
    fs = new MockFileSystem();
    fs.directories.put("testdir",
        new data.Directory("testdir", fs.directories.get("")));
  }

  /**
   * Testing changing to a directory that exists in the filesystem
   */
  @Test
  public void testChangeToExistingDir() {
    // cd should be able to change to testdir and return a newline
    String result;
    try {
      result = cd.execute(fs, "testdir");
      assertEquals(result, "\n");
      // The new current directory should reflect the change
      assertEquals(fs.currDir, "testdir");
    } catch (InvalidArgumentException e) {
      // This shouldn't cause an error
     fail("Error when changing to a valid directory");
    }    
  }

  /**
   * Testing changing to the parent directory
   */
  @Test
  public void testDotPath() {
    // Set the current directory to testdir
    fs.currDir = "testdir";
    // cd should be able to change to the root dir and return a newline
    try {
      assertEquals(cd.execute(fs, ".."), "\n");
    } catch (InvalidArgumentException e) {
      // This shouldn't cause an error
      fail("Error when changing to parent directory");
    }
    // The new current directory should reflect the change
    assertEquals(fs.currDir, "");
  }

  /**
   * Testing changing to a directory that does not exist
   */
  @Test
  public void testChangeToNonExistingDir() {
    // cd should not be able to change to any other directory, and return a
    // error
    try {
      cd.execute(fs, "somedir");
    } catch (InvalidArgumentException e) {
      assertEquals(e.getMessage(), "Error - Directory does not exist\n");
    }
    // The current directory should not have changed
    assertEquals(fs.currDir, "");
  }

}

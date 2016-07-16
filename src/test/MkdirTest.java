package test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import data.InvalidArgumentException;

/**
 * @author Kirill Lossev Testing the mkdir command
 */
public class MkdirTest {

  private commands.Mkdir mkdir = new commands.Mkdir();
  private MockFileSystem fs;

  /**
   * Sets up the test by creating a mockfilesystem containing a directory
   */
  @Before
  public void setUp() {
    // Create a new MFS and give it a new, empty directory whose parent is
    // the root (empty named) directory
    fs = new MockFileSystem();
    data.Directory testDir =
        new data.Directory("testdir", fs.directories.get(""));
    fs.directories.put("testdir", testDir);
  }

  /**
   * Testing the method when given a relative path
   */
  @Test
  public void testMakeWithRelativePath() {
    // Mkdir with a string without slashes should return a newline
    try {
      assertEquals(mkdir.execute(fs, "testA"), "\n");
    } catch (InvalidArgumentException e1) {
      fail("Failure when creating a valid new directory");
    }
    // The filesystem's current directory (root) should have a directory with
    // that name inside.
    try {
      data.Directory dirA = fs.directories.get("").getDirectory("testA");
      // Add an entry for that directory in the MFS
      fs.directories.put("testA", dirA);
      // Try to call mkdir to create a new directory inside the previously
      // created one
      // It should return a newline, and there should be a directory with the
      // given name inside it
      assertEquals(mkdir.execute(fs, "testA/testB"), "\n");
      try {
        dirA.getDirectory("testB");
      } catch (data.InvalidPathException e) {
        fail("Second test directory was not created");
      }
    } catch (data.InvalidPathException | InvalidArgumentException e) {
      fail("First test directory was not created");
    }
  }

  /**
   * Testing the method when given an absolute path
   */
  @Test
  public void testMakeWithAbsolutePath() {
    // Set the current directory to one that is not root i.e. testdir
    fs.currDir = "testdir";
    // Try to have mkdir create a directory immediately inside root
    // It should return a newline, and there should be a directory
    // with the given name inside root
    try {
      assertEquals(mkdir.execute(fs, "/testA"), "\n");
    } catch (InvalidArgumentException e1) {
      fail("Failure when creating dir with absolute path");
    }
    try {
      data.Directory dirA = fs.directories.get("").getDirectory("testA");
      // Add an entry for that directory in the MFS
      fs.directories.put("/testA", dirA);
      // Try to have mkdir create a new directory inside the one which was just
      // made. It should return a newline, and there should be a directory
      // with the given name inside it
      assertEquals(mkdir.execute(fs, "/testA/testB"), "\n");
      try {
        dirA.getDirectory("testB");
      } catch (data.InvalidPathException e) {
        fail("Second test directory was not created");
      }
    } catch (data.InvalidPathException | InvalidArgumentException e) {
      fail("First test directory was not created");
    }

  }

  /**
   * Testing the method when given a path involving the parent (..)
   */
  @Test
  public void testMakeWithDotPath() {
    // Set the current directory to one that is not root i.e. testdir
    fs.currDir = "testdir";
    // Try to have mkdir create a directory immediately inside root,
    // using .. to go up to root
    // It should return a newline, and there should be a directory
    // with the given name inside root
    try {
      assertEquals(mkdir.execute(fs, "../testA"), "\n");
    } catch (InvalidArgumentException e1) {
      fail("Failure when creating directory in parent");
    }
    try {
      fs.directories.get("").getDirectory("testA");
    } catch (data.InvalidPathException e) {
      fail("Test directory was not created");
    }

  }

  /**
   * Testing with a path that should not be able to be made
   */
  @Test()
  public void testImproperPath() {
    // Try to create a directory inside a nonexistent directory
    // Mkdir should return an error message, and neither root nor
    // testdir should have any directories within them
    try {
      mkdir.execute(fs, "doesnt/exist");
    } catch (InvalidArgumentException e) {
      assertEquals(e.getMessage(), "Error - invalid path\n");
    }
    assertTrue(
        Arrays.equals(fs.directories.get("").getContents(), new String[0]));
    assertTrue(Arrays.equals(fs.directories.get("testdir").getContents(),
        new String[0]));

  }

}

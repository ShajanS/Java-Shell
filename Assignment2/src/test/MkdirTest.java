package test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class MkdirTest {

  commands.Mkdir mkdir = new commands.Mkdir();
  MockFileSystem fs;

  @Before
  public void setUp() throws Exception {
    // Create a new MFS and give it a new, empty directory whose parent is
    // the root (empty named) directory
    fs = new MockFileSystem();
    data.Directory testDir =
        new data.Directory("testdir", fs.directories.get(""));
    fs.directories.put("testdir", testDir);
  }

  @Test
  public void testMakeWithRelativePath() {
    // Mkdir with a string without slashes should return a newline
    assertEquals(mkdir.execute(fs, "testA"), "\n");
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
    } catch (data.InvalidPathException e) {
      fail("First test directory was not created");
    }
  }

  @Test
  public void testMakeWithAbsolutePath() {
    // Set the current directory to one that is not root i.e. testdir
    fs.currDir = "testdir";
    // Try to have mkdir create a directory immediately inside root
    // It should return a newline, and there should be a directory
    // with the given name inside root
    assertEquals(mkdir.execute(fs, "/testA"), "\n");
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
    } catch (data.InvalidPathException e) {
      fail("First test directory was not created");
    }

  }

  @Test
  public void testMakeWithDotPath() {
    // Set the current directory to one that is not root i.e. testdir
    fs.currDir = "testdir";
    // Try to have mkdir create a directory immediately inside root,
    // using .. to go up to root
    // It should return a newline, and there should be a directory
    // with the given name inside root
    assertEquals(mkdir.execute(fs, "../testA"), "\n");
    try {
      fs.directories.get("").getDirectory("testA");
    } catch (data.InvalidPathException e) {
      fail("Test directory was not created");
    }

  }

  @Test()
  public void testImproperPath() {
    // Try to create a directory inside a nonexistent directory
    // Mkdir should return an error message, and neither root nor
    // testdir should have any directories within them
    assertEquals(mkdir.execute(fs, "doesnt/exist"), "Error - invalid path\n");
    assertTrue(
        Arrays.equals(fs.directories.get("").getContents(), new String[0]));
    assertTrue(Arrays.equals(fs.directories.get("testdir").getContents(),
        new String[0]));

  }

}

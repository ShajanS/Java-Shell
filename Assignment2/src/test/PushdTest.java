package test;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

public class PushdTest {
  
  commands.Pushd pushd = new commands.Pushd();
  MockFileSystem fs;
  
  @Before
  public void setUp(){
    // Create a new mock filesystem with a directory named testdir
    fs = new MockFileSystem();
    fs.directories.put("testdir", new data.Directory("testdir", "testdir2"));
    }

  @Test
  public void testChangeToExistingDir() {
	// pushd should be able to change to testdir and return a newline
    String result = pushd.execute(fs, "testdir");
    assertEquals(result, "\n");
    // The new current directory should reflect the change
    assertEquals(fs.currDir, "testdir");
  }
  
  @Test
  public void testAddDirectoryToStack() {
	// pushd should be able to add the current directory to the DirStack
	pushd.execute(fs, "testdir");
	// The popped directory should be the same as the current directory
	assertEquals(fs.currDir, fs.popFromDirStack());
  }
  
  @Test
  public void testAddTwoDirectoriesToStack() {
	// pushd should be able to add multiple directories to the DirStack
	pushd.execute(fs, "testdir");
	pushd.execute(fs, "testdir2");
	// The popped directory should be the same as the current directory
	assertEquals(fs.currDir, fs.popFromDirStack());
  }
  
  @Test
  public void testPushToNonExistingDir(){
    // pushd should not be able to change to any other directory, and return a
    // error
    String result = pushd.execute(fs, "somedir");
    assertEquals(result, "Error - Directory does not exist\n");
    // The current directory should not have changed
    assertEquals(fs.currDir, "");
  }

}

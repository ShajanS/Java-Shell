package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CdTest {
  
  commands.Cd cd = new commands.Cd();
  MockFileSystem fs;
  
  @Before
  public void setUp(){
    // Create a new mock filesystem with a directory named testdir,
    // whose parent is the root directory 
    fs = new MockFileSystem();
    fs.directories.put("testdir", new data.Directory("testdir", 
        fs.directories.get("")));
  }

  @Test
  public void testChangeToExistingDir() {
    // cd should be able to change to testdir and return a newline
    String result = cd.execute(fs, "testdir");
    assertEquals(result, "\n");
    // The new current directory should reflect the change
    assertEquals(fs.currDir, "testdir");
  }
  
  @Test
  public void testDotPath(){
    // Set the current directory to testdir
    fs.currDir = "testdir";
    // cd should be able to change to the root dir and return a newline
    assertEquals(cd.execute(fs, ".."), "\n");
    // The new current directory should reflect the change
    assertEquals(fs.currDir, "");
  }
  
  @Test
  public void testChangeToNonExistingDir(){
    // cd should not be able to change to any other directory, and return a
    // error
    String result = cd.execute(fs, "somedir");
    assertEquals(result, "Error - Directory does not exist\n");
    // The current directory should not have changed
    assertEquals(fs.currDir, "");
  }

}

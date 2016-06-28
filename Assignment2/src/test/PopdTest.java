package test;

import static org.junit.Assert.*;

import java.util.Stack;
import org.junit.Before;
import org.junit.Test;

public class PopdTest {
  
  commands.Pushd pushd = new commands.Pushd();
  commands.Popd popd = new commands.Popd();
  MockFileSystem fs;
  
  @Before
  public void setUp(){
    // Create a new mock filesystem with a directory named testdir
    fs = new MockFileSystem();
    data.Directory parent = new data.Directory("parent");
    fs.directories.put("testdir", new data.Directory("testdir", parent));
    }

  @Test
  public void testPopAfterPush() {
	// pushd should be able to add the current directory to the DirStack
	String result = fs.currDir;
	pushd.execute(fs, "parent");
	// The popped directory should be the same as the current directory
	assertEquals(result, fs.popFromDirStack());
  }
 
  @Test
  public void testPopAfterPushingTwice() {
	// pushd should be able to add multiple directories to the DirStack
	pushd.execute(fs, "parent");
	// store the current directory before pushing again
	String result = fs.currDir;
	pushd.execute(fs, "testdir");
	// The popped directory should be the same as the previous directory
	assertEquals(result, fs.popFromDirStack());
  }
  
  @Test
  public void testPopAfterNoPushes(){
	  // Result should be an error if Stack is Empty
	  String result = "Error - Cannot pop from Empty Stack\n";
	  assertEquals(result, popd.execute(fs, ""))
  }
}

package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import data.JFileSystem;



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
    echo.execute(sys, "Contents in file > file.txt");
    echo.execute(sys, "Contents in file 2 > file2.txt");
    echo.execute(sys, "Contents in file 3 > file3.txt");
  }

  @Test
  public void testExecuteSingleFile() {
    // passing the command to retrieve a file which is present in the directory
    assertEquals(cat.execute(sys, "file3.txt"), "Contents in file 3 \n");
  }

  @Test
  public void testExecuteMultipleFile() {
    // passing the command to retrieve multiple files which is present 
    // in the directory
    // format between the two files should be spaced out appropriately 
    assertEquals(cat.execute(sys, "file3.txt file2.txt"),
        "Contents in file 3 \n\n\n" + "Contents in file 2 \n");
  }

  @Test
  public void testExecuteNoFile() {
    // passing the command to retrieve a files which is not present 
    // in the directory, and error message should be displayed accordingly 
    assertEquals(cat.execute(sys, ""), "File(s) not found\n");
  }
  
  @Test
  public void testExecuteNewAppendedFile() {
    // passing the command to retrieve a file which is present but also
    // has been appended new content to add to the file
    echo.execute(sys, "New line Hello World >> file.txt");
    assertEquals(cat.execute(sys, "file.txt"), 
        "Contents in file  New line Hello World \n");
  }

}

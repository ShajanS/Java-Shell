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
  public void setUp(){
    sys = new JFileSystem();
    echo.execute(sys, "Contents in file > file.txt");
    echo.execute(sys, "Contents in file 2 > file2.txt");
    echo.execute(sys, "Contents in file 3 > file3.txt");

    
  }
  
  @Test
  public void testExecuteSingleFile() {
    assertEquals(cat.execute(sys, "file3.txt"), "Contents in file 3 \n");
  }

}
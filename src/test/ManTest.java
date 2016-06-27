package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class ManTest {

  commands.Man man = new commands.Man();
  MockFileSystem fs;
  
  @Before
  public void setUp(){
    // Create a new mock file system
    fs = new MockFileSystem();
  }
  
  @Test
  public void testExecute() {
    // With no > or >>, echo should return a trimmed version of its
    // parameters plus a newline
    String testOutput = man.execute(fs, "exit");
    assertEquals("\n\texits the program", testOutput);
  }
}


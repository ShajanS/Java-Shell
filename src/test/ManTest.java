package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class ManTest {

  commands.Man man = new commands.Man();
  MockFileSystem fs;

  @Before
  public void setUp() {
    // Create a new filesystem to handle the command calls for the test cases
    fs = new MockFileSystem();
  }

  @Test
  public void testExecute() {
    // test a valid command to check if proper documentation gets returned 
    String testOutput = man.execute(fs, "exit");
    String testActual = "exit" + ":" + "\n\texits the program" + "\n";
    assertEquals(testActual, testOutput);
  }

  @Test
  public void testEmptyArgs() {
    // pass the command an empty argument and proceed
    String testOutput = man.execute(fs, "");
    // command should display an appropriate error message
    String testActual = "Invalid Arguments\n";
    assertEquals(testActual, testOutput);
  }

  @Test
  public void testInvalidCMD() {
    // request the command to output documentation for an invalid command
    String testOutput = man.execute(fs, "movedir");
    // an error message should be displayed
    String testActual = "Invalid Arguments\n";
    assertEquals(testActual, testOutput);
  }
}


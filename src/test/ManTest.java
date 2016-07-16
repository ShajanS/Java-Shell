package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import data.InvalidArgumentException;

/**
 * @author Shajan Sivarajah Tests for the man command
 */
public class ManTest {

  commands.Man man = new commands.Man();
  MockFileSystem fs;

  @Before
  public void setUp() {
    // Create a new filesystem to handle the command calls for the test cases
    fs = new MockFileSystem();
  }

  @Test
  public void testExecute() throws InvalidArgumentException {
    // test a valid command to check if proper documentation gets returned
    String testOutput = man.execute(fs, "exit");
    String testActual = "exit" + ":" + "\n\texits the program" + "\n";
    assertEquals(testActual, testOutput);
  }

  @Test
  public void testEmptyArgs() {
    // pass the command an empty argument and proceed
    try {
      man.execute(fs, "man");
    } catch (InvalidArgumentException e) {
      fail();
    }
  }

  @Test
  public void testInvalidCMD() throws InvalidArgumentException {
    // request the command to output documentation for an invalid command
    try {
      String testOutput = man.execute(fs, "movedir");
      String testActual = "Invalid Arguments\n";
      assertEquals(testActual, testOutput);
      fail();

    } catch (InvalidArgumentException e) {
      assertEquals(e.getMessage(), "Error - Invalid arguments.\n");
    }
  }
}


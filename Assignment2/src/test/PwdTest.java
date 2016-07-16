package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kirill Lossev
 * Unit tests for the pwd command
 */
public class PwdTest {

  private commands.Pwd pwd = new commands.Pwd();
  private MockFileSystem fs;

 
  /**
   * Sets up the test by creating a mockfilesystem
   */
  @Before
  public void setUp() {
    // Initialize the MFS
    fs = new MockFileSystem();
  }

  /**
   * Testing the method. This command does not take any user input
   * so there is no reason it should fail.
   */
  @Test
  public void test() {
    // pwd should return whatever the MFS's current directory string + newline
    // Any parameters should not affect the output
    assertEquals(pwd.execute(fs, ""), "\n");
    fs.currDir = "test 1";
    assertEquals(pwd.execute(fs, ""), "test 1\n");
    assertEquals(pwd.execute(fs, "some params"), "test 1\n");
  }

}

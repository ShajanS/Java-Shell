package test;

import static org.junit.Assert.*;

import org.junit.Test;

import data.InvalidArgumentException;

import org.junit.Before;

/**
 * @author Kirill Lossev
 * Test cases for the echo command
 */
public class EchoTest {

  commands.Echo echo = new commands.Echo();
  MockFileSystem fs;

  /**
   * The setup method, creates a new filesystem.
   */
  @Before
  public void setUp() {
    // Create a new mock file system
    fs = new MockFileSystem();
  }

  /**
   * Testing that properly formatted strings (surrounded with double quotes)
   * return properly
   */
  @Test
  public void testEchoCorrectlyFormatted() {
    // echo should return its parameters without the
    // surrounding quotes plus a newline.
    // None of these should cause errors.
    try {
      assertEquals(echo.execute(fs, "\"test\""), "test\n");
      assertEquals(echo.execute(fs, "\"    test 2\""), "    test 2\n");
      assertEquals(echo.execute(fs, "\"  test  3  \""), "  test  3  \n");
      assertEquals(echo.execute(fs, "\"  \"test 4\" \""), "  \"test 4\" \n");
      assertEquals(echo.execute(fs, "\"\"   test 5  \"\""), "\"   test 5  \"\n");
    } catch (InvalidArgumentException e) {
      fail();
    }    
  }
  
  /**
   * Testing that improperly formatted strings throw errors
   */
  @Test
  public void testEchoBadlyFormatted(){
    // Each of these should cause an error
    try {
      echo.execute(fs, "no quotes");
      fail("String w/no quotes not causing an error");
    } catch (InvalidArgumentException e) {
      assertEquals(e.getMessage(), "Error - Malformed parameter\n");
    }
    try {
      echo.execute(fs, "\"one quote");
      fail("String with one quote not causing an error");
    } catch (InvalidArgumentException e) {
      assertEquals(e.getMessage(), "Error - Malformed parameter\n");
    }
  }

}

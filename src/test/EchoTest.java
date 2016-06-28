package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class EchoTest {

  commands.Echo echo = new commands.Echo();
  MockFileSystem fs;

  @Before
  public void setUp() {
    // Create a new mock file system
    fs = new MockFileSystem();
  }

  @Test
  public void testEchoToScreen() {
    // With no > or >>, echo should return a trimmed version of its
    // parameters plus a newline
    assertEquals(echo.execute(fs, "test"), "test\n");
    assertEquals(echo.execute(fs, "    test 2"), "test 2\n");
    assertEquals(echo.execute(fs, "  test  3  "), "test  3\n");
    assertEquals(echo.execute(fs, "  \"test 4\" "), "\"test 4\"\n");
    assertEquals(echo.execute(fs, "\"   test 5  \""), "\"   test 5  \"\n");
  }

  @Test
  public void testEchoOverwrite() {
    // With a >, echo should overwrite an existing file, or
    // create a new file in a directory that exists, then return a newline.
    // If this cannot be done, it should return an error.
    assertEquals(echo.execute(fs, "test > VALID PATH"), "\n");
    assertEquals(echo.execute(fs, "test > BAD PATH"), "Error - Invalid path\n");
  }

  @Test
  public void testEchoAppend() {
    // With a >>, echo should overwrite an existing file, or
    // create a new file in a directory that exists, then return a newline.
    // If this cannot be done, it should return an error.
    assertEquals(echo.execute(fs, "test >> VALID PATH"), "\n");
    assertEquals(echo.execute(fs, "test >> BAD PATH"),
        "Error - Invalid path\n");
  }

}

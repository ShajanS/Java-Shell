package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PwdTest {

  commands.Pwd pwd = new commands.Pwd();
  MockFileSystem fs;

  @Before
  public void setUp() throws Exception {
    // Initialize the MFS
    fs = new MockFileSystem();
  }

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

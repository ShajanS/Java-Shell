package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.InvalidArgumentException;
import data.InvalidPathException;

/**
 * @author Kirill Lossev Test cases for the grep command
 */
public class GrepTest {

  private MockFileSystem fs;
  private commands.Grep grep;

  /**
   * Setting up by creating some test files in the file system
   */
  @Before
  public void setUp() {
    fs = new MockFileSystem();
    grep = new commands.Grep();
    // Create the files in the root directory
    data.Directory rootDir = fs.directories.get("");
    try {
      rootDir.insertFile("test1", new data.File("test1", rootDir, "abc"));
      rootDir.insertFile("test2",
          new data.File("test2", rootDir, "abc\ndef\nabc"));
    } catch (InvalidPathException e) {
      // There should be no issue creating these files
      e.printStackTrace();
    }
    // Make sure they also exist in the MFS's file list
    fs.files.put("/test1", "abc");
    fs.files.put("/test2", "abc\ndef\nabc");

  }

  /**
   * Testing grep being called on a single file
   */
  @Test
  public void testSingleFile() {
    // Grepping test1 for abc should show a match on line 1
    String expected = "[1] abc\n";
    try {
      String result = grep.execute(fs, "abc /test1");
      assertEquals(result, expected);
    } catch (InvalidArgumentException e) {
      fail("Error when grepping valid file with valid regex");
    }
  }

  /**
   * Testing grep being called on multiple files
   */
  @Test
  public void testMultipleFiles() {
    // Grepping both files for abc should show a match on line 1 for test1,
    // and matches on lines 1 and 3 for test2
    String expected = "[1] abc\n[1] abc\n[3] abc\n";
    try {
      String result = grep.execute(fs, "abc /test1 /test2");
      assertEquals(result, expected);
    } catch (InvalidArgumentException e) {
      fail("Error grepping multiple files");
    }
  }

  /**
   * Testing grep being called on a directory
   */
  @Test
  public void testDirectoryMode() {
    // Grepping the root directory should show the same results as the
    // previous test, but with filenames included.
    String expected = "[1] /test2: abc\n[3] /test2: abc\n[1] /test1: abc\n";
    try {
      String result = grep.execute(fs, "-r abc /");
      assertEquals(result, expected);
    } catch (InvalidArgumentException e) {
      fail("Error grepping directories");
    }
  }

  /**
   * Testing grep being called on a file with the -r flag
   */
  @Test
  public void testFileWithFlag() {
    // When using the -r flag and grepping a file, an error should be thrown
    String expected = "Error - Invalid path\n";
    try {
      grep.execute(fs, "-r abc test1");
      fail("File with -r does not throw error");
    } catch (InvalidArgumentException e) {
      assertEquals(e.getMessage(), expected);
    }
  }

  /**
   * Testing grep being called on a directory without the -r flag
   */
  @Test
  public void testDirWithNoFlag() {
    // When not using the -r flag and grepping a dir, an error should be thrown
    String expected = "Error - Invalid path\n";
    try {
      grep.execute(fs, "abc /");
      fail("Dir with no -r does not throw error");
    } catch (InvalidArgumentException e) {
      assertEquals(e.getMessage(), expected);
    }
  }

  /**
   * Testing grep being called with too few parameters
   */
  @Test
  public void notEnoughParams() {
    // If there are not enough parameters, an error should be thrown
    String expected = "Error - Missing arguments.\n";
    try {
      grep.execute(fs, "abc");
      fail("Allowing too few params without -r");
    } catch (InvalidArgumentException e) {
      assertEquals(e.getMessage(), expected);
    }
    try {
      grep.execute(fs, "-r abc");
      fail("Allowing too few params with -r");
    } catch (InvalidArgumentException e) {
      assertEquals(e.getMessage(), expected);
    }
  }

  /**
   * Testing grep being called with an invalid regex
   */
  @Test
  public void testBadRegex() {
    // If the regex is invalid, an error should be thrown
    String expected = "Error - Invalid regex\n";
    try {
      grep.execute(fs, "*./ test1");
      fail("Allowing invalid regex");
    } catch (InvalidArgumentException e) {
      assertEquals(e.getMessage(), expected);
    }
  }
}

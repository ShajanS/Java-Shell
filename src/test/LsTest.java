package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LsTest {

  commands.Ls ls = new commands.Ls();
  MockFileSystem fs;

  @Before
  public void setUp() throws Exception {
    // Initialise the MFS
    fs = new MockFileSystem();
    // Add some subdirectories and files to the root directory
    data.Directory root = fs.directories.get("");
    root.createDirectory("subA");
    root.createDirectory("subB");
    root.insertFile("fileA", new data.File("fileA", root));
    // Create a directory containing some subdirectories and files
    data.Directory testDir = new data.Directory("testdir", root);
    root.insertDirectory(testDir, "testdir");

    testDir.createDirectory("subC");
    testDir.insertFile("fileB", new data.File("fileB", testDir));
    // Add this directory to the MFS
    fs.directories.put("testdir", testDir);
  }

  @Test
  public void testNoParams() {
    // ls should return a list, in alphabetical order, of everything in root
    String expected = "\nfileA\nsubA\nsubB\ntestdir\n";
    assertEquals(expected, ls.execute(fs, ""));
  }

  @Test
  public void testDirParam() {
    // When given testdir as a parameter, ls should return the contents
    // of that directory
    String expected = "\ntestdir:\nfileB\nsubC\n\n";
    assertEquals(expected, ls.execute(fs, "testdir"));
  }

  @Test
  public void testFileParam() {
    // When given a filename in the current dir, ls should return the
    // filename
    String expected = "fileB\n";
    assertEquals(expected, ls.execute(fs, "fileB"));
  }

  @Test
  public void testNestedFileParam() {
    // When given a filename in a different dir, the name of that
    // file should be returned
    String expected = "fileB\n";
    assertEquals(expected, ls.execute(fs, "testdir/fileB"));
  }

  public void testBadPath() {
    // When given a nonexistent path, ls should return an error
    String expected = "No such path exists\n";
    assertEquals(expected, ls.execute(fs, "BAD PATH"));
  }

}

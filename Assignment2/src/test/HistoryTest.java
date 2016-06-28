package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class HistoryTest {

  commands.History history = new commands.History();
  MockFileSystem fs;

  @Before
  public void setUp() {
    // Create a new filesystem
    fs = new MockFileSystem();
    // Add a few commands to its history
    fs.commandHistory.add("first");
    fs.commandHistory.add("second");
    fs.commandHistory.add("third");
  }

  @Test
  public void testExecute() {
    // When given a blank parameter, all commands should be returned
    assertEquals(history.execute(fs, ""), "1. first\n2. second\n3. third\n");
    // When given an integer n less than the size of the history, only the last
    // n commands should be returned
    assertEquals(history.execute(fs, "2"), "2. second\n3. third\n");
    // When given an integer n greater than the size of the history, all the
    // commands should be returned
    assertEquals(history.execute(fs, "4"), "1. first\n2. second\n3. third\n");
    // When given a parameter which is not an integer, an error should be
    // returned
    assertEquals(history.execute(fs, "3.14"), "Error - Invalid Parameter\n");
    assertEquals(history.execute(fs, "hello"), "Error - Invalid Parameter\n");
  }

}

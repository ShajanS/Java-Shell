package test;

import static org.junit.Assert.*;

import org.junit.Test;

import data.InvalidArgumentException;

import org.junit.Before;

/**
 * @author Kirill Lossev Test cases for the history command
 */
public class HistoryTest {

  private commands.History history = new commands.History();
  private MockFileSystem fs;

  /**
   * Setup method, creates a mockfilesystem and adds some history data to it
   */
  @Before
  public void setUp() {
    // Create a new filesystem
    fs = new MockFileSystem();
    // Add a few commands to its history
    fs.commandHistory.add("first");
    fs.commandHistory.add("second");
    fs.commandHistory.add("third");
  }

  /**
   * Test the method when given an integer
   */
  @Test
  public void testExecuteWithIntegers() {
    // None of these should cause errors
    // When given a blank parameter, all commands should be returned
    try {
      assertEquals(history.execute(fs, ""), "1. first\n2. second\n3. third\n");
    } catch (InvalidArgumentException e) {
      fail("Error with no parameter");
    }
    // When given an integer n less than the size of the history, only the last
    // n commands should be returned
    try {
      assertEquals(history.execute(fs, "2"), "2. second\n3. third\n");
    } catch (InvalidArgumentException e) {
      fail("Error with parameter < history length");
    }
    // When given an integer n greater than the size of the history, all the
    // commands should be returned
    try {
      assertEquals(history.execute(fs, "4"), "1. first\n2. second\n3. third\n");
    } catch (InvalidArgumentException e) {
      fail("Error with parameter > history length");
    }
  }

  /**
   * Test the method when given a non-integer
   */
  @Test
  public void testExecuteWithNonIntegers() {
    // When given a parameter which is not an integer, an error should be
    // returned
    try {
      history.execute(fs, "3.14");
      fail("Float not causing error");
    } catch (InvalidArgumentException e) {
      assertEquals(e.getMessage(), "Error - Invalid Parameter\n");
    }
    try {
      history.execute(fs, "hello");
      fail("String not causing error");
    } catch (InvalidArgumentException e) {
      assertEquals(e.getMessage(), "Error - Invalid Parameter\n");
    }
  }

}

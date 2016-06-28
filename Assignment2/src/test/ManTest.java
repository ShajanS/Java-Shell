package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class ManTest {

  commands.Man man = new commands.Man();
  MockFileSystem fs;

  @Before
  public void setUp() {
    fs = new MockFileSystem();
  }

  @Test
  public void testExecute() {
    String testOutput = man.execute(fs, "exit");
    String testActual = "exit" + ":" + "\n\texits the program" + "\n";
    assertEquals(testActual, testOutput);
  }

  @Test
  public void testEmptyArgs() {
    String testOutput = man.execute(fs, "");
    String testActual = "Invalid Arguments\n";
    assertEquals(testActual, testOutput);
  }

  @Test
  public void testInvalidCMD() {
    String testOutput = man.execute(fs, "movedir");
    String testActual = "Invalid Arguments\n";
    assertEquals(testActual, testOutput);
  }
}


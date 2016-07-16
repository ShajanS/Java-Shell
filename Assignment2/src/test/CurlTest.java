package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.InvalidArgumentException;
import data.InvalidPathException;

/**
 * @author Kirill Lossev
 * Tests for the curl command
 */
public class CurlTest {

  private data.FileSystem fs;
  private commands.Curl curl;
  
  // These can be changed in case these URLs no longer work
  private String WORKING_URL_TXT;
  private String EXPECTED_TXT;
  private String EXPECTED_TXT_NAME;
  private String WORKING_URL_HTML;
  private String EXPECTED_HTML;
  private String EXPECTED_HTML_NAME;
  private String BAD_URL;
  
  /**
   * Setting up by defining the URLs to use, and a short sample
   * of the text they contain.
   */
  @Before
  public void setUp() {
    fs = new MockFileSystem();
    curl = new commands.Curl();
    
    WORKING_URL_TXT = "http://www.cs.cmu.edu/~spok/grimmtmp/073.txt";
    EXPECTED_TXT = "There was once a king who had an illness,";
    EXPECTED_TXT_NAME = "073.txt";
    
    WORKING_URL_HTML = "http://www.htmlandcssbook.com/code-samples/"
        + "chapter-05/where-to-place-images.html";
    EXPECTED_HTML = "<html>\n";
    EXPECTED_HTML_NAME = "where-to-place-images.html";
    
    BAD_URL = "http://this.is.not/a/website";
  }

  /**
   * Testing getting a test file
   */
  @Test
  public void testTextFile() {
    // Run curl on the text url, and check that the file was created and
    // contains the expected text
    // The actual text is very long, so we will only compare a small sample
    try {
      curl.execute(fs, WORKING_URL_TXT);
      assertTrue(((MockFileSystem)fs).files.containsKey(EXPECTED_TXT_NAME));
      String sample = fs.getFileContents(EXPECTED_TXT_NAME).substring(0,
          EXPECTED_TXT.length());
      assertEquals(EXPECTED_TXT, sample);
    } catch (InvalidArgumentException | InvalidPathException e) {
      fail("Error when retrieving working URL");
    }
  }
  
  /**
   * Testing getting an HTML file
   */
  @Test
  public void testHTMLFile(){
    // Run curl on the text url, and check that the file was created and
    // contains the expected text
    // The actual HTML is very long, so we will only compare a small sample.
    try {
      curl.execute(fs, WORKING_URL_HTML);
      assertTrue(((MockFileSystem)fs).files.containsKey(EXPECTED_HTML_NAME));
      String sample = fs.getFileContents(EXPECTED_HTML_NAME).substring(0, EXPECTED_HTML.length());
      assertEquals(EXPECTED_HTML, sample);
    } catch (InvalidArgumentException | InvalidPathException e) {
      fail("Error when retrieving working URL");
    }
  }
  
  /**
   * Testing an invalid URL
   */
  @Test
  public void testBadUrl(){
    // Run curl on the bad url, it should throw an error
    try{
      curl.execute(fs, BAD_URL);
      fail("Bad url does not cause an error");
    } catch(data.InvalidArgumentException e){
      assertEquals(e.getMessage(), "Error - Could not read from URL.\n");
    }
  }
}

package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int countLine = 1; // To count the number of line processed.

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    // Process preparation
    String[] subStr = new String[2];
    subStr[1] = str.substring(off,off+len);
    StringBuilder resultStr = new StringBuilder();

    /* There are 2 situations where we implement the line number.
     * For the first line and when there is a line separator. */
    if (countLine == 1) { // For the first line.
      resultStr.append(getNumberingString());
    }
    subStr = Utils.getNextLine(subStr[1]);
    while(!subStr[0].isEmpty()) { // Check if there is a line separator.
      resultStr.append(subStr[0]).append(getNumberingString());
      subStr = Utils.getNextLine(subStr[1]); // Check for another line separator.
    }
    resultStr.append(subStr[1]); // The remaining string

    this.out.write(resultStr.toString());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    // call to the function Write(String str,int off, int len) for the same process.
    this.write(String.valueOf(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    boolean sendString = false; // Check if there are more than 1 character to process.
    StringBuilder resultStr = new StringBuilder(); // In case there are more than 1 character.

    if (countLine == 1) { // For the first line.
      resultStr.append(getNumberingString());
      sendString = true;
    }
    resultStr.append(Character.toString(c));

    // Catch end of line based on OS.
    char separator = System.lineSeparator().charAt(System.lineSeparator().length()-1);
    if (Character.toString(c).equals(Character.toString(separator))) {
      resultStr.append(getNumberingString());
      sendString = true;
    }

    if (sendString) {
      this.out.write(resultStr.toString());
    } else {
      this.out.write(c);
    }
  }

  /**
   * Return the number of line and a tabulation.
   * @return a simple String containing the line count and a tabulation.
   */
  private String getNumberingString() {
    return countLine++ + "\t";
  }
}

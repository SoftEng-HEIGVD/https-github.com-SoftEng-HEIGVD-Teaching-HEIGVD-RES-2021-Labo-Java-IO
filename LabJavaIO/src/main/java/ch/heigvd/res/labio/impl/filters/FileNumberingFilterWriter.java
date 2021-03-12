package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
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
  private int count = 1; // To count the number of line processed.

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
    if (count == 1) { // For the first line.
      resultStr.append(count).append("\t");
      count++;
    }
    subStr = Utils.getNextLine(subStr[1]);
    while(!subStr[0].isEmpty()) { // Check if there is a line separator.
      resultStr.append(subStr[0]).append(count).append("\t");
      count++;
      subStr = Utils.getNextLine(subStr[1]); // Check for another line separator.
    }
    resultStr.append(subStr[1]); // The remaining

    this.out.write(resultStr.toString());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(int c) throws IOException {
    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}

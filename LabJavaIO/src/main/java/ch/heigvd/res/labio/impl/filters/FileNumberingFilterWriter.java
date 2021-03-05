package ch.heigvd.res.labio.impl.filters;

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
 * Updated by Marco Maziero on 05.03.2021
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int lineCnt = 0;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for (int i = off; i < off + len; ++i) {
      write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = off; i < off + len; ++i) {
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    // Checks the integer is a valid character
    if (c < 0 || c > 255) throw new IOException("Given value is not a character");
    char v = (char)c; // Current char
    String outString = out.toString(); // Output string

    if (outString.isEmpty()) { // First line, out is empty
      out.append(String.valueOf(++lineCnt)).append('\t').append(v);
    } else if (v == '\n') { // After a \n, always insert new line
      out.append(v).append(String.valueOf(++lineCnt)).append('\t');
    } else if (outString.charAt(outString.length() - 1) == '\r') { // If \r case. Cannot be \r\n at this point
      out.append(String.valueOf(++lineCnt)).append('\t').append(v);
    } else { // Normal character
      out.append(v);
    }
  }
}

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
 * Updated by Marco Maziero on 06.03.2021
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int lineCnt = 0;
  private char lastWrittenChar;
  private boolean writeStarted = false;

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
    write(String.valueOf(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    char v = (char)c; // Current char

    if (!writeStarted) { // First line, starts the writing
      writeStarted = true;
      String data = String.valueOf(++lineCnt) + '\t' + v;
      super.write(data, 0, data.length());

    } else if (v == '\n') { // After a \n, always insert new line
      String data = v + String.valueOf(++lineCnt) + '\t';
      super.write(data, 0, data.length());

    } else if (lastWrittenChar == '\r') { // If \r case. Cannot be \r\n at this point
      String data = String.valueOf(++lineCnt) + '\t' + v;
      super.write(data, 0, data.length());

    } else { // Normal character
      super.write(v);
    }

    // Stores the current char to detect \r lines separators in the next write
    lastWrittenChar = v;
  }
}

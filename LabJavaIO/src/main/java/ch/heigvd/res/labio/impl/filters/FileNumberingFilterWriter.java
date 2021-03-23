package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;
import jdk.jshell.execution.Util;

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
 *
 * Modified by Joan Maillard
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int count = 1;
  private boolean hadAnR;
  private boolean newLine;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  private void writeLineNumToken() throws IOException {
    if(newLine) {
      newLine = false;
      out.write(count + "\t");
      count++;
    }
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String lines[] = Utils.getNextLine(str.substring(off, off+len));
    while (!lines[0].equals("")) {
      writeLineNumToken();
      out.write(lines[0]);
      newLine = true;
      lines = Utils.getNextLine(lines[1]);
    }
    writeLineNumToken();
    out.write(lines[1]);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(new String(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    if (c != '\n' && hadAnR) {
      writeLineNumToken();
    }
    out.write(c);
    if (c == '\n') {
      if (!hadAnR) {
        newLine = true;
        writeLineNumToken();
      }
      else {
        writeLineNumToken();
      }
    }
    if (c == '\r') {
      hadAnR = true;
      newLine = true;
    }
    else {
      hadAnR = false;
    }
  }
}

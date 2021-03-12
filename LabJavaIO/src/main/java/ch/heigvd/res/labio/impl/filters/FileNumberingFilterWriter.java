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
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private int lines = 0;
  private boolean sepL = false;
  private boolean sepW = false;

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    StringBuilder s1 = new StringBuilder();
    Integer lineNumber;
    if(lines == 0) {
      lineNumber = ++lines;
      s1.append(lineNumber);
      s1.append('\t');
    }

    for(int i = off; i < len + off; i++) {
      if(lines == 0) {
        lineNumber = ++lines;
        s1.append(lineNumber);
        s1.append('\t');
      }

      if(sepW ) {
        lineNumber = ++lines;
        s1.append(lineNumber);
        s1.append('\t');
        s1.append(str.charAt(i));
        sepW = false;
        sepL = false;
      } else if(str.charAt(i) != '\n' && sepL) {
        lineNumber = ++lines;
        s1.append(lineNumber);
        s1.append('\t');
        s1.append(str.charAt(i));
        sepL = false;
      } else if(str.charAt(i) == '\n') {
        s1.append(str.charAt(i));
        sepW = true;
      } else if(str.charAt(i) == '\r' ) {
        s1.append(str.charAt(i));
        sepL = true;
      } else {
        s1.append(str.charAt(i));
      }
    }

    if(len != 1 && (sepL || sepW)) {
      lineNumber = ++lines;
      s1.append(lineNumber);
      s1.append('\t');
      sepW = false;
    }

    super.write(s1.toString(), 0, s1.length());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    this.write(cbuf.toString(), off, len);
  }

  @Override
  public void write(int c) throws IOException {

    this.write(Character.toString((char) c), 0, 1);
  }

}

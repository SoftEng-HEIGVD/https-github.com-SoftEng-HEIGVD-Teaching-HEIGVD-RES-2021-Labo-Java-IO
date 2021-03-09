package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;

/**
 *
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    int endIndex = off + len - 1;
    String upStr = str.substring(off, endIndex);
    out.write(upStr.toUpperCase());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    char[] upCbuf = new char[len];
    for (int i = 0; i < len ; ++i) {
      upCbuf[i] = Character.toUpperCase(cbuf[off + i]);
    }
    out.write(upCbuf);
  }

  @Override
  public void write(int c) throws IOException {
    char upChar = Character.toUpperCase((char) c);
    out.write(upChar);
  }

}

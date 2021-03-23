package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 * Modified by Joan Maillard and Mathias Maillard
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    out.write(str.toUpperCase(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    out.write(String.copyValueOf(cbuf).toUpperCase(), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    out.write(Character.toUpperCase((char) c));
  }

}

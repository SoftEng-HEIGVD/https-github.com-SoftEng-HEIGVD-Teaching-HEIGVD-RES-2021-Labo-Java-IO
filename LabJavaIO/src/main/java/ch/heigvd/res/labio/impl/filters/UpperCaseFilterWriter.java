package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 */

//initial commit
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String newStr = str.substring(0, off);
    newStr += str.substring(off, off + len).toUpperCase();
    newStr += str.substring(off + len);
    System.out.println(newStr);
    out.write(newStr, off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = 0; i < len; ++i)
      cbuf[i + off] = Character.toUpperCase(cbuf[i + off]);
    out.write(cbuf, off, len);
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(int c) throws IOException {
    out.write(Character.toUpperCase(c));
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}

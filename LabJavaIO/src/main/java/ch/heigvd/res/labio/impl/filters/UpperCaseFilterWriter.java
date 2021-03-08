package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

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
    String res = str.substring(off, off + len).toUpperCase();
    this.out.write(res);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    final int range = off + len;
    for (int i = off; i < range; ++i)
    {
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    this.out.write(Character.toUpperCase(c));
  }

}

package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

import static java.lang.Character.toUpperCase;

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
    out.write(str.toUpperCase(),off,len);
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    char[] tmp = new char[cbuf.length];
    for(int i = 0; i<tmp.length; ++i){
      tmp[i] = toUpperCase(cbuf[i]);
    }
    out.write(tmp,off,len);
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(int c) throws IOException {
    out.write(toUpperCase(c));
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}

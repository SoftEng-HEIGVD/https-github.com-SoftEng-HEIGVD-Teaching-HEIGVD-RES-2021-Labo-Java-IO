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
    String output = str.substring(off, off + len);
    output = output.toUpperCase();
    super.write(output, 0, output.length());

    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    char[] output = new char[cbuf.length];
    for(int i = 0; i < cbuf.length; i++)
    {
      output[i] = Character.toUpperCase(cbuf[i]);
    }
    super.write(output, off, len);

    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(int c) throws IOException {
    char input = (char) c;
    char output = Character.toUpperCase(input);
    super.write(output);


    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}

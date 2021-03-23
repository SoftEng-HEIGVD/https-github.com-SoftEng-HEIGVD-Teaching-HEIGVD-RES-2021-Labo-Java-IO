package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 *
 * Modified by Noah Fusi and Janis Chiffelle
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    if(len + off > str.length())
    {
      throw new IOException("Wrong parameters : the desired length is greater than the string length minus the offset");
    }

    //Get only the useful part of the string
    String output = str.substring(off, off + len);
    //Convert the string to uppercase
    output = output.toUpperCase();
    super.write(output, 0, output.length());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    if(off + len > cbuf.length)
    {
      throw new IOException("Wrong parameters : the desired length is greater than the string length minus the offset");
    }

    //Process only the useful part of the char array
    char[] output = new char[cbuf.length];
    for(int i = 0; i < cbuf.length; i++)
    {
      output[i] = Character.toUpperCase(cbuf[i]);
    }
    super.write(output, off, len);

  }

  @Override
  public void write(int c) throws IOException {
    char input = (char) c;
    char output = Character.toUpperCase(input);
    super.write(output);
  }

}

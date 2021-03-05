package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author Olivier Liechti
 * Updated by Marco Maziero on 05.03.2021
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    // Ietartes through the string and modifies the chars
    for (int i = off; i < off + len; ++i) {
      out.append(Character.toUpperCase(str.charAt(i)));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    // Transforms the array into a string and calls write with a string
    write(String.valueOf(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    // Checks the integer is a valid character
    if (c < 0 || c > 255) throw new IOException("Given value is not a character");
    out.append(Character.toUpperCase((char)c));
  }

}

package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 *
 * Modified by Dylan Canton, Alessandro Parrino
 */
public class UpperCaseFilterWriter extends MyFilterWriter{
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(int c) throws IOException {
    //Writes the character in upper case
    super.write(Character.toUpperCase(c));
  }
}
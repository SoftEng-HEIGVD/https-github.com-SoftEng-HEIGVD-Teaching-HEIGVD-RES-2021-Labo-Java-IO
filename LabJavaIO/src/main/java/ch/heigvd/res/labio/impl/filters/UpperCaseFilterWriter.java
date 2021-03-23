package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import java.lang.String;

//https://docs.oracle.com/javase/7/docs/api/java/io/Writer.html

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
    super.write(str.toUpperCase(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String str = new String(cbuf);
    super.write(str.toUpperCase(), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    if(c >= 97 && c <= 122){
        super.write(c-32);
    }else{
        super.write(c);
    }
  }
}

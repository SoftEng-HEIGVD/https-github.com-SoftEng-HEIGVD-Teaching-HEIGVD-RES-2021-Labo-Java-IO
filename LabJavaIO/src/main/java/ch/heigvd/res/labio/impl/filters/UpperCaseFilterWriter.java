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
    super.write(str.toUpperCase(),off,len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    char tempBuf[];
    tempBuf = new char[off + len];
    int j = 0;
    for(int i = 0; i < off + len; i++){
      tempBuf[j++] = Character.toUpperCase(cbuf[i]);
    }
    super.write(tempBuf,off,len);

  }

  @Override
  public void write(int c) throws IOException {
    char a = (char)c;
    a = Character.toUpperCase(a);
    super.write(a);
  }

}

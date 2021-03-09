package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  private static int nbLine = 1;
  private static boolean newLine = true;

  @Override
  public void write(String str, int off, int len) throws IOException {

    int endIndex = off + len;
    String tempStr = str.substring(off, endIndex);
    String newStr = "";

    if(newLine) {
      out.write(nbLine++ + "\t");
      newLine = false;
    }

    for (int i=0; i < tempStr.length(); i++) {
      char c = tempStr.charAt(i);
      newStr += c;
      if(c == '\n' || c == '\r')
      {
        out.write(newStr);
        out.write(nbLine++ + "\t");
        newStr = "";
      }
    }

    out.write(newStr);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    char[] newCBuf = new char[len];
    if(newLine) {
      out.write(nbLine++ + "\t");
      newLine = false;
    }
    for (int i = 0; i < len ; ++i) {
      newCBuf[i] = cbuf[off + i];
      if(cbuf[off + i] == '\n' || cbuf[off + i] == '\r')
      {
        out.write(newCBuf);
        out.write(nbLine++ + "\t");
        newCBuf = new char[len-i];
      }
    }
    out.write(newCBuf);
  }



  @Override
  public void write(int c) throws IOException {
    if(newLine) {
      out.write(nbLine++ + "\t");
      newLine = false;
    }
    out.write((char) c);

    if((char) c == '\n' || (char) c == '\r')
      out.write(nbLine++ + "\t");

  }

}

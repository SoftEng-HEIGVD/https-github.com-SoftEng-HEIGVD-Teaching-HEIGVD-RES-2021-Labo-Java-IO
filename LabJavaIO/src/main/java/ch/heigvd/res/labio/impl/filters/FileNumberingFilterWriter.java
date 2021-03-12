package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.StringWriter;
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
    nbLine = 1;
    newFile = true;
  }

  private static int nbLine;
  private static boolean newFile;

  @Override
  public void write(String str, int off, int len) throws IOException {

    int endIndex = off + len;
    String tempStr = str.substring(off, endIndex);
    String newStr = "";

    if(newFile) {
      out.write(nbLine++ + "\t");
      newFile = false;
    }

    for (int i=0; i < tempStr.length(); i++) {
      char c = tempStr.charAt(i);

        newStr += c;
      if(c == '\n' || c == '\r')
      {
        out.write(newStr);
        if(i+1 >= len || tempStr.charAt(i+1) != '\n')
          out.write(nbLine++ + "\t");
        newStr = "";
      }
    }

    out.write(newStr);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(new String(cbuf),off,len);
  }

  @Override
  public void write(int c) throws IOException {
    if(newFile) {
      out.write(nbLine++ + "\t");
      newFile = false;
    }
    out.write((char) c);

    if((char) c == '\n') {
      out.write(nbLine++ + "\t");
    }
  }

}

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

  private int lineNumber = 1;
  private boolean newLine = false;

  @Override
  public void write(String str, int off, int len) throws IOException {
    for (int i = 0; i < len; i++) {
      write(str.charAt(off + i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = 0; i < len; i++) {
      write(off + i);
    }
  }

  @Override
  public void write(int c) throws IOException
  {
    if(lineNumber == 1)
    {
      out.write(lineNumber++ + "\t");
    }
    if(newLine)
    {
      if(c != '\n')
      {
        out.write(lineNumber++ + "\t");
      }
      newLine = false;
    }
    out.write(c);
    if(c == '\n')
    {
      out.write(lineNumber++ + "\t");
    }
    if(c == '\r')
    {
      newLine = true;
    }
  }

}

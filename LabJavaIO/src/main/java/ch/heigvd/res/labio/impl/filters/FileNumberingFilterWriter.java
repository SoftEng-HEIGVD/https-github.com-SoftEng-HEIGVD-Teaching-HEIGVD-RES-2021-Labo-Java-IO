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
  private static int cnt;
  private boolean backslashR = false;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    cnt = 0;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for (int i = off; i < off + len; ++i) {
      write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = off; i < off + len; ++i) {
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {

    if (cnt == 0) { // First line
      out.write(++cnt +  "\t");
      super.write(c);
      return;
    } else if (c == '\n') { // Unix and Windows case
      super.write(c);
      out.write(++cnt +  "\t");
      backslashR = false;
      return;
    } else if (backslashR) { // Mac case
      out.write(++cnt +  "\t");
      backslashR = false;
    } else if (c == '\r') { // Potential Mac or Windows case : we must wait the next char to find out
      backslashR = true;
    }
    super.write(c);

  }
}


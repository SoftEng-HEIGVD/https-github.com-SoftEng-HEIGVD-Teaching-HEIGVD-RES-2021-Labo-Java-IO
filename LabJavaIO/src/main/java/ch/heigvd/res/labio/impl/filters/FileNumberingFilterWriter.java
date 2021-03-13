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

  private int lineNumber = 1;
  private boolean newLine = true;

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for(int i = off; i < off + len; i++){
      char a = str.charAt(i);
      this.write(a);
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i < off + len; i++){
      this.write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {

    if (c == '\r') {
      newLine = true;
      super.write(c);
    }

    else if (c == '\n') {
      newLine = false;
      super.write(c);
      super.write(String.valueOf(lineNumber++));
      super.write('\t');

    }else {

      if (newLine) {
        newLine = false;
        super.write(String.valueOf(lineNumber++));
        super.write('\t');
      }

      super.write(c);
    }
  }
}

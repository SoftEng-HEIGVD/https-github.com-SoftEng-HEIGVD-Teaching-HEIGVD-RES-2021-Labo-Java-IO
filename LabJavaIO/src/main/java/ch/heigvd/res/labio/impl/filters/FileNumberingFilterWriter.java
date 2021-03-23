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

  private int no;
  private boolean shouldInsertNewLine = false;

  public FileNumberingFilterWriter(Writer out) throws IOException {
    super(out);
    insertNumerotation();
  }

  private void insertNumerotation() throws IOException {
    super.out.write(String.valueOf(++no));
    super.out.write(9); // Tab
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    if(cbuf.length < off + len - 1)
      throw new ArrayIndexOutOfBoundsException("The offset or len is out of range!");

    for(int i = off; i < off + len; i++)
        write(cbuf[i]);
  }

  @Override
  public void write(int c) throws IOException {
    if (shouldInsertNewLine) {
      if ((char) c == '\n') {
        super.out.write(c);
        insertNumerotation();
        shouldInsertNewLine = false;
        return;
      } else {
        insertNumerotation();
        shouldInsertNewLine = false;
      }
    } else if ((char) c == '\n') {
      super.out.write(c);
      insertNumerotation();
      return;
    }

    if ((char) c == '\r')
      shouldInsertNewLine = true;

    super.out.write(c);
  }
}

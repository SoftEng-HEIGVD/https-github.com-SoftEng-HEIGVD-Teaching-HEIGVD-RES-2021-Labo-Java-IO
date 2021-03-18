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

  private int totalLines = 1;
  private boolean isNewLine = false;
  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = 0; i < len; i++){
      write(cbuf[off + i]);
    }
    // Send null byte so we can detect newline properly
    write(0x0);
  }

  @Override
  public void write(int c) throws IOException {

    // Check if it's the start of th text
    if(totalLines == 1){
      out.write(totalLines++ + "\t" + (char)c);
      return;
    }
    // Check for new line
    if(c == '\r' || c == '\n') {
      isNewLine = true;
    }
    // Write new line with tab
    else if(isNewLine){
      out.write(totalLines++ + "\t");
      isNewLine = false;
    }
    // Write char if not null byte
    if(c != 0x0)
      out.write(c);

  }

}

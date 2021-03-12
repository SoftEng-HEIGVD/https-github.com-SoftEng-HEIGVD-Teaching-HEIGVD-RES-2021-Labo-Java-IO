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

  private int nLines;
  private boolean previousCharacterWasCarriageReturn;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    nLines = 1;
    // https://en.wikipedia.org/wiki/Carriage_return
    previousCharacterWasCarriageReturn = false;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
      write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
      for(int i = off; i < off + len; ++i){
          write(cbuf[i]);
    }
  }


  @Override
  public void write(int c) throws IOException {
      if(nLines == 1 ){
          newLineFormat();
      }

      // Add new line if the previous char was \r and wasn't followed by \n ( \r\n )
      if(c != '\n' && previousCharacterWasCarriageReturn){
          previousCharacterWasCarriageReturn = false;
          newLineFormat();
      }

      super.write(c);

      if(c =='\r'){
          previousCharacterWasCarriageReturn = true;
      }
      else if(c == '\n'){
          previousCharacterWasCarriageReturn = false;
          newLineFormat();
      }
  }

  private void newLineFormat() throws IOException {
      super.write(Integer.toString(nLines++));
      super.write('\t');
  }
}

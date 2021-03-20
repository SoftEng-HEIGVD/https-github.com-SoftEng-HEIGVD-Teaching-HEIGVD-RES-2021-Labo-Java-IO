package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.logging.Logger;
import ch.heigvd.res.labio.impl.Utils;

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
  private int lineNb = 1;
  private boolean isMacSeparator = false;
  @Override
  public void write(String str, int off, int len) throws IOException {

    for(int i = 0; i < len; i++){
      write(str.charAt(off + i));
    }

  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(Arrays.toString(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {

    if(lineNb == 1){
      out.write(lineNb++  + "\t");
      out.write(c);
    }

    else if(c == '\n'){
      
      //If there was a '\r' character before
      if(isMacSeparator){
        isMacSeparator = false;
      }
        out.write(c);
        out.write(lineNb++ + "\t");

    }

    else if(isMacSeparator){
      out.write( lineNb++ + "\t");
      out.write(c);
      isMacSeparator = false;

    }

    else{
      out.write(c);
    }

    if(c == '\r'){
      isMacSeparator = true;
    }
  }
}

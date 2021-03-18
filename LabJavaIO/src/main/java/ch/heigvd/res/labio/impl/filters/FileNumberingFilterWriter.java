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
  private boolean firstLine = true;
  private boolean isMacSeparator = false;
  @Override
  public void write(String str, int off, int len) throws IOException {

    String[] result = Utils.getNextLine(str.substring(off, off+len));
    if(firstLine){
      super.write(lineNb++  + "\t");
    }
    while(result[0].length() != 0){
      if(result[0].substring(result.length - 2) == "\r\n"){
        super.write(result[0].substring(result.length - 2) + lineNb++ + "\t");
      }
      else{
        super.write(result[0].substring(result.length - 1) + lineNb++ + "\t");
      }
      result = Utils.getNextLine(result[1]);
    }
    super.write(result[1]);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(Arrays.toString(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {

    if(firstLine){
      super.write(lineNb++  + "\t" + c);
      firstLine = false;
    }
    else if(c == '\n'){
      if(isMacSeparator){
        super.write("\r\n" + lineNb++ + "\t");
        isMacSeparator = false;
      }else{
        super.write(c + lineNb++ + "\t");
      }
    }
    else if(isMacSeparator){
      super.write('\r' + lineNb++ + "\t" + c);
      isMacSeparator = false;

    }
    else if(c != '\r'){
      super.write(c);
    }
    else{
      isMacSeparator = true;
    }

  }

}

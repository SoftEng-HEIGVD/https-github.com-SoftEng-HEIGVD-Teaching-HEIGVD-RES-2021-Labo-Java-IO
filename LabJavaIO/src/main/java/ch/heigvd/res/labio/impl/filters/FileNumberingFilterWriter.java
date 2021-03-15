package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
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

  private int lineCounter = 2;
  private boolean imTheFirstLine = true;

  @Override
  public void write(String str, int off, int len) throws IOException {

    str = str.substring(off,off+len);
    len = str.length();

    if(imTheFirstLine){
      str = "1\t" + str;
      len = str.length();
      imTheFirstLine = false;
    }

    for(int i = 0 ; i < str.length() ; i++){
      if(str.charAt(i) == '\n' || str.charAt(i) == '\r'){
        if((i != str.length()-1) && (str.charAt(i) == '\r' && str.charAt(i+1) == '\n')){
          continue;
        }
        str = str.substring(0,i+1) + lineCounter++ + '\t' + str.substring(i+1);
        len = str.length();
      }
    }

    off = 0;

    super.write(str,off,len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(Arrays.toString(cbuf),off,len);
  }

  @Override
  public void write(int c) throws IOException {
    if(imTheFirstLine){
      super.write(49);
      super.write(9);
      imTheFirstLine = false;
    }

    if(c == 13){
      super.write(13);
    }else if(c == 10){
      super.write(10);
      super.write(48 + lineCounter++);
      super.write(9);
    }if(c != 10 && c != 13){
      super.write(c);
    }
  }
}

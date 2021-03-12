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

  private int cpt = 2;
  private boolean imTheFirstLine = true;

  @Override
  public void write(String str, int off, int len) throws IOException {

    str = str.substring(off,off+len);
    len = str.length();

    if(imTheFirstLine){
      str = "1\t" + str;
      len += 2;
      imTheFirstLine = false;
    }

    if(str.charAt(len-1) == '\n'){
      str = str.substring(0,len) + cpt++ + '\t';
      len += 2;
    }


    //if(str.charAt(len) == '\n')
      //str = str.substring(0,len);

    /*for (int i = 0 ; i < len ; i++){ // len-1 for avoid the treatement of the last \n
      if(str.charAt(i) == '\n'){
        str = str.substring(0,i) + '\n' + cpt + '\t' + str.substring(i+1);
        cpt++;
        len += 3;
      }
    }*/
    super.write(str,off,len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(int c) throws IOException {
    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}

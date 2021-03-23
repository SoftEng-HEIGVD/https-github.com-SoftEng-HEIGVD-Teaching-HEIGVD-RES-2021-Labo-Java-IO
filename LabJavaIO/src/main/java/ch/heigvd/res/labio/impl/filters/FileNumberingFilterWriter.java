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
 * Hello\n\World -> 1\tHello\n2\tWorld
 *
 * @author Olivier Liechti
 * Modified by Blanc Jean-Luc
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int counter = 0;
  private int lastC = '\0';

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for(int i = off; i < len+off; i++){
      write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i < len+off; i++){
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    //here we want to check if we are on the first line, if that's not the case then we want to check
    // the various "c" cases regarding the OS
    // UNIX = \n      MAC OS = \r     Windows = \r\n
    // after that we write down the counter, insert a tab
    // then we write down the char (while not forgetting to save the last char
    if(counter == 0){
      out.write(++counter + "\t");
    }
    else if(lastC == '\r' && c != '\n'){
      out.write(++counter + "\t");
    }

    out.write(c);

    if(c == '\n' && lastC == '\r'){
      out.write(++counter + "\t");
    }
    else if(c == '\n' && lastC != '\r'){
      out.write(++counter + "\t");
    }

    lastC = c;
  }

}

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
  private int nbLine = 0;
  private int lastChar;
  private boolean firstLine = true;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }


  @Override
  public void write(String str, int off, int len) throws IOException {
    for(int i = off; i < off + len; ++i){
      this.write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = off; i < off + len; ++i){
      this.write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    /*
    New line property between OS:
    \n is for unix
    \r is for mac
    \r\n is for windows
     */
    
    if(firstLine){
      out.write(++nbLine + "\t");
      firstLine = false;
    }

    // if on mac
    if(lastChar == '\r' && c != '\n'){
      out.write(++nbLine + "\t");
    }

    // if not the end of the line
    out.write(c);

    if(c == '\n'){
      out.write(++nbLine + "\t");
    }

    // save the last char
    lastChar = c;
  }

}

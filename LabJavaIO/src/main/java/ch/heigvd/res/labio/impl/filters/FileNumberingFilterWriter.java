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
 */
public class FileNumberingFilterWriter extends FilterWriter {
  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int lineCounter = 0;
  private char lastChar = '\0';

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    //this.write(str.toCharArray(), off, len);
    for(int i = off; i < (off + len); i++){
      this.write(str.charAt(i));
    }
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(char c : cbuf){
    //for(int i = off; i < (off + len); i++){
      this.write(cbuf[c]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    char letter = (char) c;
    if(lineCounter == 0){
      out.write((++lineCounter) + "\t" + letter);
    } else if (lastChar == '\r' && letter == '\n'){
      out.write("\r\n" + (++lineCounter) + "\t");
    }else if (letter == '\n' && lastChar != '\r'){
      out.write( "\n" + (++lineCounter) + "\t");
    }else if (lastChar == '\r'){
      out.write( "\r" + (++lineCounter) + "\t" + letter);
    }else if (letter != '\r'){
      out.write (letter);
    }
    lastChar = letter;
  }
}

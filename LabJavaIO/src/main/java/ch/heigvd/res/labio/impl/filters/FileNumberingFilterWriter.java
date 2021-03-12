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
  int nbLine = 1;
  boolean isWindows = false;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    if (nbLine == 1){
      str = nbLine + "\t" + str.substring(off,len+off);
      len += 2;
      nbLine += 1;
    }

    for (int i = off ; i <= len-1; i++){

      if (((str.charAt(i) == '\n') && (str.charAt(i-1) == '\r')) || str.charAt(i) == '\n'
              || (str.charAt(i) == '\r' && str.charAt(i+1) != '\n') ){

        str = str.substring(0, i + 1) + nbLine + "\t" + str.substring(i+1);

        if (nbLine < 10) {
          len += 2;
          i += 2;
        } else if (nbLine < 100) {
          len += 3;
          i += 3;
        } else {
          len += 4;
          i += 4;
        }
        nbLine += 1;
      }

    }

    super.write(str, 0, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {





    super.write(cbuf, off, len);
  }

  @Override
  public void write(int c) throws IOException {
    if (nbLine == 1){
      super.write(48+nbLine);
      super.write(9);
      nbLine += 1;
    }

    if (c == 13){
      super.write(13);
    }

    if (c == 10){
      super.write(10);
      super.write(48+nbLine);
      super.write(9);
      nbLine += 1;
    }

if (c != 10 && c != 13)
     super.write(c);
  }

}

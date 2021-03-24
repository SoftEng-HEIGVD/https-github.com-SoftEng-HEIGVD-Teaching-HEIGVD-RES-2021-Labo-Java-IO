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
  private int lineNumber = 1;
  private boolean returnLine = true;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for (int i = off; i < off + len; ++i){
      write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = off; i < off + len; ++i) {
      write(cbuf[i]);
    }
  }


  @Override
  public void write(int c) throws IOException {

    switch (c){
      case '\n':
        super.write(c);
        //writeLineNumber();
        returnLine = false;
        for (char n : Integer.toString(lineNumber).toCharArray()) {
          super.write(n);
        }
        super.write('\t');
        lineNumber++;
        break;
      case '\r':
        returnLine = true;
        super.write(c);
        break;
      default:
        if (returnLine) {
          // writeLineNumber();
          returnLine = false;
          for (char n : Integer.toString(lineNumber).toCharArray()) {
            super.write(n);
          }
          super.write('\t');
          lineNumber++;
        }
        super.write(c);

    }

  }


  /***
   * Signifies a line return and writes the line number
   * Used to compile and work greatly but stopped overnight for some reason.
   */  /*
  private void writeLineNumber(){
    returnLine = false;
    char[] number = Integer.toString(lineNumber).toCharArray();
    for (char n : number) {
      super.write(n);
    }
    super.write('\t');
    lineNumber++;
  }

  */

}

package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

import static ch.heigvd.res.labio.impl.Utils.getNextLine;

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

  private int lineNumber = 0;
  // Seperator \r and \n
  private boolean r = false;
  private boolean n = false;

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String s1 = "";
    if(lineNumber == 0) {
      s1 += ++lineNumber;
      s1 += '\t';
    }

    if(str.length() == 1) {
      if(n) {
        s1 += ++lineNumber;
        s1 += '\t';
        n = false;
        if (r) r = false;
      } else if (str.charAt(0) != '\n' && r) {
        s1 += ++lineNumber;
        s1 += '\t';
        r = false;
      } else if (str.charAt(0) == '\n') {
        n = true;
      } else if (str.charAt(0) == '\r') {
        r = true;
      }
      s1 += str;
    } else if(str.length() > 1) {
      String subStr = str.substring(off, off + len);
      String[] lines = getNextLine(subStr);
      if(lines[0].equals("")) {
        s1 += lines[1];
      } else {
        while(!lines[0].equals("")) {
          s1 += lines[0] + (++lineNumber) + '\t';
          lines = getNextLine(lines[1]);
        }

        if(!lines[1].equals("")) {
          s1 += lines[1];
        }
      }
    }


    super.write(s1, 0, s1.length());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    this.write(cbuf.toString(), off, len);
  }

  @Override
  public void write(int c) throws IOException {

    this.write(Character.toString((char) c), 0, 1);
  }

}

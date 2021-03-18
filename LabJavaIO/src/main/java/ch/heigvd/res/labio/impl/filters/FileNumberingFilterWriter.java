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

  private int nbLines = 0;
  boolean isLastIsN = false;
  boolean isLastIsR = false;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String newStr = "";
    int newChar = 0;
    if(nbLines == 0){
      newStr = str.substring(0, off) + "1\t";
      ++nbLines;
      newChar += 2;
    }
    newStr += str.substring(off);
    for (int i = 0; i < newStr.length(); ++i){
      if(newStr.charAt(i) == '\r' || newStr.charAt(i) == '\n'){
        ++nbLines;
        ++i;
        if (i < newStr.length()){
          if(newStr.charAt(i) == '\n'){
            ++i;
          }
        }
        newStr = newStr.substring(0, i) + Integer.toString(nbLines) + "\t" + newStr.substring(i);
        newChar += 1;
        newChar += Integer.toString(nbLines).length();
      }
    }

    out.write(newStr, off, len + newChar);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String str = new String(cbuf);
    write(str, off, len);
  }

  @Override
  public void write(int c) throws IOException {
    if(nbLines == 0){
      ++nbLines;
      out.write('1');
      out.write('\t');
    }
    if (isLastIsR || isLastIsN){
      if(c != '\n'){
        ++nbLines;
        //out.write((char)(nbLines + '0'));
        out.write(Integer.toString(nbLines));
        out.write('\t');
      }
    }
    isLastIsR = c == '\r';
    isLastIsN = c == '\n';
    out.write(c);
  }

}

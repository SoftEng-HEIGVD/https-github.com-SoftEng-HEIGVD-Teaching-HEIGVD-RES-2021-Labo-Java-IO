package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

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

  private int idLine = 1;
  private  boolean init = false;
  private int previousChar;

  @Override
  public void write(String str, int off, int len) throws IOException {
      String s = str.substring(off, off + len);
      String[] line = new String[2];
      StringBuilder res = new StringBuilder();
      do {
        line = Utils.getNextLine(s);
        if (!init){
          init = true;
          res.append(idLine).append("\t");
        }
        res.append(line[0]);
        if (line[0].length() != 0){
          ++idLine;
          res.append(idLine).append("\t");
        }
        s = line[1];

      }while (line[0].length() != 0);
      res.append(line[1]);
      out.write(res.toString());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
      write(new String(cbuf, off,len));
  }

  @Override
  public void write(int c) throws IOException {

    String res = "";
    if (!init){
      init = true;
      String noLine = String.valueOf(idLine);
      for (int i=0; i<noLine.length(); i++) {
        int fig = noLine.charAt(i);
        out.write(fig);
      }
      out.write('\t');
    }
    if (c == '\n' || previousChar != '\r'){
      out.write(c);
    }

    if (c == '\n' || previousChar == '\r'){
      ++idLine;
      String noLine = String.valueOf(idLine);
      for (int i=0; i<noLine.length(); i++) {
        int fig = noLine.charAt(i);
        out.write(fig);
      }
      out.write('\t');
    }
    if (c != '\n' && previousChar == '\r'){
      out.write(c);
    }
    previousChar = c;
  }

}

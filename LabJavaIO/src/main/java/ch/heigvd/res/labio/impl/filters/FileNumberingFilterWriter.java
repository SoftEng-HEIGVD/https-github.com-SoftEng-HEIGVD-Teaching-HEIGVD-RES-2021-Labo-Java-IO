package ch.heigvd.res.labio.impl.filters;

import java.io.*;
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
 *
 * Modified by Noah Fusi and Janis Chiffelle
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  private int counter = 1;
  private char lastChar;

  @Override
  public void write(String str, int off, int len) throws IOException {
    String[] strParse = str.substring(off, len + off).split("(?<=(?>\\r\\n|\\r|\\n))");

    for (String s : strParse) {
      if (counter == 1) {
        out.write(counter++ + "\t");
      }

      if (s.contains("\n") || s.contains("\r")) {
        out.write(s + counter++ + '\t');
      } else {
        out.write(s);
      }
    }
  }


  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(int c) throws IOException {
    if (counter == 1) {
      out.write(counter++ + "\t");
    }

    if (lastChar == '\r' && c != '\n') {
      out.write(lastChar);
      out.write(counter++ + "\t");
      out.write(c);
    } else if (lastChar == '\r') {
      out.write("\r\n");
      out.write(counter++ + "\t");
    } else if  (c == '\n'){
      out.write(c);
      out.write(counter++ + "\t");
    } else if (c != '\r'){
      out.write(c);
    }

    lastChar = (char) c;
  }
}

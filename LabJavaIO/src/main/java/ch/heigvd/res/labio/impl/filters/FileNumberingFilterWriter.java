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

  private int lineCounter = 1;
  private char lastChar;

  @Override
  public void write(String str, int off, int len) throws IOException {
    if(len + off > str.length())
    {
      throw new IOException("Wrong parameters : the desired length is greater than the string length minus the offset");
    }

    //split the string in substrings with '\n', '\r'and '\r\n' for the separators
    String[] strParse = str.substring(off, len + off).split("(?<=(?>\\r\\n|\\r|\\n))");

    for (String s : strParse) {
      //if it's the first line, we add the line number and the tab
      if (lineCounter == 1) {
        out.write(lineCounter++ + "\t");
      }

      //check if there is a line separator
      if (s.contains("\n") || s.contains("\r")) {
        out.write(s + lineCounter++ + '\t');
      } else {
        out.write(s);
      }
    }
  }


  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    if(off + len > cbuf.length)
    {
      throw new IOException("Wrong parameters : the desired length is greater than the string length minus the offset");
    }

    for (int i=off; i < off + len; i++) {
      this.write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    if (lineCounter == 1) {
      out.write(lineCounter++ + "\t");
    }

    /*If the current character is '\r' we do nothing because we need to know if it's the windows line separator '\r\n'
      If it's '\n' we write the character and the line counter plus the tab
      If the last char is '\r' and the current one is '\n', it's the windows line separator, so we write the separator
        in one write + the line counter + tab . If it's another character we print '\r' + the line counter + tab
        and the current character
      Else we just write the character
     */
    if (lastChar == '\r' && c != '\n') {
      out.write(lastChar);
      out.write(lineCounter++ + "\t");
      out.write(c);
    } else if (lastChar == '\r') {
      out.write("\r\n");
      out.write(lineCounter++ + "\t");
    } else if  (c == '\n'){
      out.write(c);
      out.write(lineCounter++ + "\t");
    } else if (c != '\r'){
      out.write(c);
    }

    //We stock the current char
    lastChar = (char) c;
  }
}

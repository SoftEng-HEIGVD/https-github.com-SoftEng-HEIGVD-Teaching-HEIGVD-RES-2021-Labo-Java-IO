package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
  private static int lineNb = 1;

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private static boolean firstLineForIntFct = true;
  public FileNumberingFilterWriter(Writer out) {
    super(out);
    lineNb = 1;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String newStr = "";
    if(lineNb == 1) {
      newStr =  lineNb++ + "\t";
    }
    Pattern pattern =  Pattern.compile("(\r\n|\r|\n|\n\r)");
    Matcher matcher = pattern.matcher(str);
    int posSeparateur = 0;
    boolean hasSep = false;
    if(matcher.find()){
      posSeparateur = matcher.end() - 1;
      hasSep = true;
    }
    for(int i = off; i < off+len;i++)
    {
      newStr += str.charAt(i);
      if(hasSep && i == posSeparateur)
      {
        newStr += lineNb++ + "\t";
        int offset = i + 1;
        matcher = pattern.matcher(str.substring(offset));
        if(matcher.find()){
          posSeparateur = offset + matcher.end() - 1;
        }else{
          hasSep = false;
        }
      }
    }
    super.write(newStr, 0, newStr.length() );
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    throw new UnsupportedOperationException("The student has not implemented this method yet.");
    //String string = new String(cbuf);
  }

  @Override
  public void write(int c) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
    if (firstLineForIntFct) {
      super.write(lineNb++ + 48);
      super.write((int) '\t');
      firstLineForIntFct = false;
    }

    if ((char) c == '\n') {
      super.write(c);
      super.write(lineNb++ + 48);
      super.write((int) '\t');
    } else {
      super.write(c);
    }
  }
}
package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
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
  private int currentLine = 1;
  private boolean firstLine = true;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String tmp = str.substring(off, off + len);
    String[] nextLine = Utils.getNextLine(tmp);

    if (firstLine) {
      out.write(currentLine + "\t");
      firstLine = false;
    }

    while(true){
      if(nextLine[0] == "") {
        if (nextLine[1] == ""){
          break;
        }else{
          String subStr = nextLine[1];//.substring(off, len);
          out.write(subStr);
          break;
        }
      }else {
        String subStr = nextLine[0];//.substring(off, len);
        out.write(subStr);
        nextLine = Utils.getNextLine(nextLine[1]);
      }

      out.write(++currentLine + "\t");
    }


  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

  }

  @Override
  public void write(int c) throws IOException {
    if(firstLine) {
      out.write(currentLine + "\t");
      firstLine = false;
    }

    out.write(c);
    if (c == '\n'){
      out.write(++currentLine + "\t");
    }


  }

}

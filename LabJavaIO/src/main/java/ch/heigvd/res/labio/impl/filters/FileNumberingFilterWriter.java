package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
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
  private boolean isFirstCall;
  private int lineNumber;
  private boolean reachedLineBrake;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    this.isFirstCall = true;
    this.lineNumber = 1;
    this.reachedLineBrake = false;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    StringBuilder sOut = new StringBuilder();

    str = str.substring(off, off + len);
    String[] lines = str.split("(?<=\n|\r(?!\n))");

    for(String line : lines){
      if(this.isFirstCall && !this.reachedLineBrake) {
        this.isFirstCall = false;
        sOut.append(this.lineNumber + "\t");
      }
      sOut.append(line);
      this.reachedLineBrake = line.endsWith("\n") || line.endsWith("\r");
      if(this.reachedLineBrake){
        sOut.append(++this.lineNumber + "\t");
      }
    }

    out.write(String.valueOf(sOut));
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
   this.write(new String(cbuf));
  }

  @Override
  public void write(int c) throws IOException {
    boolean isLineBreak = (c == '\r' || c == '\n');

    if(this.isFirstCall && !this.reachedLineBrake) {
      this.isFirstCall = false;
      out.write(this.lineNumber + "\t");
    }

    if(this.reachedLineBrake && !isLineBreak){
      out.write(++this.lineNumber + "\t");
    }
    out.write(c);
    this.reachedLineBrake = isLineBreak;
  }


}

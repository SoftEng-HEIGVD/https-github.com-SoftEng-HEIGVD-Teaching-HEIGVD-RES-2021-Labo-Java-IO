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
 * Hello\n\World -> 1\tHello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int lineNumber = 1; // lines are numbered from 1 not 0

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    /*
     * This function writes the input string to the output, recursing if the given string contains multiple newline-
     * separated parts. We can establish whether recursion is required using Utils.getNextLine. Additionally, if the
     * given string ends with a newline, we write the next line number already, not at the next call of this function.
     */
    if (lineNumber == 1) out.write(lineNumber++ + "\t"); // handle initial write
    String substr = str.substring(off, off + len);
    String[] lines = Utils.getNextLine(substr);

    if (!lines[0].equals("") && !lines[1].equals("")) {
      out.write(lines[0], 0, lines[0].length());
      out.write(lineNumber++ + "\t"); // already write next line number + tab before recursing
      this.write(lines[1]);
    } else if (!lines[0].equals("")) {
      out.write(lines[0], 0, lines[0].length());
      out.write(lineNumber++ + "\t"); // same here, write next line number + tab already.
    } else if (!lines[1].equals("")) {
      out.write(lines[1], 0, lines[1].length());
    }

  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    // just use our recursive String implementation
    this.write(new String(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    // Write the character, but as in String impl. we write the next line number as soon as we encounter \n.
    if (lineNumber == 1) out.write(lineNumber++ + "\t");
      out.write(c);
    if (c == '\n') {
      out.write(lineNumber++ + "\t");
    }
  }

}

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
  private int fileNb;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    fileNb = 1;
  }

  private String addNumberToString(String str) {
    StringBuilder result = new StringBuilder();
    if (fileNb == 1) {
      ++fileNb;
      result.append("1\t");
    }
    result.append(str);
    if (str.indexOf('\n') != -1) result.append(fileNb++).append('\t');
    return result.toString();
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    StringBuilder result = new StringBuilder();
    int prevOff = off;
    int i = str.indexOf('\n') + 1;
    do {
      String test = (i==0 ? str : str.substring(prevOff, i));
      result.append(addNumberToString(test));
      prevOff = i;
      i = str.indexOf('\n', prevOff) + 1;
    } while (i != 0);

    out.write(result.toString());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(int c) throws IOException {
    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}

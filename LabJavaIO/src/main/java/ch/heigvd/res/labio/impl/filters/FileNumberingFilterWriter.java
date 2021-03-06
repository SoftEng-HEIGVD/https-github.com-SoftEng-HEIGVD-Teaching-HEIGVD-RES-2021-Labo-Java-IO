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

  private int no;

  public FileNumberingFilterWriter(Writer out) throws IOException {
    super(out);
    insertNumerotation();
  }

  private void insertNumerotation() throws IOException {
    super.out.write((++no + '0'));
    super.out.write(9); // Tab
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    StringBuilder toConsider = new StringBuilder();
    for(int i = off; i < off + len; i++)
      toConsider.append(cbuf[i]);

    String[] lines = Utils.getNextLine(toConsider.toString());
    for(String line : lines) {
      if(line.isEmpty())
        continue;

      for(int c : line.toCharArray())
        super.out.write(c);

      if(!lines[0].isEmpty())
        insertNumerotation();
    }
  }

  @Override
  public void write(int c) throws IOException {
    String possibleNewLine1 = "", possibleNewLine2 = "", bufferContent = super.out.toString();
    // Si les deux derniers sont \r, \n ou si les 4 derniers sont \r\n, insérer une numérotation
    if(bufferContent.length() >= 1)
      possibleNewLine1 = bufferContent.substring(bufferContent.length() - 1);
    if(bufferContent.length() >= 2)
      possibleNewLine2 = bufferContent.substring(bufferContent.length() - 2);

    if(possibleNewLine1.equals("\r") || possibleNewLine1.equals("\n") || possibleNewLine2.equals("\r\n"))
      if(bufferContent.substring(bufferContent.length() - 1).toCharArray()[0] != (char)9) {
        insertNumerotation();
        return;
      }
    super.out.write(c);
  }

}

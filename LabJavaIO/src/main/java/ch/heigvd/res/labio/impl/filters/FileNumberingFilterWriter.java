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

  private int lineNb = 1;
  private boolean newline = false;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String substr = str.substring(off, off + len);
    String[] nextLines = Utils.getNextLine(substr);

    if (lineNb == 1)
      insertLineNB();

    while (!nextLines[0].equals("")) {
//      super.flush();
      super.write(nextLines[0], 0, nextLines[0].length());
      insertLineNB();
      nextLines = Utils.getNextLine(nextLines[1]);
    }

    super.write(nextLines[1], 0, nextLines[1].length());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(String.valueOf(cbuf, off, len));
  }

  @Override
  public void write(int c) throws IOException {
    // si le texte fini par \r et qu'il n'y a pas de char qui est lu après, le numéro de ligne
    // suivant n'est pas inséré. L'appelant de la méthode doit gérer ce cas, car ici il n'est
    // pas possible de prédire le prochain char

    if (lineNb == 1 || (newline && c != '\n'))
      insertLineNB();

    super.write(c);

    if (c == '\n')
      insertLineNB();

    newline = c == '\r';
  }

  private void insertLineNB() throws IOException {
    String s = lineNb++ + "\t";
    super.write(s, 0, s.length());
  }
}

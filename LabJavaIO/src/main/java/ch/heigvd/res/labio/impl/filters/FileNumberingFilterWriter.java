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
  private String last2Chars;
  private char buffer;

  public FileNumberingFilterWriter(Writer out) throws IOException {
    super(out);
    insertNumerotation();
  }

  private void insertNumerotation() throws IOException {
    super.out.write(String.valueOf(++no));
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
    for(int i = 0; i < lines.length; i++) {
      if(lines[i].isEmpty())
        continue;

      for(int c : lines[i].toCharArray())
        super.out.write(c);

      if(i != lines.length - 1)
        insertNumerotation();
    }
  }

  @Override
  public void write(int c) throws IOException {
    // la première fois que l'on accède le buffer, last2Chars est à null
    if(last2Chars != null) {
      // Dans le cas où le dernier caractère écrit est un retour à la ligne, on place dans le buffer
      // c, car on ne sait pas s'il faut faire le retour à la ligne maintenant (simple \r ou \n) ou si
      // c'est un retour à la ligne double (\r\n)
      if(last2Chars.charAt(1) == '\r' || last2Chars.charAt(1) == '\n' && buffer == 0)
        buffer = (char)c;

      // Si nos deux derniers caractères sont du type \r-, \n- ou \r\n, on va ajouter une nouvelle ligne
      if((last2Chars.charAt(0) == '\r')
              || (last2Chars.charAt(0) == '\n')
              ||  (last2Chars.charAt(0) == '\r' && last2Chars.charAt(1) == '\n')) {
          // Si c'est un double retour, on écrit d'abord \n puis on insert la numérotation
          if(buffer == '\r' || buffer == '\n') {
            super.out.write(buffer);
            insertNumerotation();
          // Si c'est un simple retour, on insert la numérotation puis le premier caractère de la ligne suivante
          } else {
            insertNumerotation();
            super.out.write(buffer);
          }
          buffer = 0;
      }
    }
    // Si on a qqch dans le buffer, on l'écrit pas de suite dans la sortie
    if(buffer == 0)
      super.out.write(c);
    last2Chars = super.out.toString().substring(super.out.toString().length() - 2);
    // Si on a qqch dans le buffer, on met à jour last2Chars avec le contenu du buffer
    if(buffer != 0)
      last2Chars = last2Chars.substring(last2Chars.length() - 1) + buffer;
  }
}

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

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  private int cmptLigne = 1;
  private boolean changementLigne = true;
  private char precendentChar = 0;

  @Override
  public void write(String str, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

      for(int position = off; position < len + off; position++ )
      {
          if(changementLigne && str.charAt(position) != '\r' && str.charAt(position) != '\n')
          {
              out.write(cmptLigne++ + "\t");
              changementLigne = false;
          }

          // Ecrit le char dans le stream
          out.write(str.charAt(position));

          // Control si il y a un changement de ligne à faire à la prochaine itération de la fonction
          if(str.charAt(position) == '\r' || str.charAt(position) == '\n')
          {
              changementLigne = true;
          }
      }

      // Controle si il y a eu un retour à la ligne en dernier char
      if(changementLigne)
      {
          out.write(cmptLigne++ + "\t");
          changementLigne = false;
      }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

      for(int position = off; position < len + off; position++ )
      {
          if(changementLigne && cbuf[position] != '\r' && cbuf[position] != '\n')
          {
              out.write(cmptLigne++ + "\t");
              changementLigne = false;
          }

          // Ecrit le char dans le stream
          out.write(cbuf[position]);

          // Control si il y a un changement de ligne à faire à la prochaine itération de la fonction
          if(cbuf[position] == '\r' || cbuf[position] == '\n')
          {
              changementLigne = true;
          }
      }

      // Controle si il y a eu un retour à la ligne en dernier char
      if(changementLigne)
      {
          out.write(cmptLigne++ + "\t");
          changementLigne = false;
      }
  }

  @Override
  public void write(int c) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

      // Fait le changement de ligne s'il y en a un (et pas deux s'il y a des \r et \n qui se suivent)
      if(changementLigne && (c != '\r' && c != '\n'))
      {
          out.write(cmptLigne++ + "\t");
          changementLigne = false;
      }

      // Ecrit le char dans le stream
      out.write((char)c);

      // Control si il y a un changement de ligne à faire à la prochaine itération de la fonction
      if(c == '\r' || c == '\n')
      {
          changementLigne = true;
      }

      // Sauvegarde du char traité
      precendentChar = (char) c;
  }
}

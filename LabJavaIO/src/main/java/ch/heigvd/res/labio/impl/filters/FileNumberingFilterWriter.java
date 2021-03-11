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

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  private int cmptLigne = 1;
  private char precendentChar = 0;
  //private boolean changementLigne = true; // First Version

  @Override
  public void write(String str, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

      /*String strDepart = str.substring(off,len);

      String[] lignes = Utils.getNextLine(strDepart);

      while (!lignes[0].equals(""))
      {
          out.write(cmptLigne++ + "\t" + lignes[0]);
          lignes = Utils.getNextLine(lignes[1]);
          System.out.println("/" + lignes[1] + "/");
      }*/

      for(int position = off; position < len + off; position++ )
      {
          // Appel la fonction write(char)
          this.write(str.charAt(position));
      }


      /* First Version
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
      }*/
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

      for(int position = off; position < len + off; position++ )
      {
          // Appel la fonction write(char)
          this.write(cbuf[position]);
      }

      /* First Version
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
      }*/
  }

  @Override
  public void write(int c) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

      if(cmptLigne == 1) {
          out.write(cmptLigne++ + "\t");
          out.write((char)c);
      }
      else if(c == '\n')
      {
          out.write((char)c);
          out.write(cmptLigne++ + "\t");
      }
      else if(precendentChar == '\r')
      {

          out.write(cmptLigne++ + "\t");
          out.write((char)c);
      }
      else
      {
          // Ecrit le char dans le stream
          out.write((char)c);
      }

      // Sauvegarde du char traité
      precendentChar = (char) c;

      /* First Version
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

      */
  }
}

package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines)
  {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
      String[] ret = {"",""};

      for(int position = 0; position < lines.length(); position++ )
      {
          // Ecrit le char dans le stream
          ret[0] += lines.charAt(position);

          // Itère jusqu'au changement de ligne
          if(lines.charAt(position) == '\r' || lines.charAt(position) == '\n')
          {
              // Contrôle s'il y a un \r ou \n supplémentaire
              if(position < lines.length() - 1 && (lines.charAt(position + 1) == '\r' || lines.charAt(position + 1) == '\n'))
              {
                  position++;
              }

              // Met le reste de lines dans la deuxième partie de la String[] de retour
              ret[1] += lines.substring(position);

              // Quitte la boucle et la fonction
              break;
          }
      }

      return ret;
  }

}

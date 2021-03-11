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
      String[] ret = new String[]{"",""};

      int positionRetourLigneN = lines.indexOf('\n');
      int positionRetourLigneR = lines.indexOf('\r');
      int position;

      // Cas spécial
      if(positionRetourLigneN == -1 && positionRetourLigneR == -1)
      {
          ret[1] = lines;
          return ret;
      }

      // Affectation de la position de fin de la premier ligne
      position = Math.max(positionRetourLigneN, positionRetourLigneR);

      // Met la première ligne dans la première case du tableau
      ret[0] = lines.substring(0, ++position);
      // Met le reste dans la deuxième case du tableau
      ret[1] = lines.substring(position);

      return ret;
  }

}

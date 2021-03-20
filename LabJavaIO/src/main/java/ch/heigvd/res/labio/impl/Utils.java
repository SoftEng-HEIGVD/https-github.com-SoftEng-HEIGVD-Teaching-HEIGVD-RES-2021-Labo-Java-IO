package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 * modifié par Laurent Tailhades
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


  public static String[] getNextLine(String lines) {


    if (lines.contains("\r\n")) {
       return splitLine(lines, "\r\n");
    }

    else if (lines.contains("\r")) {
      return splitLine(lines, "\r");
    }


    else if (lines.contains("\n")) {
      return splitLine(lines, "\n");
    }

    else {
      return new String[]{"", lines};
    }
  }

  /**
   * Cette méthode transforme la ligne en argument selon le séparateur (/n, /r, /r/n)
   *
   *
   * @param lines un string qui contient une ou plusieurs ligne
   * @param separator le type de séparateur
   * @return un array avec 2 éléments, le séparateur adéquat est ajouté à la première ligne
   */
  private static String[] splitLine(String lines, String separator) {
    String[] parts = lines.split(separator,2);
    return new String[]{parts[0] + separator, parts[1]};
  }


}



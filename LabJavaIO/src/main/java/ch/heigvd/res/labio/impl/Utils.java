package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 *
 * modified by Mario Tomic
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
    int lineBreakIndex = 0;

    String[] lineBreakers = { "\n", "\r", "\r\n" };
    for (String s : lineBreakers) {
      lineBreakIndex = lines.indexOf(s);
      if (lineBreakIndex != -1) {
        break;
      }
    }

    if (lineBreakIndex == -1) {
      return new String[] { "", lines };
    }

    return new String[] { lines.substring(0, lineBreakIndex + 1), lines.substring(lineBreakIndex + 1) };

  }
}

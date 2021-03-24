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
  public static String[] getNextLine(String lines) {
    String[] resullt;

    // Line with separator "\r\n"
    if (lines.contains("\r\n"))
      resullt = split("\r\n", lines);

    // Line with separator "\r"
    else if (lines.contains("\r"))
      resullt = split("\r", lines);

    // Line with separator "\n"
    else if (lines.contains("\n"))
      resullt = split("\n", lines);

    // No line separator
    else
      resullt = new String[] {"", lines};
    return resullt;
  }

  /**
   * This method splits the string according to the Separator into two strings;
   * the nextLine and the rest.
   *
   * @param separator
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text.
   */
  private static String[] split(String separator, String lines) {
    String[] nextLine = lines.split(separator, 2);
    nextLine[0] += separator;
    return nextLine;
  }

}

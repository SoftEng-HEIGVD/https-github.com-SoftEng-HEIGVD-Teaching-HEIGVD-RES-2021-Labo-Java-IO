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
    String[] s = new String[2];
    // For Linux or Windows
    int index = lines.indexOf("\n");
    if (index == -1) { // If nothing search for MacOS
      index = lines.indexOf("\r");
    }
    index++; // The line separator is included.
    // If index was -1 then it becomes 0 which leads to an empty subString for s[0].
    s[0] = lines.substring(0,index);
    s[1] = lines.substring(index); // Remaining after the new line
    return s;
  }

}

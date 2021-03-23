package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 *
 * Modified by Blanc Jean-Luc
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

    //First we want to know the index of the line separators
    int indexR = lines.indexOf("\r");
    int indexN = lines.indexOf("\n");
    int indexRN = lines.indexOf("\r\n");

    //Then we want to check if those indexes exist or not,
    // if the index = -1 then the line separator is not in the "lines" string
    // Once we know if they exist, we want to start forming our return element String[]
    // and actually return it if the line separator exists
    // we return something too if we don't find any line separators
    if(indexR != -1)
      return new String[]{lines.substring(0, indexR + 1), lines.substring(indexR + 1)};

    if(indexN != -1)
      return new String[]{lines.substring(0, indexR + 1), lines.substring(indexR + 1)};

    //here we add 2 to the index because the line separator is 2 characters
    if(indexRN != -1)
      return new String[]{lines.substring(0, indexR + 2), lines.substring(indexR + 2)};

    return new String[]{"", lines};
  }

}

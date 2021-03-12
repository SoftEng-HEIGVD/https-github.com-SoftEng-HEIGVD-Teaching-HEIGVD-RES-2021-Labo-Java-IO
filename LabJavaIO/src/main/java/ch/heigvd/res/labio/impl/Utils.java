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
    char lsMac = '\r';
    char lsUnix = '\n';
    String lsWindows = "\r\n";
    String[] result = new String[2];
    int index;
    //Check for \r\n
    if ( (index = lines.indexOf(lsWindows)) != -1) {
      result[0] = lines.substring(0, index+2);
      result[1] = lines.substring(index+2);
    }
    //check for \r
    else if ((index = lines.indexOf(lsMac)) != -1) {
      result[0] = lines.substring(0, index+1);
      result[1] = lines.substring(index+1);
    }
    //check for \n
    else if ((index = lines.indexOf(lsUnix)) != -1) {
      result[0] = lines.substring(0, index+1);
      result[1] = lines.substring(index+1);
    }
    //No line separators
    else {
      result[0] = "";
      result[1] = lines;
    }
    return result;
  }

}

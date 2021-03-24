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
    String[] arrLines = {"", lines};

    for (int i = 0; i < lines.length(); ++i){
      /* Checking for \r\n : if current char is \r and it's not the last char of the string and last next char is \n
         ! Check if is last char before checking if next char is \n to avoid "string index out of range"
       */
      if (lines.charAt(i) == '\r' && i < lines.length() - 1 && lines.charAt(i + 1) == '\n'){
        arrLines[0] = lines.substring(0, i + 2);
        arrLines[1] = lines.substring(i + 2);
        return arrLines;
      }

      /* Checking for \r and \n */
      if(lines.charAt(i) == '\n' || lines.charAt(i) == '\r'){
        arrLines[0] = lines.substring(0, i + 1);
        arrLines[1] = lines.substring(i + 1);
        return arrLines;
      }

    }
    return arrLines;
  }

}

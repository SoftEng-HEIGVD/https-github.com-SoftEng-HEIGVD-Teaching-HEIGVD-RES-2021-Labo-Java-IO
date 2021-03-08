package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;
import java.lang.String;

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
    String[] newLine = new String[]{"", ""};
    String[] symbols = new String[]{"\n", "\r", "\r\n"};
    boolean isWritten = false;


    for(String s : symbols){
      int index = lines.indexOf(s);

      if(index > -1){
        newLine[0] = lines.substring(0, index + 1);
        newLine[1] = lines.substring(index + 1);
        isWritten = true;
        break;
      }
    }
    // if lines do not contain any line separator
    if(!isWritten){
      newLine[1] = lines;
    }

    return newLine;
  }

}

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
  public static String[] getNextLine(String lines) throws RuntimeException {
    String[] parts = new String[2];
    parts[0] = "";
    parts[1] = "";

    int i = 0;
    int separator = 0;
    boolean empty = true;
    boolean rn = false;
    boolean stop = false;

    while(i < lines.length() && !stop) {
      if (lines.charAt(i) == '\r') {
        separator = 1;
        rn = true;
        i++;
      } else if (lines.charAt(i) == '\n') {
        separator = rn ? 3 : 2;
        if (rn) rn = false;
        i++;
        stop = true;
      } else {
        if (rn) {
          rn = false;
          stop = true;
        } else {
          parts[0] += lines.charAt(i);
          empty = false;
          i++;
        }
      }
    }

    if(!empty) {
      parts[1] = separator == 0 ? parts[0] : lines.substring(i);

      switch (separator) {
        case 0 : parts[0] = ""; break;
        case 1 : parts[0] += '\r'; break;
        case 2 : parts[0] += '\n'; break;
        case 3 : parts[0] += "\r\n"; break;
        default: break;
      }
    }

    return parts;
  }

}

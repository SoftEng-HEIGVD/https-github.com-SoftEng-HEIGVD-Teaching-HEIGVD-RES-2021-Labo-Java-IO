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
    s[0] = "";
    s[1] = "";
    for(int i = 0; i < lines.length(); ++i){
      s[0] += lines.substring(i, i+1);
      if(lines.charAt(i) == '\r'){
        if((i + 1) < lines.length()){
          if(lines.charAt(i+1) == '\n'){
            ++i;
            s[0] += lines.substring(i, i+1);
          }
        }
        break;
      }
      if(lines.charAt(i) == '\n')
        break;
    }
    if (s[0].length() < lines.length())
      s[1] = lines.substring(s[0].length());
    char c = s[0].charAt(s[0].length() - 1);
    if (c != '\r' && c != '\n'){
      s[1] = s[0];
      s[0] = "";
    }


    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  return s;
  }

}

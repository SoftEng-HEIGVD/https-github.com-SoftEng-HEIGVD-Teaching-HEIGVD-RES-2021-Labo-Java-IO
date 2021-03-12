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
    String[] result = new String[2];


    //find first occurence of "\n"
    int endl = lines.indexOf("\n");

    if(lines.charAt(endl-1) == '\r')
    {
      //we are on Windows
      endl = endl-1;
      result[0] = lines.substring(0,endl+2);
      result[1] = lines.substring(endl+2);
      return result;
    }
    else
    {
      //we are on linux
      result[0] = lines.substring(0,endl+1);
      result[1] = lines.substring(endl+1);
      return result;
    }
  }

}

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

      String[] lineSeparators = {"\r\n", "\r", "\n"};
      int occurence = -1, i = 0;

      // for(; i < lineSeparators.length; ++i){
      for(; i < lineSeparators.length; ++i){
          int currentOccurence = lines.indexOf(lineSeparators[i]);
          if(currentOccurence != -1){
              occurence = currentOccurence;
              // TODO: Peut mieux faire !
              break;
          }
      }

      if(occurence < 0){ // No line separator.
          return new String[] {"", lines};
      }

      int separatorLength = (lineSeparators[i]).length();


      return new String[] {lines.substring(0, occurence + separatorLength), lines.substring(occurence + separatorLength)};
  }

}

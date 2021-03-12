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
    String[] retour = new String[2];

    int idx  = lines.indexOf("\r") + 1;
    int idx1 = lines.indexOf("\n") + 1;

    if(idx == 0 && idx1 == 0){
      retour[1] = lines;
      retour[0] = "";
      return retour;
    }

    if(idx == idx1 - 1){
      if(idx1 == lines.length()){
        retour[0] = lines;
        retour[1] = "";
      } else {
        retour[0] = lines.substring(0, idx1);
        retour[1] = lines.substring(idx1);
      }
    } else if (idx < idx1) {
      if(idx == 0){ //idx1 est plus petit
        retour[0] = lines.substring(0, idx1);
        retour[1] = lines.substring(idx1);
      } else { //idx est vraiment plus petit
        retour[0] = lines.substring(0, idx);
        retour[1] = lines.substring(idx);
      }
    } else {
      if(idx1 == 0){ //idx est plus petit
        retour[0] = lines.substring(0, idx);
        retour[1] = lines.substring(idx);
      } else { //idx1 est vraiment plus petit
        retour[0] = lines.substring(0, idx1);
        retour[1] = lines.substring(idx1);
      }
    }


    //retour[0] = part1;
    //retour[1] = part2;

    return retour;
  }

}

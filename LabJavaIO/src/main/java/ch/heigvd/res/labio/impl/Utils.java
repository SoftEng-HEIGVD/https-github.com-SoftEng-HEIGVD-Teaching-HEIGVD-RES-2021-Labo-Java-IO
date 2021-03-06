package ch.heigvd.res.labio.impl;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
      String firstLine = "";
      String ligneRestante = "";
      String[] listOfSeparator = {"\r\n", "\n", "\r"};
      String sep2 = "";
      for(String sep : listOfSeparator) {
          if (lines.contains(sep)) {
              sep2 = sep;
              break;
          }
      }
      if(!sep2.isEmpty()){
          String[] l = lines.split(sep2);
          for (int i = 1; i < l.length; ++i) {
              ligneRestante += l[i] + sep2;
          }
          firstLine = l[0] + sep2;
      }else{
          ligneRestante = lines;
      }
      return new String[]{firstLine, ligneRestante};
          //TODO: We have a modify here
  }
}


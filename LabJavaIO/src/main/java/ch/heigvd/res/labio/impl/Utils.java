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
      //TODO: We have a modify here
      String firstLine = "";
      String ligneRestante = lines;
      int end = 0;
      Pattern pattern =  Pattern.compile("(\r\n|\r|\n|\n\r)");
      Matcher matcher = pattern.matcher(lines);
      if(matcher.find()){
          end = matcher.end();
          firstLine += lines.substring(0, end);
      }
      ligneRestante = lines.substring(end);
      return new String[]{firstLine, ligneRestante};

      //Sources : https://www.rgagnon.com/javadetails/java-0454.html


  }
}


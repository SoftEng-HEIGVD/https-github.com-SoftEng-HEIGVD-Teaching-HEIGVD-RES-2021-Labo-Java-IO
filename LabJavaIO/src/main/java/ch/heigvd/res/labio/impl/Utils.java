package ch.heigvd.res.labio.impl;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author Olivier Liechti
 */
public class Utils
{

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
    public static String[] getNextLine(String lines)
    {

        String[] result = new String[]{"", ""};
        String[] newLineSeparator = new String[]{"\r\n", "\r", "\n"};

        int index = -1;
        int delimSize = 0;

        for (String ls : newLineSeparator)
        {
            int tmp = lines.indexOf(ls);
            if (tmp != -1 && (tmp < index - delimSize || index == -1))
            {
                delimSize = ls.length()-1;
                index = tmp + delimSize;
            }
        }


        result[0] = lines.substring(0, index + 1);
        result[1] = lines.substring(index + 1);

        return result;
    }

}

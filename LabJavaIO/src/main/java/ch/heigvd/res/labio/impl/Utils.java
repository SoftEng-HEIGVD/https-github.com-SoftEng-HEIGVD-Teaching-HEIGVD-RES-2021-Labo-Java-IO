package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
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

        String[] result = new String[]{"", lines};

        for (int i = 0; i < lines.length(); ++i) {
            char c = lines.charAt(i);

            /* Version 2
            if (c == '\r' && i + 1 < lines.length() && lines.charAt(i + 1) == '\n') {
                result[0] = lines.substring(0, i + 2);
                result[1] = lines.substring(i + 2);
                break;
            } else if (c == '\r' || c == '\n'){
                result[0] = lines.substring(0, i + 1);
                result[1] = lines.substring(i + 1);
                break;
            }
            */

            switch (c) {
                case '\r':
                    if (i < lines.length() - 1 && lines.charAt(i + 1) == '\n') {
                        result[0] = lines.substring(0, i + 2);
                        result[1] = lines.substring(i + 2);
                        break;
                    }
                case '\n':
                    result[0] = lines.substring(0, i + 1);
                    result[1] = lines.substring(i + 1);
                    return result;
            }

        }
        return result;
    }

}

package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 * <p>
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends MyFilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
    int lineNumber = 1;
    int pastChar;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }


    @Override
    public void write(int c) throws IOException {
        if(c < 0 || c >255) throw new IOException("Invalid character");
        char[] digits  = Integer.toString( lineNumber ).toCharArray();
        if (lineNumber == 1){
            for(char digit : digits)
                super.write(digit);
            digits  = Integer.toString( lineNumber++ ).toCharArray();
            super.write('\t');
            }

        if (c != '\n' && pastChar == '\r'){
            for(char digit : digits)
                super.write(digit);
            digits  = Integer.toString( lineNumber++ ).toCharArray();
            super.write('\t');
        }

        if (c == '\n') {
            super.write(c);
            for(char digit : digits)
                super.write(digit);
            ++lineNumber;
            super.write('\t');
        } else
            super.write(c);
        pastChar = c;

    }

}

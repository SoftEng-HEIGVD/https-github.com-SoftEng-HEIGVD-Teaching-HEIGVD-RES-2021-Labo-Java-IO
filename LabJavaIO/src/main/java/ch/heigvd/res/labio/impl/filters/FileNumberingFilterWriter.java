package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
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
 *
 * Modified by Dylan Canton, Alessandro Parrino
 */
public class FileNumberingFilterWriter extends MyFilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
    private int lineNumber = 1;
    private int pastChar;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }


    @Override
    public void write(int c) throws IOException {

        //Check if it is the first line
        if (lineNumber == 1)
            out.write(lineNumber++ + "\t");

        //Check if there is a return to the line (Mac OS)
        if (c != '\n' && pastChar == '\r')
            out.write(lineNumber++ + "\t");

        //Write the character
        out.write(c);

        //Check if there is a return to the line (Windows and Linux)
        if (c == '\n')
            out.write(lineNumber++ + "\t");

        //Saves the previous character
        pastChar = c;

    }
}
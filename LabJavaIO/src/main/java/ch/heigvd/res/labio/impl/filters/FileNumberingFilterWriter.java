package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

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
 * Hello\n\World -> 1\tHello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
    private int lineNumber = 1;
    private boolean isLineStart = true;
    private boolean precedIsR = false;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        String[] strings = Utils.getNextLine(str.substring(off, len + off));
        while (!strings[0].equals("")) {
            writeStart();
            super.write(strings[0], 0, strings[0].length());
            isLineStart = true;
            strings = Utils.getNextLine(strings[1]);
        }
        writeStart();
        super.write(strings[1], 0, strings[1].length());
    }

    private void writeStart() throws IOException {
        if (isLineStart) {
            isLineStart = false;
            write(lineNumber++ + "\t");
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        write(new String(cbuf), off, len);
    }

    @Override
    public void write(int c) throws IOException {
        if (c == '\r') {
            writeStart();
            isLineStart = true;
            precedIsR = true;
        } else if (c == '\n') {
            if (precedIsR)
            {
                precedIsR = false;
            }
            else
            {
                writeStart();
                isLineStart = true;
            }
        } else {
            writeStart();
            precedIsR = false;
        }
        super.write(c);
    }
}
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
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private final char TAB_CHAR = '\t';

    private int lineNumber = 1;
    private boolean backslashR = false;

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        write(str.toCharArray(), off, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < off + len; ++i)
            write(cbuf[i]);
    }

    @Override
    public void write(int c) throws IOException {
        // First line
        if (lineNumber == 1) {
            super.write(String.valueOf(lineNumber++) + TAB_CHAR);
            super.write(c);
        }
        // '/r' case
        else if ((char) c == '\r') {
            backslashR = true;
            super.write(c);
        }
        // Line break with '\n' or "\r\n"
        else if ((char) c == '\n') {
          backslashR = false;
          super.write(c);
          super.write(String.valueOf(lineNumber++) + TAB_CHAR);
        }

        // Line break with '\r'
        else if (backslashR) {
            backslashR = false;
            super.write(String.valueOf(lineNumber++) + TAB_CHAR);
            super.write(c);
        }
        // Other character
        else
            super.write(c);
    }
}



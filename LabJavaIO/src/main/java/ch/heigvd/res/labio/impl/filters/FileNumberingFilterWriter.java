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

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

    private int lineNumber;
    private boolean beginning;
    private boolean newLineFound;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
        lineNumber = 1;
        beginning = true;
        newLineFound = false;
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        write(str.toCharArray(), off, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        if(off < 0 || len < 0 || off+len < 0 || off + len >  cbuf.length){
            throw new IndexOutOfBoundsException();
        }
        for (int i = off; i < off + len; i++) {
            write(cbuf[i]);
        }
        if (newLineFound) {
            newLineFound = false;
            insertNewLine();
        }
    }

    @Override
    public void write(int c) throws IOException {
        if (beginning) {
            beginning = false;
            insertNewLine();
        }
        if (c == '\n' || c == '\r') {
            newLineFound = true;
        } else if (newLineFound) {
            newLineFound = false;
            insertNewLine();
        }
        out.write(c);

    }

    /**
     * Send the line number and a tabulation to the writer then update the line number.
     *
     * (Just here to avoid code duplication and make sure the line number is always incremented)
     */
    private void insertNewLine() throws IOException {
        out.write(lineNumber + "\t");
        ++lineNumber;
    }
}


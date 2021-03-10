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

    private int nbLines;
    private int lastChar;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        write(str.toCharArray(), off, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < off + len && i < cbuf.length; ++i) {
            write(cbuf[i]);
        }
    }

    @Override
    public void write(int c) throws IOException {
        if (nbLines == 0 || lastChar == '\r' && c != '\n') newLine();
        super.write(c);
        if (c == '\n') newLine();
        lastChar = c;
    }


    private void newLine() throws IOException {
        String s = ++nbLines + "\t";
        super.write(s, 0, s.length());
    }
}

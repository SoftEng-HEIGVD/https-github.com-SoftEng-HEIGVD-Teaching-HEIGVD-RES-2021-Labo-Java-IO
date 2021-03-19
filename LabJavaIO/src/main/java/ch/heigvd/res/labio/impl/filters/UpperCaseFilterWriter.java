package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {

    public UpperCaseFilterWriter(Writer wrappedWriter) {
        super(wrappedWriter);
    }

    /**
     *
     * @param str
     * @param off
     * @param len
     * @throws IOException
     * @author Nicolas and Ryan
     */
    @Override
    public void write(String str, int off, int len) throws IOException {
        super.write(str.toUpperCase(), off, len);
    }

    /**
     * @param cbuf
     * @param off
     * @param len
     * @throws IOException
     * @author Nicolas and Ryan
     */
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            if (Character.isLetter(cbuf[i])) {
                cbuf[i] = Character.toUpperCase(cbuf[i]);
            }
        }
        super.write(cbuf, off, len);
    }

    /**
     *
     * @param c
     * @throws IOException
     * @author Nicolas and Ryan
     */
    @Override
    public void write(int c) throws IOException {
        super.write(Character.toUpperCase(c));
    }

}

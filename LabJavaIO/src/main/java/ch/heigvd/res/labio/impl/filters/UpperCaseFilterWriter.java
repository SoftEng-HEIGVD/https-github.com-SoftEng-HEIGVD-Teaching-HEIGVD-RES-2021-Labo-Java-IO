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

    @Override
    public void write(String str, int off, int len) throws IOException {
        // On applique le toUpperCase seulement sur la partie de la chaine qui doit etre écrite
        super.write(str.substring(off, len + off).toUpperCase(), 0, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        char[] buf = new char[len];
        for (int i = off; i < off + len; i++) {
            buf[i - off] = Character.toUpperCase(cbuf[i]);
        }
        super.write(buf, 0, len);
    }

    @Override
    public void write(int c) throws IOException {
        super.write(Character.toUpperCase(c));
    }
}
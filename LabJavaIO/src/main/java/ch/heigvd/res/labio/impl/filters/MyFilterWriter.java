package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class MyFilterWriter extends FilterWriter {

    protected MyFilterWriter(Writer wrappedWriter) {
        super(wrappedWriter);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        int size = off+len;
        for(int i = off; i <size ; ++i){
            write(str.charAt(i));
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        int size = off + len;
        for(int i = off ; i < size; ++i)
            write(cbuf[i]);
    }

}

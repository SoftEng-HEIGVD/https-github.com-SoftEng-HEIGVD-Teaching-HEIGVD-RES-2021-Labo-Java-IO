package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * This class acts as an intermediate for the application of a filter,
 * the class has been created to avoid the repetition of code in the subclasses,
 * if you add a new filter it must inherit from this class and override
 * the method write( char ).
 *
 * @Author Canton Dylan and Alessandro Parrino
 */
public class MyFilterWriter extends FilterWriter {

    protected MyFilterWriter(Writer wrappedWriter) {
        super(wrappedWriter);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        //Travel through the characters in the string and calls write( char ).
        int size = off+len;
        for(int i = off; i <size ; ++i){
            write(str.charAt(i));
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        //Travel through the characters in the array and calls write( char ).
        int size = off + len;
        for(int i = off ; i < size; ++i)
            write(cbuf[i]);
    }

}
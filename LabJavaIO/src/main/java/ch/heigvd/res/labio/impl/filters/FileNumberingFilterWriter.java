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
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
    int lineNumber = 1;
    int pastChar;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        /*
        str = str.substring(off, off + len);
        StringBuilder builder = new StringBuilder("");
        boolean isOneLine = Utils.getNextLine(str)[0].equals("");

        if (isOneLine) {

            if (lineNumber == 1) builder.append(lineNumber++ + "\t" + Utils.getNextLine(str)[1]);
            else builder.append(Utils.getNextLine(str)[1]);

        } else {

            if (lineNumber == 1) builder.append(lineNumber++ + "\t" + Utils.getNextLine(str)[0] + lineNumber++ + "\t");
            else builder.append(Utils.getNextLine(str)[0] + lineNumber++ + "\t");

            String tmp = Utils.getNextLine(str)[1];

            while (!Utils.getNextLine(tmp)[0].equals("")) {
                builder.append(Utils.getNextLine(tmp)[0] + lineNumber++ + "\t");
                tmp = Utils.getNextLine(tmp)[1];
            }

            builder.append(Utils.getNextLine(tmp)[1]);
        }

        super.write(builder.toString(), 0, builder.toString().length());
*/
       int size = off+len;
        for(int i = off; i <size ; ++i){
            write(str.charAt(i));
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
       // write(new String(cbuf), off, len);
        int size = off + len;
        for(int i = off ; i < size; ++i)
            write(cbuf[i]);
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

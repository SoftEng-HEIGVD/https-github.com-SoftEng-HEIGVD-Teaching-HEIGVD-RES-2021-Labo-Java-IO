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
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());


    public FileNumberingFilterWriter(Writer out) {
        super(out);
        lineCounter = 1;
    }

    static long lineCounter = 1;
    static char previousChar;

    @Override
    public void write(String str, int off, int len) throws IOException {
        //throw new UnsupportedOperationException("The student has not implemented this method yet.");

        if (str.equals("")) {
            return;
        }

        String tempStr = "";
        String[] oneLineAndRest = Utils.getNextLine(str);


        if (lineCounter == 1) {
            tempStr += lineCounter;
            tempStr += "\t";
            ++lineCounter;
        }


        while (!oneLineAndRest[0].equals("")){

                tempStr += oneLineAndRest[0];
                tempStr += lineCounter;
                tempStr += "\t";
                ++lineCounter;

            oneLineAndRest = Utils.getNextLine(oneLineAndRest[1]);
        }

        if(off != 0 && len != 0) {
            oneLineAndRest[1] = oneLineAndRest[1].substring(off, off + len);
        }
        tempStr += oneLineAndRest[1];

        out.write(tempStr);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        throw new UnsupportedOperationException("The student has not implemented this method yet.");
    }

    @Override
    public void write(int c) throws IOException {
        //throw new UnsupportedOperationException("The student has not implemented this method yet.");


        String str = "";

        if(lineCounter == 1){
            str += lineCounter;
            str += "\t";
            ++lineCounter;
        }


        if((c == '\r' && previousChar != '\r' && previousChar != '\n') || (c == '\n' && previousChar != '\r' &&
            previousChar != '\n')){
            str += "\r\n";
            str += lineCounter;
            str += "\t";
            ++lineCounter;
        }
        else if (c != '\r' && c != '\n'){
            str += (char)c;
        }

        previousChar = (char)c;

        out.write(str);


    }

}

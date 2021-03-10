package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer. When filter encounters a line separator,
 * it sends it to the decorated writer. It then sends the line number and a tab character, before resuming the write
 * process.
 * <p>
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

   private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
   private int lineNumber = 0;

   private boolean holdingR = false;

   public FileNumberingFilterWriter(Writer out) {
      super(out);
   }

   @Override
   public void write(int c) throws IOException {
      if ((char)c == '\r'){
         holdingR = true;
      } else {
         String s = String.valueOf((char) c);
         if (holdingR) {
            s = '\r' + s;
            holdingR = false;
         }
         write(s, 0, s.length());
      }
   }

   @Override
   public void write(char[] cbuf, int off, int len) throws IOException {
      write(cbuf.toString(), off, len);
   }

   @Override
   public void write(String str, int off, int len) throws IOException {
      String[] lines = Utils.getNextLine(str.substring(off,off+len));
      while (true) {
         if (lineNumber == 0) {
            ++lineNumber;
            writeLineNumber();
         }
         if (lines[0].equals("")) {
            super.write(lines[1], 0, lines[1].length());
            return;
         } else {
            super.write(lines[0], 0, lines[0].length());
            writeLineNumber();
            lines = Utils.getNextLine(lines[1]);
         }
      }
   }

   private void writeLineNumber() throws IOException {
      String number = String.valueOf(lineNumber);
      super.write(number + '\t', 0, number.length()+1);
      ++lineNumber;
   }
}

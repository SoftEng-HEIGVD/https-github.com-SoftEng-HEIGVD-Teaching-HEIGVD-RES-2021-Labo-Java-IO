package ch.heigvd.res.labio.impl.filters;

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

   public FileNumberingFilterWriter(Writer out) {
      super(out);
   }

   @Override
   public void write(int c) throws IOException {
      char character = (char) c;
      if (lineNumber == 0) {
         ++lineNumber;
         super.write(String.valueOf(lineNumber) + '\t', 0, 2);
         ++lineNumber;
      }
      if (character == '\r' || character == '\n') {
         super.write(character + String.valueOf(lineNumber) + '\t', 0, 3);
         ++lineNumber;
      } else {
         super.write(character);
      }
   }

   @Override
   public void write(char[] cbuf, int off, int len) throws IOException {
      write(cbuf.toString(), off, len);
   }

   @Override
   public void write(String str, int off, int len) throws IOException {
      StringBuilder temp = new StringBuilder();
      if (lineNumber == 0) {
         ++lineNumber;
         super.write(String.valueOf(lineNumber) + '\t', 0, 2);
         ++lineNumber;
      }
      for (int i = off; i < off + len; ++i) {
         temp.append(str.charAt(i));
         boolean isEndOfLine = false;
         if (str.charAt(i) == '\r') {
            if (i + 1 < off + len && str.charAt(i + 1) == '\n') {
               ++i;
            }
            isEndOfLine = true;
         } else if (str.charAt(i) == '\n') {
            isEndOfLine = true;
         }
         if (isEndOfLine) {
            temp.append(lineNumber).append('\t');
            ++lineNumber;
            super.write(temp.toString(), 0, temp.length());
            temp = new StringBuilder();
         }
      }
      super.write(temp.toString(), 0, temp.length());
   }
}

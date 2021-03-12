package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;
import jdk.jshell.execution.Util;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
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
public class FileNumberingFilterWriter extends FilterWriter
{

   private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
   private int lineNumber;
   private char lastChar;

   public FileNumberingFilterWriter(Writer out)
   {
      super(out);
      lineNumber = 1;
   }

   private void beginNewLine() throws IOException
   {
      String str = lineNumber + "\t";
      super.write(str, 0, str.length());
      ++lineNumber;
   }

   @Override
   public void write(String str, int off, int len) throws IOException
   {
      if(str == null || str.isEmpty()) return;
      String[] result;
      String sub = str.substring(off, off+len);
      result = Utils.getNextLine(sub);

      if(lineNumber == 1) beginNewLine();

      while(!result[0].isEmpty())
      {
         super.write(result[0], 0, result[0].length());
         beginNewLine();

         result = Utils.getNextLine(result[1]);
      }

      super.write(result[1], 0, result[1].length());
   }

   @Override
   public void write(char[] cbuf, int off, int len) throws IOException
   {
      write(new String(cbuf), off, len);
   }

   @Override
   public void write(int c) throws IOException
   {
      if(lineNumber == 1 || lastChar == '\r' && c != '\n')
      {
         beginNewLine();
      }

      super.write(c);

      if(c == '\n')
      {
         beginNewLine();
      }

      lastChar = (char)c;

   }

}

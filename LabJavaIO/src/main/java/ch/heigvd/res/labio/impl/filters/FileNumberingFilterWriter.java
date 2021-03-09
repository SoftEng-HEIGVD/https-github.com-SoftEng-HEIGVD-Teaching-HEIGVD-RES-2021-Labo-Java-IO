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
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int nLines;
  private boolean previousCharacterWasCarriageReturn;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    nLines = 1;
      /**
       * TODO: Trouver un meilleur nom. Je suis pas doué pour nommer des trucs.
       * https://en.wikipedia.org/wiki/Carriage_return
       */
    previousCharacterWasCarriageReturn = false;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
      write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
      for(int i = off; i < off + len; ++i){
          write(cbuf[i]);
    }
  }


  @Override
  public void write(int c) throws IOException {
      /**
       * TODO: Résultat identique à l'oeil
       * Cependant, à tester sur Windows pour test itShouldWorkOnWindows (je suis sur OSX).
       */

      /**
       * TODO: Je pense que la logique peut être mieux implémentée !
       */

      if(nLines == 1 ){
          newLineFormat();
      }

      // Add new line if the \r was alone (no \r\n)
      if(c != '\n' && previousCharacterWasCarriageReturn){
          previousCharacterWasCarriageReturn = false;
          newLineFormat();
      }

      super.write(c);

      if(c =='\r'){
          previousCharacterWasCarriageReturn = true;
      }
      else if(c == '\n'){
          previousCharacterWasCarriageReturn = false;
          newLineFormat();
      }
  }

  private void newLineFormat() throws IOException {
      super.write(Integer.toString(nLines++));
      super.write('\t');
  }
}

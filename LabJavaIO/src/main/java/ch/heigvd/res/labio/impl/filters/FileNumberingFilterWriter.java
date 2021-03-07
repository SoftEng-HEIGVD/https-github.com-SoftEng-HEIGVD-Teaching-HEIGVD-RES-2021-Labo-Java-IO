package ch.heigvd.res.labio.impl.filters;

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
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
  /*  List<Integer> indexes = new ArrayList<>();
    int index = 0;


    while(index != -1){
      index = str.indexOf("\n", index);
      if (index != -1) {
        indexes.add(index + 1);
        index++;
      }
    }

    StringBuilder builder = new StringBuilder("1\t"+ str);
    int size = indexes.size() + 1;
    for(int i = 2; i < size; ++i){
      builder.insert(indexes.get(i - 1), i + "\t");
    }
    super.write(builder.toString(), off, len);*/
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(int c) throws IOException {
    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}

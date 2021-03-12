package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor visitor) throws IOException {

    visitor.visit(rootDirectory);
    if(rootDirectory.isDirectory()) { // Only recurse if rootDirectory is a directory
      String[] paths = Objects.requireNonNull(rootDirectory.list());
      Arrays.sort(paths);             // Sort the arrays so it is alphabetical order on every os
      for (String filePath : paths) {
        explore(new File(rootDirectory + "/" + filePath), visitor);
      }
    }
  }
}

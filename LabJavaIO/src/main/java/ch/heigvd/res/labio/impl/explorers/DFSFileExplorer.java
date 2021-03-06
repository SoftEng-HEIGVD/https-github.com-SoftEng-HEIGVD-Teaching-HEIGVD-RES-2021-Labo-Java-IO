package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 * Updated by Marco Maziero
 */

public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor visitor) {
    if (rootDirectory == null) return; // File must exist
    visitor.visit(rootDirectory); // Visits the file
    File[] listFiles = rootDirectory.listFiles(); // Retrieves all the files in directory

    if (listFiles != null) {
      // Sorts the files by alphabetical order
      Arrays.sort(listFiles);

      // Iterates through each file
      for (File f : listFiles)
        explore(f, visitor);
    }

  }
}

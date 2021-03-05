package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;

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
    visitor.visit(rootDirectory); // Visits the file
    if (!rootDirectory.exists()) return; // File must exist
    File[] listFiles = rootDirectory.listFiles(); // Retrieves all the files in directory

    // Iterates through each file
    for (File f : listFiles)
      explore(f, visitor);
  }
}

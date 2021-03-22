package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.util.Arrays;

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
  public void explore(File rootDirectory, IFileVisitor vistor) {
    //Needs to be checked again
    if (rootDirectory == null) {
      return;
    }
    vistor.visit(rootDirectory);

    File[] subDirectory = rootDirectory.listFiles();

    if (subDirectory != null) {
      Arrays.sort(subDirectory);
      for (File subFile : subDirectory) {
        explore(subFile, vistor);
      }
    }
  }
}

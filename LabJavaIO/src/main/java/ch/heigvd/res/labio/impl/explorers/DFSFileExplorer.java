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
    // Check if the File exists
    if (rootDirectory == null)
      return;

    // Visit the node
    vistor.visit(rootDirectory);

    // Visit the directories recursively (sorted)
    File[] directories = rootDirectory.listFiles(File::isDirectory);
    if (directories != null) {
      Arrays.sort(directories);
      for (File currentDirectory : directories)
        explore(currentDirectory, vistor);
    }

    // Visit The files (sorted)
    File[] files = rootDirectory.listFiles(File::isFile);
    if (files != null) {
      Arrays.sort(files);
      for (File currentFile : files)
        vistor.visit(currentFile);
    }
  }

}

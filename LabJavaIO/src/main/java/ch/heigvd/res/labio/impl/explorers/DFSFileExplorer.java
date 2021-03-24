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
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor visitor) {
    // Add rootDirectory in visited nodes
    visitor.visit(rootDirectory);

    // Get all files and subdirectories in rootDirectory
    File[] files = rootDirectory.listFiles();
    // If there is no file in rootDirectory
    if(files == null) {
      return;
    }

    for(File f : files) {
      if(f.isFile()) {    // If f is a file, then add f to visited nodes
        visitor.visit(f);
      } else if(f.isDirectory()) { // If f is a subdirectory, then
        explore(f, visitor);       // continue DFS with f
      }
    }
  }
}

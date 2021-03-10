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
  public void explore(File rootDirectory, IFileVisitor visitor) {
    // we store the first directory in the filename list
    visitor.visit(rootDirectory);
    // if it is a directory, we list its content in an array
    if(rootDirectory.isDirectory()) {
      File[] listDirectoryAndFiles = rootDirectory.listFiles();
      // if there is content, we sort it alphabetically and explore it recursively
      if (listDirectoryAndFiles != null) {
        Arrays.sort(listDirectoryAndFiles);
        for (File file : listDirectoryAndFiles){
          explore(file, visitor);
        }
      }
    }

  }

}

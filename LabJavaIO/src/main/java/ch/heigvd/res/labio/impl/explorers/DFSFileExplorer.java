package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.util.ArrayList;

/**
 * This implementation of the IFileExplorer interface performs a depth-first exploration of the file system and invokes
 * the visitor for every encountered node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 *
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

   @Override
   public void explore(File rootDirectory, IFileVisitor vistor) {
      dfsRecursive(rootDirectory, vistor);
      //Go through the files in depth first, and call visitor.visit() on every node (files or folders) found.
   }

   private void dfsRecursive(File currentFile, IFileVisitor visitor) {
      visitor.visit(currentFile);
      File[] files = currentFile.listFiles();
      if (files != null) { //is a directory
         for (File file : files) {
               dfsRecursive(file, visitor);
         }
      }
   }

}

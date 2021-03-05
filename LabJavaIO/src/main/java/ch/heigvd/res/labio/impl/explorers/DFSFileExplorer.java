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
public class
DFSFileExplorer implements IFileExplorer
{

   @Override
   public void explore(File rootDirectory, IFileVisitor vistor)
   {
      vistor.visit(rootDirectory);

      File[] files;

      // Populates the array with names of files and directories
      files = rootDirectory.listFiles();

      if(files == null) return;

      File element;
      // For each pathname in the pathnames array
      for (File file : files)
      {
         explore(file, vistor);
      }
   }
}

package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.io.IOException;
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
  public void explore(File rootDirectory, IFileVisitor vistor)
  {
      vistor.visit(rootDirectory);
      exploreRecursive(rootDirectory, vistor);
  }

  /*
  *     Fonction récursive "Esclave" afin de pouvoir noter le premier rootDirectory dans le writer sans qu il y ait
  *     de répétition dans les suivants
  */
  private void exploreRecursive(File rootDirectory, IFileVisitor visitor)
  {
      // ALGO DE DFS pour explorer : https://www.techiedelight.com/traverse-given-directory-bfs-dfs-java/
      // get the list of all files and directories present in the `root`
      File[] listOfFilesAndDirectory = rootDirectory.listFiles();

      // `listFiles()` returns non-null array if `root` denotes a directory
      if (listOfFilesAndDirectory != null)
      {
          // Tire dans l'ordre alphabetique
          Arrays.sort(listOfFilesAndDirectory);

          for (File file : listOfFilesAndDirectory)
          {
              // Action sur le fichier / dossier courant
              visitor.visit(file);

              // if the file denotes a directory, recur for it
              if (file.isDirectory()) {
                  exploreRecursive(file, visitor);
              }
          }
      }
  }
}

package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.util.Arrays;

import java.io.File;
// pouvoir parcourir tous les enfants d'un dossier parent , récursif, chaque fois qu'on rencontre un noeud, on
// doit appeler une méthode sur cet objet de l'interface visite en appelant le noeud que l'on a visité. EXEMPLE:
// visitor.visit(dossier)
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
    if (rootDirectory == null) {
      return;
    }

    visitor.visit(rootDirectory);

    File[] listOfFilesAndDirectory = rootDirectory.listFiles();

    if(listOfFilesAndDirectory != null) {

      Arrays.sort(listOfFilesAndDirectory);

      for (File file : listOfFilesAndDirectory) {
        if (file.isDirectory()) {
          visitor.visit(file);
        }
        else {
          explore(file, visitor);
        }
      }
    }
  }
}

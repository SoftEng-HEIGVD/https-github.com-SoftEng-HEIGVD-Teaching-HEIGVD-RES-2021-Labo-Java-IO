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
 *
 * Modified by Blanc Jean-Luc
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor visitor) {

    //Checking that rootDirectory does exist before going further
    if(rootDirectory == null){
      return;
    }

    //if it exists, then we can visit it
    visitor.visit(rootDirectory);

    //if the file is actually a directory, then we need to go further in
    if(rootDirectory.isDirectory()){
      //we create an array that contains the directory content, we then sort this content in order to display it
      File[] folderContent =  rootDirectory.listFiles();
      Arrays.sort(folderContent);
      //adding recursive here
      for(File file : folderContent){
        explore(file, visitor);
      }
    }
  }

}

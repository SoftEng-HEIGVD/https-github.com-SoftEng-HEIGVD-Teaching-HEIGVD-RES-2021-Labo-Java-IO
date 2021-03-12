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
 * Modified by Dylan Canton, Alessandro Parrino
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {

      //Check if directory exists
      if(rootDirectory == null){
          return;
      }

      //Visit the file
      vistor.visit(rootDirectory);

      //If it's a folder
      if(rootDirectory.isDirectory()){

          //Store all folder's files
          File[] folderContent = rootDirectory.listFiles();
          //Sort files for display
          Arrays.sort(folderContent);

          //Recursion in the next folder level
          for(File file : folderContent){
              explore(file, vistor);
          }
      }
  }
}
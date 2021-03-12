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
  public void explore(File rootDirectory, IFileVisitor vistor) {

    //System.out.println("test");

    File lFiles [] = rootDirectory.listFiles();

    for (int i = 0; i < lFiles.length; ++i){
      //System.out.println(lFiles[i].getPath());
      //if (lFiles[i].isFile()){
        //System.out.println(lFiles[i].getPath());
        vistor.visit(lFiles[i]);
      //}

    }

    for (int i = 0; i < lFiles.length; ++i){
      if(lFiles[i].isDirectory())
        explore(lFiles[i], vistor);
    }

    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}

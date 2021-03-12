package ch.heigvd.res.labio.impl.explorers;


import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits moves into the subdirectories and then visits all
 * files in the directory
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    if(rootDirectory == null){
      return;
    }
    vistor.visit(rootDirectory);

    File[] children = rootDirectory.listFiles();

    if (children != null){
      // Sort directories first then files
      Arrays.sort(children, (file, t1) -> {
        if (file.isDirectory() && !t1.isDirectory()){
          return -1;
        }else if (!file.isDirectory() && t1.isDirectory()){
          return 1;
        }else{
          return file.compareTo(t1);
        }
      });
      for (File f : children){
        if (f.isDirectory()){
          explore(f, vistor);
        }else{
          vistor.visit(f);
        }
      }
    }
  }
}

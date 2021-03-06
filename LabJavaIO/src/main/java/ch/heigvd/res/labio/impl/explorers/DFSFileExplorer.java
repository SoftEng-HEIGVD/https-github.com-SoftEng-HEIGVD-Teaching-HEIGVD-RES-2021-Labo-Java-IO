package ch.heigvd.res.labio.impl.explorers;


import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

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
    if(rootDirectory == null){
      return;
    }
    vistor.visit(rootDirectory);

    File[] childs = rootDirectory.listFiles();

    if (childs != null){
      Arrays.sort(childs, new Comparator<File>() {
        @Override
        public int compare(File file, File t1) {
          if (file.isDirectory() && !t1.isDirectory()){
            return -1;
          }else if (!file.isDirectory() && t1.isDirectory()){
            return 1;
          }else{
            return file.compareTo(t1);
          }
        }
      });
      for (File f : childs){
        if (f.isDirectory()){
          explore(f, vistor);
        }else{
          vistor.visit(f);
        }
      }
    }
  }
}

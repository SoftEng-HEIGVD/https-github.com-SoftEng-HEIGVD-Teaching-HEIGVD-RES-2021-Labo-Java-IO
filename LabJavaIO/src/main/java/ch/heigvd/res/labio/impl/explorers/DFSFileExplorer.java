package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
    vistor.visit(rootDirectory);
    if (rootDirectory.list() != null) {
      Rexplore(rootDirectory, vistor);
    }
  }

  private void Rexplore(File rootDirectory, IFileVisitor vistor){

    File [] subFolder = rootDirectory.listFiles();

    Arrays.sort(subFolder, new Comparator<File>() {
      @Override
      public int compare(File o1, File o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });

    for (File f : subFolder ) {
      if(f.isDirectory()) {
        vistor.visit(f);
        Rexplore(f, vistor);
      }
      if (f.isFile()) {
        vistor.visit(f);
      }
    }
    return;
  }

}

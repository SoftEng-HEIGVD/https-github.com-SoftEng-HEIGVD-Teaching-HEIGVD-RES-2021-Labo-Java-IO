package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Stack;

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
    // Note: it explores the arbo by the end.
    Stack<File> filesStack = new Stack<File>();
    filesStack.push(rootDirectory);
    while(!filesStack.empty()){
        File file =  filesStack.pop();

        // Je sais pas si c'est correcte
        // TODO file.isFile() pas nécessaire pour le moment
        if(file.isFile()){
          vistor.visit(file);
        }else{
           File[] childrenFiles = file.listFiles();
           if(childrenFiles != null){
               for(File newFile: childrenFiles){
                   filesStack.push(newFile);
               }
           }else{
               vistor.visit(file);
           }
        }
    }

    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }
}

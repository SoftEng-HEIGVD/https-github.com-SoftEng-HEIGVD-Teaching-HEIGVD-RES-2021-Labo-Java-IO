package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

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
    public void explore(File rootDirectory, IFileVisitor vistor) throws IOException {

        File[] paths = rootDirectory.listFiles();

        // for each pathname in pathname array
        //TODO: modification personnelle, pour application
        if (rootDirectory != null) {
            vistor.visit(rootDirectory);
            if (paths != null) {

                for (File path : paths) {
                    // prints file and directory paths

                    // if the file denotes a directory, recur for it

                    if (path.isDirectory()) {
                        explore(path, vistor);
                    } else {
                        vistor.visit(path);
                    }
                    // otherwise, print it

                }
            }

            //throw new UnsupportedOperationException("The student has not implemented this method yet.");
        }
    }


}

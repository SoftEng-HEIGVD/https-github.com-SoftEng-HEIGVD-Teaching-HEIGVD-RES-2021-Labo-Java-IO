package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 *
 * @author Olivier Liechti
 * @author Modified by Nicolas and Ryan
 */
public class DFSFileExplorer implements IFileExplorer {

    @Override
    public void explore(File rootDirectory, IFileVisitor vistor) throws IOException {
        Objects.requireNonNull(rootDirectory) ;
        Objects.requireNonNull(vistor);
        File[] paths = rootDirectory.listFiles();
        vistor.visit(rootDirectory);
        if (paths != null) {
            //on trie la liste car listFiles ne garantit pas l'ordre
            Arrays.sort(paths);
            for (File path : paths) {
                if (path.isDirectory()) {
                    explore(path, vistor);
                } else {
                    vistor.visit(path);
                }
            }
        }
    }
}

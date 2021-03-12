package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    public void explore(File rootDirectory, IFileVisitor visitor) {
        if (rootDirectory == null) {
            return;
        }
        List<File> list = new ArrayList<File>();
        list.add(rootDirectory);

        while (!list.isEmpty()) {
            File currentFile = list.get(0);
            list.remove(0);

            visitor.visit(currentFile);

            if (currentFile.isDirectory()) {
                File[] files = currentFile.listFiles();

                if (files == null) {
                    return;
                }
                Arrays.sort(files);
                list.addAll(0, Arrays.asList(files));
            }
        }
    }
}

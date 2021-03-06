package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.sql.Array;
import java.util.Arrays;

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

        /* Visit root directory */
        visitor.visit(rootDirectory);

        /* List files and sub-directories in root */
        File[] listOfFiles = rootDirectory.listFiles();

        if (listOfFiles != null) {
            /*
             * Need to sort files by name because "There is no guarantee that the name strings in the
             * resulting array will appear in any specific order; they are not, in particular, guaranteed
             * to appear in alphabetical order."
             */
            Arrays.sort(listOfFiles);

            /* Visit each file and explore recursively sub-directories */
            for (File file : listOfFiles) {
                if (file.isDirectory()) {     /* sub-directory */
                    explore(file, visitor);
                } else {                      /* file */
                    visitor.visit(file);
                }
            }
        }

    }

}

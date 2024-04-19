package de.mehtrick.bjoern.base;

import de.mehtrick.bjoern.parser.reader.BjoernFileExtensions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * This class is the Base for all the generators, and provides functionality to load the correct files
 */
public abstract class AbstractBjoernGenerator extends BjoernGeneratorConfigProvided {


    /**
     * @param bjoernGeneratorConfig the default conif
     * @throws BjoernMissingPropertyException - Shows that a property is missing
     */
    public AbstractBjoernGenerator(BjoernGeneratorConfig bjoernGeneratorConfig) throws BjoernMissingPropertyException {
        super(bjoernGeneratorConfig);
        bjoernGeneratorConfig.validate();
    }

    /**
     * Loads all bjoern files from an specific folder.
     *
     * @param folder the folder to search (non recursive)
     * @return the found bjoern files
     */
    public File[] getFilesFromFolder(String folder) {
        File file = new File(folder);
        if (!file.exists()) {
            System.out.println("WARNING: The folder " + folder + " could not be found. Nothing has been generated.");
            return new File[0];
        }
        if (bjoernGeneratorConfig.isSpecRecursive()) {
            return getFilesFromFolderRecursive(file);
        } else {
            return getFilesFromFolderNonRecursive(file);
        }

    }

    private File[] getFilesFromFolderNonRecursive(File folder) {
        return folder.listFiles((dir, name) -> name.toLowerCase().endsWith(BjoernFileExtensions.zgr.toString()));
    }

    private File[] getFilesFromFolderRecursive(File folder) {
        try (Stream<Path> paths = Files.walk(folder.toPath())) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith(BjoernFileExtensions.zgr.toString()))
                    .map(Path::toFile)
                    .toArray(File[]::new);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading files from folder", e);
        }
    }
}

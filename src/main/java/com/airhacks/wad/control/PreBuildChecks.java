
package com.airhacks.wad.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author airhacks.com
 */
public interface PreBuildChecks {

    static void pomExists() {
        Path pomPath = Paths.get("pom.xml");
        if (!Files.exists(pomPath)) {
            printUsage();
            exit();
        }
    }

    static void exit() {
        System.exit(-1);
    }

    public static void printUsage() {
        String message = loadMessage("usage.txt");
        System.out.println(message);
    }

    public static String loadMessage(String fileName) {
        InputStream stream = PreBuildChecks.class.getResourceAsStream("/" + fileName);
        return load(stream);
    }

    static String load(InputStream stream) {
        try ( BufferedReader buffer = new BufferedReader(new InputStreamReader(stream))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot read from stream", ex);
        }
    }

    static void validateDeploymentDirectories(Set<Path> path) {
        long invalidDirectories = path.stream().
                map(PreBuildChecks::validateDeploymentDirectory).
                filter(valid -> valid == false).
                count();
        if (invalidDirectories != 0) {
            exit();
        }
    }

    static boolean validateDeploymentDirectory(Path path) {
        if (!Files.exists(path)) {
            System.err.printf("Directory \'%s\' does not exist\n", path);
            return false;
        }
        if (!Files.isDirectory(path)) {
            System.err.printf("%s is not a directory", path);
            return false;
        }
        return true;
    }


}

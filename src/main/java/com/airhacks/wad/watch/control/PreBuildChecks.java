
package com.airhacks.wad.watch.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            System.exit(-1);
        }
    }

    public static void printUsage() {
        String message = loadMessage("usage.txt");
        System.out.println("message = " + message);
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

}

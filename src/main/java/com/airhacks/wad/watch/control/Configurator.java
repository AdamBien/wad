
package com.airhacks.wad.watch.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author airhacks.com
 */
public interface Configurator {

    static final String WAD_CONFIGURATION_FILE = ".wadrc";

    public static Set<Path> getConfiguredFolders(List<Path> commandLineArguments) {
        Set<Path> deploymentFolders = getConfigurationFromUserDirectory();
        deploymentFolders.forEach(f -> System.out.printf("%s from ~/.wadrc\n", f));
        commandLineArguments.forEach(f -> System.out.printf("command line argument %s\n", f));
        deploymentFolders.addAll(commandLineArguments);
        System.out.println("resulting deployment folders");
        deploymentFolders.forEach(f -> System.out.println(f));
        return deploymentFolders;

    }

    static Set<Path> getConfigurationFromDirectory(Path pathToConfiguration) {
        if (!Files.exists(pathToConfiguration)) {
            return new HashSet<>();
        }
        try {
            Set<Path> deploymentFolders = Files.readAllLines(pathToConfiguration).
                    stream().
                    map(d -> d.trim()).
                    map(Paths::get).
                    collect(Collectors.toSet());
            return deploymentFolders;
        } catch (IOException ex) {
            return new HashSet<>();
        }
    }

    public static Set<Path> getConfigurationFromUserDirectory() {
        String userHome = System.getProperty("user.home");
        Path pathToConfiguration = Paths.get(userHome, WAD_CONFIGURATION_FILE);
        return getConfigurationFromDirectory(pathToConfiguration);
    }

    public static boolean userConfigurationExists() {
        return !getConfigurationFromUserDirectory().isEmpty();
    }


}

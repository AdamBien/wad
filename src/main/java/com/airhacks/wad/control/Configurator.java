
package com.airhacks.wad.control;

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
        deploymentFolders.forEach(f -> System.out.printf("%s \'%s\' %s from ~/.wadrc\n", TerminalColors.FILE.value(), f, TerminalColors.RESET.value()));
        commandLineArguments.forEach(f -> System.out.printf("command line argument %s \'%s\' %s\n", TerminalColors.FILE.value(), f, TerminalColors.RESET.value()));
        deploymentFolders.addAll(commandLineArguments);
        System.out.println("resulting deployment folders are:");
        deploymentFolders.forEach(f -> System.out.printf("%s \'%s\' %s\n", TerminalColors.FILE.value(), f, TerminalColors.RESET.value()));
        return deploymentFolders;

    }

    static Set<Path> getConfigurationFromDirectory(Path pathToConfiguration) {
        try {
            Set<Path> deploymentFolders = Files.readAllLines(pathToConfiguration).
                    stream().
                    map(d -> d.trim()).
                    map(Substitutor::substitute).
                    map(Paths::get).
                    collect(Collectors.toSet());
            return deploymentFolders;
        } catch (IOException ex) {
            return new HashSet<>();
        }
    }

    public static Set<Path> getConfigurationFromUserDirectory() {
        return getConfigurationFromDirectory(getUserHomeValue());
    }

    static boolean userConfigurationExists() {
        return userConfigurationExists(getUserHomeValue());
    }

    static Path getUserHomeValue() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, WAD_CONFIGURATION_FILE);

    }

    static boolean userConfigurationExists(Path pathToConfiguration) {
        return Files.exists(pathToConfiguration);
    }


}

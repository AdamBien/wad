
package com.airhacks.wad.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author airhacks.com
 */
public class Copier {

    private List<Long> warSizes;
    private final Collection<Path> deploymentTargetDirectories;
    private final Path fromDirectory;

    public Copier(Path from, Collection<Path> deploymentTargetDirectories) {
        this.warSizes = new ArrayList<>();
        this.fromDirectory = from;
        this.deploymentTargetDirectories = deploymentTargetDirectories;
    }

    String shortenForDisplay(Path path, int maxLength) {
        String message = path.toString();
        int length = message.length();
        if (length > maxLength) {
            return "(...)" + message.substring(length - maxLength);
        } else {
            return message;
        }
    }

    public void copy() {
        Path warPath = findWarInPath(this.fromDirectory).orElseThrow(() -> new IllegalStateException("war file was not found in the path"));
        List<Path> copyTargets = addWarName(deploymentTargetDirectories, warPath.getFileName().toString());
        copyTargets.forEach(target -> copySingle(warPath, target));
    }

    Optional<Path> findWarInPath(Path fromDirectory) {
        try (Stream<Path> stream = Files.walk(fromDirectory, 1)) {
            return stream
                .filter(file -> !Files.isDirectory(file) && file.getFileName().toString().endsWith(".war"))
                .findFirst();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    List<Path> addWarName(Collection<Path> deploymentDirectories, String warName) {
        return deploymentDirectories.
            stream().
            map(path -> path.resolve(warName)).
            collect(Collectors.toList());
    }

    void copySingle(Path from, Path to) {
        long kb;
        try {
            kb = Files.size(from) / 1024;
            warSizes.add(kb);
            System.out.printf("Copying %dkB ThinWAR %s to %s %s %s \n", kb,  from.toString(), TerminalColors.FILE.value(), shortenForDisplay(to, 40), TerminalColors.RESET.value());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            File targetFile = to.toFile();
            boolean targetFileIsLocked = !targetFile.renameTo(targetFile);
            if(targetFileIsLocked) {
                System.err.println("Cannot copy because server has locked file \""+to+"\"");
            }else{
                throw new IllegalStateException(ex.getMessage(), ex);
            }
        }
    }

    public LongSummaryStatistics warSizeStatistics() {
        return this.warSizes.stream().mapToLong(s -> s).summaryStatistics();
    }

}

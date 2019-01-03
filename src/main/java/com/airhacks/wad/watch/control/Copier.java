
package com.airhacks.wad.watch.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;

/**
 *
 * @author airhacks.com
 */
public class Copier {

    private List<Long> warSizes;
    private final List<Path> deploymentTargets;
    private final Path from;

    public Copier(Path from, List<Path> deploymentTargets) {
        this.warSizes = new ArrayList<>();
        this.from = from;
        this.deploymentTargets = deploymentTargets;
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
        deploymentTargets.forEach(target -> copySingle(this.from, target));
    }

    Path copySingle(Path from, Path to) {
        long kb;
        try {
            kb = Files.size(from) / 1024;
            warSizes.add(kb);
            System.out.printf("Copying %dkB ThinWAR to %s \n", kb, shortenForDisplay(to, 40));
            return Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }

    public LongSummaryStatistics warSizeStatistics() {
        return this.warSizes.stream().mapToLong(s -> s).summaryStatistics();
    }

}

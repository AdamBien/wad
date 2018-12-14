
package com.airhacks.wad.watch.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author airhacks.com
 */
public interface Copier {

    public static Path copy(Path from, Path to) throws IOException {
        long kb = Files.size(from) / 1024;
        System.out.printf("Copying %s (%d kB ThinWAR) to %s \n", from, kb, to);
        return Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
    }

}

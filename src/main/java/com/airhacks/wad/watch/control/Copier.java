
package com.airhacks.wad.watch.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author airhacks.com
 */
public interface Copier {

    public static Path copy(String from, String to) throws IOException {
        Path fromPath = Paths.get(from);
        Path toPath = Paths.get(to);
        return Files.copy(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING);
    }

}

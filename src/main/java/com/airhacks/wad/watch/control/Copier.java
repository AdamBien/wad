
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
        return Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
    }

}

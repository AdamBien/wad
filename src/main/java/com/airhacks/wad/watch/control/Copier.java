
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

    static String shortenForDisplay(Path path, int maxLength) {
        String message = path.toString();
        int length = message.length();
        if (length > maxLength) {
            return "(...)" + message.substring(length - maxLength);
        } else {
            return message;
        }
    }

    public static Path copy(Path from, Path to) throws IOException {
        long kb = Files.size(from) / 1024;
        System.out.printf("Copying %dkB ThinWAR to %s \n", kb, shortenForDisplay(to, 40));
        return Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
    }

}

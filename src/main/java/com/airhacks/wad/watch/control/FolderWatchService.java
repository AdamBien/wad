
package com.airhacks.wad.watch.control;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author airhacks.com
 */
public interface FolderWatchService {


    public static void listenForChanges(Path dir, Runnable listener) throws IOException {
        WatchService service = FileSystems.getDefault().newWatchService();
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                dir.register(service, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                return FileVisitResult.CONTINUE;
            }
        });
        while (true) {
            try {
                WatchKey key = service.take();
                key.pollEvents();
                listener.run();
                key.reset();
            } catch (InterruptedException x) {
                return;
            }
        }
    }

    static long listFiles(Path dir) throws IOException {
        return Files.list(dir).
                mapToLong(FolderWatchService::getFileTime).
                sum();
    }

    static long getFileTime(Path p) {
        try {
            return Files.size(p);
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot obtain FileTime", ex);
        }
    }


}

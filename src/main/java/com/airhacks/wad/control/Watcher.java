package com.airhacks.wad.control;

import com.sun.nio.file.SensitivityWatchEventModifier;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

public class Watcher {
    private static final TemporalAmount MIN_RUN_DELAY = Duration.ofSeconds(2);

    private final WatchService watchService;
    private final Runnable runnable;
    private LocalDateTime lastChange = LocalDateTime.MIN;

    public Watcher(Path dir, Runnable runnable) throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
        registerAll(dir, watchService);
        this.runnable = runnable;
    }

    private void registerAll(final Path start, WatchService watchService) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dir.register(watchService, new WatchEvent.Kind[] {StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE}, SensitivityWatchEventModifier.HIGH);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public void runBlocking() {
        try{
            WatchKey key;
            while ((key = watchService.take()) != null) {
                if(LocalDateTime.now().isAfter(lastChange.plus(MIN_RUN_DELAY))){
                    runnable.run();
                }else{
                    //Not running deploy because last deploy was less than "MIN_RUN_DELAY" seconds ago.
                }
                key.pollEvents().clear(); //remove all pending events to prevent second deploy
                lastChange = LocalDateTime.now();
                key.reset();
            }
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}

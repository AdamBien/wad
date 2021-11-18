package com.airhacks.wad.control;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class FolderWatchServiceTest {

    private final boolean EXECUTED = true;
    private Path testFolderPath;
    private List<Path> tempFiles;
    private Watcher watcher;
    private boolean listener;

    @Before
    public void init() throws IOException {
        this.testFolderPath = Paths.get("target");

        this.tempFiles = Stream.generate(this::createFile).
                limit(15).
                collect(Collectors.toList());
        this.watcher = new Watcher(testFolderPath, () -> listener = EXECUTED);
        listener=false;
    }

    Path createFile() {
        try {
            Path p = Files.createTempFile(this.testFolderPath, "wad", "java");
            String fileContent = "duke" + System.nanoTime();
            Files.write(p, fileContent.getBytes());
            return p;
        } catch (IOException ex) {
            throw new IllegalStateException("temp file creation", ex);
        }
    }

    void deleteFile(int index) throws IOException {
        Path file = this.tempFiles.get(index);
        Files.delete(file);
        this.tempFiles.remove(file);
    }

    void changeFile(int index, String content) throws IOException {
        Files.write(this.tempFiles.get(index), content.getBytes());
    }

    void addFile() {
        this.tempFiles.add(createFile());
    }

    @Test
    public void listenerIsNotTriggeredWithoutModification() throws Exception {
        new Thread(()-> watcher.runBlocking()).start();
        Thread.sleep(100);
        Thread.sleep(100);
        assertThat(listener, not(EXECUTED));
    }

    @Test
    public void addingFileTriggersListener() throws Exception {
        new Thread(()-> watcher.runBlocking()).start();
        Thread.sleep(100);
        addFile();
        Thread.sleep(100);
        assertThat(listener, is(EXECUTED));
    }

    @Test
    public void changingFileTriggersListener() throws Exception {
        new Thread(()-> watcher.runBlocking()).start();
        Thread.sleep(100);
        changeFile(1, "Java EE rocks");
        Thread.sleep(100);
        assertThat(listener, is(EXECUTED));
    }

    @Test
    public void deletingFileTriggersListener() throws Exception {
        new Thread(()-> watcher.runBlocking()).start();
        Thread.sleep(100);
        deleteFile(2);
        Thread.sleep(100);
        assertThat(listener, is(EXECUTED));
    }

    //see Watcher.MIN_RUN_DELAY
    @Test
    public void changingMultipleFilesTriggersOnce() throws Exception {
        new Thread(()-> watcher.runBlocking()).start();

        listener = false;
        Thread.sleep(100);
        addFile();
        Thread.sleep(100);
        assertThat(listener, is(EXECUTED));

        listener = false;
        Thread.sleep(100);
        addFile();
        Thread.sleep(100);
        assertThat(listener, not(EXECUTED));
    }
}

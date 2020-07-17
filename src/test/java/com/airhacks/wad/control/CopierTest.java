package com.airhacks.wad.control;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.junit.Test;

public class CopierTest {

    @Test
    public void testFindWarInPath() {

        Path path = Paths.get("src/test/resources/result");
        Optional<Path> warInPath = new Copier(null, null).findWarInPath(path);
        assertTrue(warInPath.isPresent());
        assertTrue(warInPath.get().getFileName().endsWith("myApp_1.0.0-SNAPSHOT.war"));
    }
}
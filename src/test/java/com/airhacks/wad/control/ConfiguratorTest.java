/*
 */
package com.airhacks.wad.control;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class ConfiguratorTest {

    @Test
    public void readExistingConfiguration() {
        Path wadrc = Paths.get("src/test/resources", "wadrc");
        Set<Path> lines = Configurator.getConfigurationFromDirectory(wadrc);
        List<String> folderLines = lines.
                stream().
                map(l -> l.toString()).
                collect(Collectors.toList());
        assertTrue(folderLines.contains("firstfolder"));
        assertTrue(folderLines.contains("secondfolder"));
        assertTrue(folderLines.contains("lastfolder"));

    }
    @Test
    public void readNonExistingConfiguration() {
        Path wadrc = Paths.get("src/test/resources", "doesNotExist");
        Set<Path> lines = Configurator.getConfigurationFromDirectory(wadrc);
        assertTrue(lines.isEmpty());
    }

}

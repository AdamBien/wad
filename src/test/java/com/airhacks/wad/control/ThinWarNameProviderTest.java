package com.airhacks.wad.control;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ThinWarNameProviderTest {

    @Test
    public void finalNameInPomIsNotSet() throws IOException {
        Path pomWithoutFinalNamePath = Paths.get("src/test/resources", "pom-without-final-name.xml");
        Path currentPath = Paths.get("").toAbsolutePath();
        Path currentDirectory = currentPath.getFileName();
        String thinWARName = currentDirectory + ".war";

        String thinWarName = ThinWarNameProvider.getCurrentThinWarName(pomWithoutFinalNamePath);
        assertThat(thinWarName, equalTo(thinWARName));
    }

    @Test
    public void finalNameInPomIsSet() throws IOException {
        Path pomWithFinalNamePath = Paths.get("src/test/resources", "pom-with-final-name.xml");

        String thinWarName = ThinWarNameProvider.getCurrentThinWarName(pomWithFinalNamePath);
        assertThat(thinWarName, equalTo("test.war"));
    }
}

package wad;

import java.nio.file.Path;
import java.nio.file.Paths;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class AppTest {

    @Test
    public void currentFolderFolderName() {
        String expected = "wad";
        Path currentPath = Paths.get("").toAbsolutePath().getFileName();
        assertThat(currentPath.toString(), is(expected));
    }

    @Test
    public void addTrailingSlash() {
        String expected = "/";

        String actual = App.addTrailingSlash("");
        assertThat(actual, is(expected));

        actual = App.addTrailingSlash("/");
        assertThat(actual, is(expected));
    }

    @Test
    public void validateDeploymentDirectory() {
        Path path = Paths.get("src/main/java");
        boolean validationResult = App.validateDeploymentDirectory(path);
        assertTrue(validationResult);

        validationResult = App.validateDeploymentDirectory(Paths.get("doesNotExist"));
        assertFalse(validationResult);
    }


}

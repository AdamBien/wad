package wad;

import java.nio.file.Path;
import java.nio.file.Paths;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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

        String actual = App.addTrailingSlash("").toString();
        assertThat(actual, is(expected));

        actual = App.addTrailingSlash("/").toString();
        assertThat(actual, is(expected));
    }


}

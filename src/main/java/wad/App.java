
package wad;

import com.airhacks.wad.watch.boundary.WADFlow;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author airhacks.com
 */
public class App {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Invoke with java -jar wad.jar [DEPLOYMENT_DIR]");
            System.exit(-1);
        }

        Path deploymentDir = Paths.get(args[0]);
        Path sourceCodeDir = Paths.get("./src/main/java");

        Path currentPath = Paths.get("").toAbsolutePath();
        Path currentDirectory = currentPath.getFileName();

        Path thinWAR = Paths.get("target", currentDirectory + ".war");

        WADFlow.wad(sourceCodeDir, thinWAR, deploymentDir);
    }
}

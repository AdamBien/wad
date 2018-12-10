
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
        Path currentPath = Paths.get("").toAbsolutePath();
        Path currentDirectory = currentPath.getFileName();
        String thinWARName = currentDirectory + ".war";

        Path thinWARPath = Paths.get("target", thinWARName);

        Path deploymentDir = Paths.get(args[0], thinWARName);
        Path sourceCodeDir = Paths.get("./src/main/java");
        System.out.printf("WAD is watching %s, deploying %s to %s \n", sourceCodeDir, thinWARPath, deploymentDir);
        WADFlow wadFlow = new WADFlow(sourceCodeDir, thinWARPath, deploymentDir);
    }
}


package wad;

import com.airhacks.wad.watch.boundary.WADFlow;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author airhacks.com
 */
public class App {
    
    static void validateDeploymentDirectory(Path path) {
        if (!Files.exists(path)) {
            System.err.printf("Directory %s does not exist", path);
            System.exit(-1);
        }
        if (!Files.isDirectory(path)) {
            System.err.printf("%s is not a directory", path);
            System.exit(-1);
        }
    }

    static String addTrailingSlash(String path) {
        if (!path.endsWith(File.pathSeparator)) {
            return path + File.pathSeparator;
        }
        return path;
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Invoke with java -jar wad.jar [DEPLOYMENT_DIR]");
            System.exit(-1);
        }
        Path currentPath = Paths.get("").toAbsolutePath();
        Path currentDirectory = currentPath.getFileName();
        String thinWARName = currentDirectory + ".war";

        Path thinWARPath = Paths.get("target", thinWARName);

        Path deploymentDir = Paths.get(addTrailingSlash(args[0]), thinWARName);
        validateDeploymentDirectory(deploymentDir);
        Path sourceCodeDir = Paths.get("./src/main/java");
        System.out.printf("WAD is watching %s, deploying %s to %s \n", sourceCodeDir, thinWARPath, deploymentDir);
        WADFlow wadFlow = new WADFlow(sourceCodeDir, thinWARPath, deploymentDir);
    }
}

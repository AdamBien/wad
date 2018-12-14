
package wad;

import com.airhacks.wad.watch.boundary.WADFlow;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 * @author airhacks.com
 */
public class App {
    
    static boolean validateDeploymentDirectory(Path path) {
        if (!Files.exists(path)) {
            System.err.printf("Directory %s does not exist", path);
            return false;
        }
        if (!Files.isDirectory(path)) {
            System.err.printf("%s is not a directory", path);
            return false;
        }

        return true;
    }

    static Path addTrailingSlash(String path) {
        if (!path.endsWith(File.separator)) {
            return Paths.get(path, File.separator);
        }
        return Paths.get(path);
    }

    static void printWelcomeMessage() throws IOException {
        try (InputStream resourceAsStream = App.class.
                getClassLoader().
                getResourceAsStream("META-INF/maven/com.airhacks/wad/pom.properties")) {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            String wad = properties.getProperty("artifactId");
            String version = properties.getProperty("version");
            System.out.println(wad + " " + version);
        }
    }


    public static void main(String[] args) throws IOException {
        printWelcomeMessage();
        if (args.length != 1) {
            System.out.println("Invoke with java -jar wad.jar [DEPLOYMENT_DIR]");
            System.exit(-1);
        }
        Path currentPath = Paths.get("").toAbsolutePath();
        Path currentDirectory = currentPath.getFileName();
        String thinWARName = currentDirectory + ".war";

        Path thinWARPath = Paths.get("target", thinWARName);
        Path deploymentDirArgument = addTrailingSlash(args[0]);
        boolean validationWasSuccessful = validateDeploymentDirectory(deploymentDirArgument);
        if (!validationWasSuccessful) {
            System.exit(-1);
        }
        Path deploymentDir = deploymentDirArgument.resolve(thinWARName);
        Path sourceCodeDir = Paths.get("./src/main/");
        System.out.printf("WAD is watching %s, deploying %s to %s \n", sourceCodeDir, thinWARPath, deploymentDir);
        WADFlow wadFlow = new WADFlow(sourceCodeDir, thinWARPath, deploymentDir);
    }
}

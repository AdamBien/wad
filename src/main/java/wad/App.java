
package wad;

import com.airhacks.wad.watch.boundary.WADFlow;
import static com.airhacks.wad.watch.control.PreBuildChecks.pomExists;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 *
 * @author airhacks.com
 */
public class App {
    
    static boolean validateDeploymentDirectories(List<Path> path) {
        long invalidDirectories = path.stream().map(App::validateDeploymentDirectory).filter(valid -> valid == false).count();
        return (invalidDirectories == 0);
    }

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

    static List<Path> convert(String[] args) {
        return Arrays.stream(args).map(App::addTrailingSlash).collect(Collectors.toList());
    }

    static List<Path> addWarName(List<Path> deploymentDirectories, String warName) {
        return deploymentDirectories.
                stream().
                map(path -> path.resolve(warName)).
                collect(Collectors.toList());
    }


    public static void main(String[] args) throws IOException {
        printWelcomeMessage();
        if (args.length < 1) {
            System.out.println("Invoke with java -jar wad.jar [DEPLOYMENT_DIR1,DEPLOYMENT_DIR1]");
            System.exit(-1);
        }
        pomExists();
        Path currentPath = Paths.get("").toAbsolutePath();
        Path currentDirectory = currentPath.getFileName();
        String thinWARName = currentDirectory + ".war";

        Path thinWARPath = Paths.get("target", thinWARName);

        List<Path> deploymentDirs = convert(args);
        boolean validationWasSuccessful = validateDeploymentDirectories(deploymentDirs);
        if (!validationWasSuccessful) {
            System.exit(-1);
        }

        List<Path> deploymentTargets = addWarName(deploymentDirs, thinWARName);
        Path sourceCodeDir = Paths.get("./src/main/");
        System.out.printf("WAD is watching %s, deploying %s to %s \n", sourceCodeDir, thinWARPath, deploymentTargets);
        WADFlow wadFlow = new WADFlow(sourceCodeDir, thinWARPath, deploymentTargets);
    }

}

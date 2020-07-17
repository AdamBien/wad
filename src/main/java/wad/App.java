
package wad;

import static com.airhacks.wad.control.PreBuildChecks.pomExists;
import static com.airhacks.wad.control.PreBuildChecks.validateDeploymentDirectories;

import com.airhacks.wad.boundary.WADFlow;
import com.airhacks.wad.control.Configurator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author airhacks.com
 */
public class App {
    
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



    public static void main(String[] args) throws IOException {
        printWelcomeMessage();
        if (args.length < 1 && !Configurator.userConfigurationExists()) {
            System.out.println("Invoke with java -jar wad.jar [DEPLOYMENT_DIR1,DEPLOYMENT_DIR1] or create ~/.wadrc");
            System.exit(-1);
        }
        pomExists();

        Path thinWARPath = Paths.get("target");

        Set<Path> deploymentDirs = Configurator.getConfiguredFolders(convert(args));
        validateDeploymentDirectories(deploymentDirs);

        Path sourceCodeDir = Paths.get("./src/main/");
        System.out.printf("WAD is watching %s, deploying from dir '%s' to %s \n", sourceCodeDir, thinWARPath, deploymentDirs);
        WADFlow wadFlow = new WADFlow(sourceCodeDir, thinWARPath, deploymentDirs);
    }

}

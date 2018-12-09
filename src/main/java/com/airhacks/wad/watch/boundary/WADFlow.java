
package com.airhacks.wad.watch.boundary;

import com.airhacks.wad.watch.control.Builder;
import com.airhacks.wad.watch.control.Copier;
import com.airhacks.wad.watch.control.FolderWatchService;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.maven.shared.invoker.InvocationResult;

/**
 *
 * @author airhacks.com
 */
public interface WADFlow {

    public static void wad(Path dir, Path war, Path deploymentDir) throws IOException {
        Runnable changeListener = () -> buildAndDeploy(war, deploymentDir);
        FolderWatchService.listenForChanges(dir, changeListener);
    }

    static void buildAndDeploy(Path war, Path deploymentDir) {
        try {
            InvocationResult result = Builder.build();
            if (result.getExitCode() == 0) {
                Copier.copy(war, deploymentDir);
            } else {
                System.err.println("maven execution problem: " + result.getExecutionException().getMessage());
            }
        } catch (Exception ex) {
            System.err.println("maven " + ex.getMessage());
        }
    }

}

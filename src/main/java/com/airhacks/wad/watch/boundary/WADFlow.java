
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
public class WADFlow {

    private final Builder builder;

    public WADFlow(Path dir, Path war, Path deploymentDir) throws IOException {
        this.builder = new Builder();
        Runnable changeListener = () -> buildAndDeploy(war, deploymentDir);
        FolderWatchService.listenForChanges(dir, changeListener);
        System.out.println("WAD watches " + dir);
    }

    void buildAndDeploy(Path war, Path deploymentDir) {
        try {
            InvocationResult result = this.builder.build();
            if (result.getExitCode() == 0) {
                Copier.copy(war, deploymentDir);
            } else {
                System.err.println("maven execution problem: " + result.getExecutionException().getMessage());
            }
        } catch (Exception ex) {
            System.err.println(ex.getClass().getName() + " " + ex.getMessage());
        }
    }

}

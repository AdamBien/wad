
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
    }

    void buildAndDeploy(Path war, Path deploymentDir) {
        long start = System.currentTimeMillis();
        try {
            InvocationResult result = this.builder.build();
            if (result.getExitCode() == 0) {
                System.out.println("...built in " + (System.currentTimeMillis() - start) + " ms");
                start = System.currentTimeMillis();
                Copier.copy(war, deploymentDir);
                System.out.println("...copied in " + (System.currentTimeMillis() - start) + " ms");
            } else {
                System.err.println("maven execution problem: " + result.getExecutionException().getMessage());
            }
        } catch (Exception ex) {
            System.err.println(ex.getClass().getName() + " " + ex.getMessage());
        }
    }

}

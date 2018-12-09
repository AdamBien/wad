
package com.airhacks.wad.watch.control;

import java.io.File;
import java.util.Collections;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

/**
 *
 * @author airhacks.com
 */
public interface Builder {

    public static InvocationResult build() throws MavenInvocationException {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File("./pom.xml"));
        request.setGoals(Collections.singletonList("clean install"));
        Invoker invoker = new DefaultInvoker();
        return invoker.execute(request);
    }

}

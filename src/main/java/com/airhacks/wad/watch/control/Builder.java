
package com.airhacks.wad.watch.control;

import java.io.File;
import java.util.Collections;
import java.util.List;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.MavenInvocationException;

/**
 *
 * @author airhacks.com
 */
public class Builder {

    private final DefaultInvoker invoker;
    private final List<String> goals;

    public Builder() {
        this.invoker = new DefaultInvoker();
        this.goals = Collections.singletonList("clean install");
    }


    public InvocationResult build() throws MavenInvocationException {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File("./pom.xml"));
        request.setGoals(this.goals);
        return this.invoker.execute(request);
    }

}

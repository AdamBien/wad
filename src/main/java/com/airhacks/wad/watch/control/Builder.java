
package com.airhacks.wad.watch.control;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.MavenInvocationException;

/**
 *
 * @author airhacks.com
 */
public class Builder {

    private final DefaultInvoker invoker;
    private final DefaultInvocationRequest request;

    public Builder() {
        this.invoker = new DefaultInvoker();
        List<String> goals = Collections.singletonList("package");
        Properties properties = new Properties();
        properties.put("maven.test.skip", String.valueOf(true));
        this.request = new DefaultInvocationRequest();
        this.request.setPomFile(new File("./pom.xml"));
        this.request.setGoals(goals);
        this.request.setBatchMode(true);
        this.request.setProperties(properties);
    }

    public InvocationResult build() throws MavenInvocationException {
        return this.invoker.execute(request);
    }

}

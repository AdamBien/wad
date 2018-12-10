
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
    private final List<String> goals;
    private final Properties properties;
    private final DefaultInvocationRequest request;

    public Builder() {
        this.invoker = new DefaultInvoker();
        this.goals = Collections.singletonList("package");
        this.properties = new Properties();
        this.properties.put("skipTests", true);
        this.request = new DefaultInvocationRequest();
        this.request.setPomFile(new File("./pom.xml"));
        this.request.setGoals(this.goals);
        this.request.setBatchMode(false);
        this.request.setProperties(this.properties);
    }

    public InvocationResult build() throws MavenInvocationException {
        return this.invoker.execute(request);
    }

}

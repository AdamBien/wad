
package com.airhacks.wad.control;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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
public class MavenBuilder implements Builder {

    private final DefaultInvoker invoker;
    private final DefaultInvocationRequest request;

    public MavenBuilder() {
	this.invoker = new DefaultInvoker();
	this.invoker.setLogger(new SilentLogger());
	this.invoker.setOutputHandler((line) -> {
	});
	List<String> goals = Arrays.asList("clean", "package");
	Properties properties = new Properties();
	properties.put("maven.test.skip", String.valueOf(true));
	this.request = new DefaultInvocationRequest();
	this.request.setPomFile(new File("./pom.xml"));
	this.request.setGoals(goals);
	this.request.setBatchMode(true);
	this.request.setProperties(properties);
	this.request.setThreads(System.getProperty("threads", "1"));
	this.request.setShowErrors(true);
    }

    @Override
    public InvocationResult build() throws MavenInvocationException {
	return this.invoker.execute(request);
    }

    @Override
    public Path getThinWarPath() {
	Path currentPath = Paths.get("").toAbsolutePath();
	Path currentDirectory = currentPath.getFileName();
	String thinWARName = currentDirectory + ".war";

	Path thinWARPath = Paths.get("target", thinWARName);
	return thinWARPath;
    }
}

package com.airhacks.wad.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.apache.maven.shared.utils.cli.CommandLineException;

public class GradleBuilder implements Builder {

    @Override
    public InvocationResult build() throws MavenInvocationException {
	try {
	    int exitCode = new ProcessBuilder("./gradlew", "war").start().waitFor();
	    return new InvocationResult() {

		@Override
		public int getExitCode() {
		    return exitCode;
		}

		@Override
		public CommandLineException getExecutionException() {
		    return null;
		}
	    };
	} catch (IOException | InterruptedException e) {
	    throw new MavenInvocationException("Gradle build failed", e);
	}
    }

    @Override
    public Path getThinWarPath() {
	try {
	    build();
	} catch (MavenInvocationException e1) {
	    // can be ignored
	}
	Path thinWARPath = Paths.get("build").resolve("libs");
	try {
	    return Files.find(thinWARPath, 1, (p, a) -> p.toString().endsWith(".war")).findFirst().orElse(thinWARPath);
	} catch (IOException e) {
	    return thinWARPath;
	}
    }
}

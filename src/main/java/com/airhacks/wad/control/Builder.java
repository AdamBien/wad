package com.airhacks.wad.control;

import java.nio.file.Path;

import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.MavenInvocationException;

public interface Builder {

    InvocationResult build() throws MavenInvocationException;

    Path getThinWarPath();

}
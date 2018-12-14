
package com.airhacks.wad.watch.control;

import org.apache.maven.shared.invoker.InvokerLogger;

/**
 *
 * @author airhacks.com
 */
public class SilentLogger implements InvokerLogger {

    private int threshold;

    @Override
    public void debug(String message) {
    }

    @Override
    public void debug(String message, Throwable throwable) {
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void info(String message) {
    }

    @Override
    public void info(String message, Throwable throwable) {
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public void warn(String message) {
    }

    @Override
    public void warn(String message, Throwable throwable) {
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public void error(String message) {
        System.err.println(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        System.err.println(message + " " + throwable.getMessage());
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void fatalError(String message) {
    }

    @Override
    public void fatalError(String message, Throwable throwable) {
    }

    @Override
    public boolean isFatalErrorEnabled() {
        return true;
    }

    @Override
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public int getThreshold() {
        return this.threshold;
    }

}

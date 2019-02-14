
package com.airhacks.wad.control;

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
        this.log(message);
    }

    @Override
    public void info(String message, Throwable throwable) {
        this.log(message, throwable);
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public void warn(String message) {
        this.log(message);
    }

    @Override
    public void warn(String message, Throwable throwable) {
        this.log(message, throwable);
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void error(String message) {
        this.log(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        this.log(message, throwable);
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void fatalError(String message) {
        log(message);
    }

    @Override
    public void fatalError(String message, Throwable throwable) {
        log(message, throwable);
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

    void log(String message) {
        System.err.println(message);
    }

    void log(String message, Throwable t) {
        log(message + " " + t);
    }

}

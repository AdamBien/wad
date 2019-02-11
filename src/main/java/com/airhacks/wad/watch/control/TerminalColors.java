/*
 */
package com.airhacks.wad.watch.control;

/**
 *
 * @author airhacks.com
 */
public enum TerminalColors {

    FILE("\u001B[34m"), TIME("\033[1;90m"), RESET("\u001B[0m");

    private final String value;


    private TerminalColors(String value) {
        this.value =  value;
    }

    public String value() {
        return this.value;
    }


}

/*
 */
package com.airhacks.wad.watch.boundary;

import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class WADFlowTest {

    @Test
    public void currentFormattedTime() {
        String currentFormattedTime = WADFlow.currentFormattedTime();
        System.out.println("currentFormattedTime = " + currentFormattedTime);
    }

}

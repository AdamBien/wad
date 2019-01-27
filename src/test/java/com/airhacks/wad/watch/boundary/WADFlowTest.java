/*
 */
package com.airhacks.wad.watch.boundary;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class WADFlowTest {

    @Test
    public void currentFormattedTime() {
        String formattedTime = WADFlow.currentFormattedTime();
        assertThat(formattedTime.length(), is(8));
        assertThat(formattedTime.indexOf(":"), is(2));
        assertThat(formattedTime.lastIndexOf(":"), is(5));
        System.out.println("formattedTime = " + formattedTime);
    }

}

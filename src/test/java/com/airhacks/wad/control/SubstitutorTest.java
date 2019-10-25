/*
 */
package com.airhacks.wad.control;

import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Assume;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class SubstitutorTest {

    @Test
    public void extractExistingKey() {
        String expected = "DUKE";
        String stringWithPlaceholder = "hello ${" + expected + "}";
        Optional<String> actual = Substitutor.extractKey(stringWithPlaceholder);
        assertNotNull(actual);
        assertThat(actual.get(), is(expected));
    }

    @Test
    public void substituteExistingVariable() {
        String envEntry = "JAVA_HOME";
        String javaHome = System.getenv(envEntry);
        Assume.assumeNotNull(javaHome);
        String prefix = "java=";
        String pathWithPlaceholder = prefix + "${" + envEntry + "}";
        String expected = prefix + javaHome;

        String actual = Substitutor.substitute(pathWithPlaceholder);
        assertThat(actual, is(expected));
    }

    @Test
    public void substituteNotExistingVariable() {
        String envEntry = "SHOULD_NOT_EXIST";
        String prefix = "java=";
        String pathWithPlaceholder = prefix + "${" + envEntry + "}";
        String actual = Substitutor.substitute(pathWithPlaceholder);
        assertThat(actual, is(pathWithPlaceholder));
    }


}

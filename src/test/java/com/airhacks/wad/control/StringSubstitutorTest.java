/*
 */
package com.airhacks.wad.control;

import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class StringSubstitutorTest {

    @Test
    public void extractExistingKey() {
        String expected = "DUKE";
        String stringWithPlaceholder = "hello ${" + expected + "}";
        Optional<String> actual = Substitutor.extractKey(stringWithPlaceholder);
        assertNotNull(actual);
        assertThat(actual.get(), is(expected));
    }

}

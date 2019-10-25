
package com.airhacks.wad.control;

import java.util.Optional;

/**
 *
 * @author airhacks.com
 */
public class Substitutor {

    static String substitute(String placeHolder) {
        Optional<String> key = extractKey(placeHolder);
        return key.map(Substitutor::resolveWithEnvironment).orElse(placeHolder);
    }

    static Optional<String> extractKey(String stringWithPlaceholder) {
        int firstIndex = stringWithPlaceholder.indexOf("${");
        if (firstIndex == -1) {
            return Optional.empty();
        }
        int beginning = firstIndex + 2;
        int lastIndex = stringWithPlaceholder.indexOf("}");
        return Optional.of(stringWithPlaceholder.substring(beginning, lastIndex));
    }

    static String resolveWithEnvironment(String key) {
        return System.getenv(key);
    }


}

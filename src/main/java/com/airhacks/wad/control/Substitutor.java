
package com.airhacks.wad.control;

import java.util.Optional;

/**
 *
 * @author airhacks.com
 */
public class Substitutor {

    public static String substitute(String placeHolder) {
        Optional<String> key = extractKey(placeHolder);
        Optional<String> variableValue = key.map(Substitutor::resolveWithEnvironment);
        return variableValue.map(v -> substituteVariableWithValue(placeHolder, v)).orElse(placeHolder);
    }

    static String substituteVariableWithValue(String withPlaceHolder, String value) {
        int firstIndex = withPlaceHolder.indexOf("${");
        int lastIndex = withPlaceHolder.indexOf("}");
        String beginning = withPlaceHolder.substring(0, firstIndex);
        String end = withPlaceHolder.substring(lastIndex + 1);
        return beginning + value + end;

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
        String value = System.getenv(key);
        if (value == null) {
            System.out.printf("Environment entry %s is not defined\n", key);
        }
        return value;
    }


}

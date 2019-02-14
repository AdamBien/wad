/*
 */
package com.airhacks.wad.control;

import com.airhacks.wad.control.PreBuildChecks;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class PreBuildChecksTest {

    @Test
    public void validateDeploymentDirectory() {
        Path path = Paths.get("src/main/java");
        boolean validationResult = PreBuildChecks.validateDeploymentDirectory(path);
        assertTrue(validationResult);

        validationResult = PreBuildChecks.validateDeploymentDirectory(Paths.get("doesNotExist"));
        assertFalse(validationResult);
    }

}

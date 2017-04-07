package ru.alfabank.sonar.rpg.checks;

import com.sonarsource.rpg.api.test.RpgCheckVerifier;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class OperationErrorExtenderCheckTest {

    @Test
    public void test() throws Exception {
        RpgCheckVerifier.verify(new OperationErrorExtenderCheck(), new File(getClass().getResource("/OperationErrorExtenderCheck.rpg").getFile()), StandardCharsets.UTF_8);
    }

}

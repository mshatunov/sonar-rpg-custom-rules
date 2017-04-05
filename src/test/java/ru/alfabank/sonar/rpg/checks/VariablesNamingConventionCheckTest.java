package ru.alfabank.sonar.rpg.checks;

import com.sonarsource.rpg.api.test.RpgCheckVerifier;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class VariablesNamingConventionCheckTest {

    @Test
    public void test() throws Exception {
        RpgCheckVerifier.verify(new VariablesNamingConventionCheck(), new File(getClass().getResource("/VariablesNamingConventionCheck.rpg").getFile()), StandardCharsets.UTF_8);
    }

}

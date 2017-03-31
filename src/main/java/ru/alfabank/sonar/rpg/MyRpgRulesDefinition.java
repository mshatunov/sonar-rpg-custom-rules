package ru.alfabank.sonar.rpg;

import com.sonarsource.rpg.api.CustomRulesDefinition;
import ru.alfabank.sonar.rpg.checks.VariablesNamingConventionCheck;

import java.util.Arrays;
import java.util.List;

public class MyRpgRulesDefinition implements CustomRulesDefinition {

    public static final String REPOSITORY_KEY = "AlfaCustomRpgRules";

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(REPOSITORY_KEY, "rpg");
        repository.setName("Alfabank custom rpg rules repository");

        NewRule rule = repository.createRule(VariablesNamingConventionCheck.RULE_KEY)
                .setName("Variables naming convention check")
                .setHtmlDescription("Checks variables names for naming convention")
                .addTags("convention");
        rule.setDebtRemediationFunction(rule.debtRemediationFunctions().constantPerIssue("3min"));
        repository.done();
    }

    @Override
    public String repositoryKey() {
        return REPOSITORY_KEY;
    }

    @Override
    public List<Class> checkClasses() {
        return Arrays.asList(VariablesNamingConventionCheck.class);
    }

}

package ru.alfabank.sonar.rpg.checks;

import com.sonarsource.rpg.api.checks.VisitorBasedCheck;
import com.sonarsource.rpg.api.tree.DefinitionTree;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Rule;
import ru.alfabank.sonar.rpg.MyRpgRulesDefinition;

import java.util.regex.Pattern;

@Rule(key = VariablesNamingConventionCheck.RULE_KEY)
public class VariablesNamingConventionCheck extends VisitorBasedCheck {

    public static final String RULE_KEY = "VariablesNamingConventionVariables";

    private static final String REGEXP = "^[a-z][a-zA-Z0-9]*$";
    private static final Pattern PATTERN = Pattern.compile(REGEXP);

    @Override
    public void visitDefinition(DefinitionTree definition) {
        if (!PATTERN.matcher(definition.name()).matches()) {
            addIssue(definition.startLine());
        }
        super.visitDefinition(definition);
    }

    private void addIssue(int lineWithError) {
        final String ERROR_MESSAGE = "The name of the variable should match regexp " + REGEXP;
        context().addIssue(RuleKey.of(MyRpgRulesDefinition.REPOSITORY_KEY, RULE_KEY), lineWithError, ERROR_MESSAGE, null);
    }

}

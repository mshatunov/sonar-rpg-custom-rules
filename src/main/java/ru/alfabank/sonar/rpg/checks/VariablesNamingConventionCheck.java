package ru.alfabank.sonar.rpg.checks;

import ru.alfabank.sonar.rpg.MyRpgRulesDefinition;
import com.sonarsource.rpg.api.checks.VisitorBasedCheck;
import com.sonarsource.rpg.api.tree.DefinitionTree;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;

import java.util.regex.Pattern;

@Rule(key = VariablesNamingConventionCheck.RULE_KEY)
public class VariablesNamingConventionCheck extends VisitorBasedCheck {


    public static final String RULE_KEY = "VariablesNamingConventionVariables";
    private static final String REGEXP = "^[a-z][a-zA-Z0-9]*$";
    @RuleProperty(
            key = "format",
            defaultValue = REGEXP
    )

    private Pattern regExp = Pattern.compile(REGEXP);


    @Override
    public void visitDefinition(DefinitionTree definition) {
        if (!this.regExp.matcher(definition.name()).matches()) {
            context().addIssue(ruleKey(), definition.startLine(), "The name of the variable should match regexp " + REGEXP, null);
        }

        // super.visitXX(...) should be called to visit child trees
        super.visitDefinition(definition);
    }

    private RuleKey ruleKey() {
        return RuleKey.of(MyRpgRulesDefinition.REPOSITORY_KEY, RULE_KEY);
    }

}

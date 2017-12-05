package ru.alfabank.sonar.rpg.checks;

import com.sonarsource.rpg.api.checks.VisitorBasedCheck;
import com.sonarsource.rpg.api.tree.OperationTree;
import com.sonarsource.rpg.api.tree.Tree;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Rule;
import ru.alfabank.sonar.rpg.MyRpgRulesDefinition;

import java.util.stream.StreamSupport;

@Rule(key = OperationErrorExtenderCheck.RULE_KEY)
public class OperationErrorExtenderCheck extends VisitorBasedCheck {

    public static final String RULE_KEY = "OperationErrorExtenderCheck";

    private static final char ERROR_EXTENDER = 'E';

    private int operationWithErrorLine;
    private int errorProcessingLine;

    @Override
    public void visitOperation(OperationTree operationTree) {

        errorProcessingLine = operationTree.expressions()
                .stream()
                .flatMap(expressionTree -> StreamSupport.stream(expressionTree.tokens().spliterator(), false))
                .filter(syntaxToken -> "%error".equalsIgnoreCase(syntaxToken.value()))
                .mapToInt(Tree::startLine)
                .findFirst()
                .orElse(errorProcessingLine);

        if (operationTree.hasExtender(ERROR_EXTENDER) || "return".equalsIgnoreCase(operationTree.operationCode().toString())) {
            processErrorExtenderLine(operationTree.startLine());
        }

        super.visitOperation(operationTree);
    }

    private void processErrorExtenderLine(int newOperationLine) {
        if (errorProcessingLine < operationWithErrorLine) {
            addIssue(operationWithErrorLine);
        }
        operationWithErrorLine = newOperationLine;
    }

    private void addIssue(int lineWithError) {
        final String ERROR_MESSAGE = "You should not use error extender without processing it";
        context().addIssue(RuleKey.of(MyRpgRulesDefinition.REPOSITORY_KEY, RULE_KEY), lineWithError, ERROR_MESSAGE, null);
    }

}

package ru.alfabank.sonar.rpg.checks;


import com.sonarsource.rpg.api.checks.VisitorBasedCheck;
import com.sonarsource.rpg.api.tree.ExpressionTree;
import com.sonarsource.rpg.api.tree.OperationTree;
import com.sonarsource.rpg.api.tree.SyntaxToken;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Rule;
import ru.alfabank.sonar.rpg.MyRpgRulesDefinition;


@Rule(key = OperationErrorExtenderCheck.RULE_KEY)
public class OperationErrorExtenderCheck extends VisitorBasedCheck {

    public static final String RULE_KEY = "OperationErrorExtenderCheck";
    private static final char ERROR_EXTENDER = 'E';

    private int lastOperationLine;
    private int lastExpressionLine;

    @Override
    public void visitOperation(OperationTree operationTree) {

        for (ExpressionTree expressionTree : operationTree.expressions()) {
            for (SyntaxToken syntaxToken : expressionTree.tokens()) {
                if (syntaxToken.value().equals("%ERROR")) {
                    lastExpressionLine = syntaxToken.startLine();
                }
            }
        }

        if (operationTree.hasExtender(ERROR_EXTENDER) || operationTree.operationCode().toString().equals("RETURN")) {
            if (isErrorExtenderProcessing(operationTree.startLine())) {
                addIssue(lastOperationLine);
            }
            lastOperationLine = operationTree.startLine();
        }

        super.visitOperation(operationTree);
    }

    private boolean isErrorExtenderProcessing(int newOperationLine) {
        return newOperationLine != 0 && lastOperationLine != 0 && lastExpressionLine < lastOperationLine;
    }

    private void addIssue(int lineWithError) {
        final String ERROR_MESSAGE = "You should not use error extender without processing it";

        context().addIssue(RuleKey.of(MyRpgRulesDefinition.REPOSITORY_KEY, RULE_KEY), lineWithError, ERROR_MESSAGE, null);
    }

}

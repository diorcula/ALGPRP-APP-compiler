package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.selectors.TagSelector;
import nl.han.ica.icss.ast.types.ExpressionType;


public class Checker {

//    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
//         variableTypes = new HANLinkedList<>();
        System.out.println("----------- root" + ast.root);
        checkStyleSheet(ast.root);
    }

    private void checkStyleSheet(Stylesheet stylesheet) {
        System.out.println("checking stylesheet");
        System.out.println(stylesheet.getChildren());

        for (ASTNode child : stylesheet.getChildren()) {
            System.out.println("checking children in stylesheet" + child.toString());
            checkStyleRule(child);
        }
    }

    private void checkStyleRule(ASTNode astNode) {
        for (ASTNode child : astNode.getChildren()) {
            if (child instanceof Selector) {
                System.out.println("checking selectorrrrrrrrr");
                checkSelector(child);
            }
        }
    }

    private void checkSelector(ASTNode astNode) {
        for (ASTNode child : astNode.getChildren()) {
//            if (child instanceof TagSelector) {
                System.out.println("--------checking selector tag");
                checkDeclaration(child);
//            }
        }
    }

    private void checkDeclaration(ASTNode astNode) {
        // setError als ze niet matchen
        System.out.println("---- checking declaration");
//        Declaration declaration = (Declaration) astNode;
//        Expression expression = declaration.expression;
//        System.out.println(declaration.property.name + expression);
//        ExpressionType expressionType = checkExpression(declaration.expression);

    }

//    private ExpressionType checkExpression(Expression expression) {
//        //
//    }
}

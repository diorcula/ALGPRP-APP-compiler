package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.types.ExpressionType;


public class Checker {

//    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
//         variableTypes = new HANLinkedList<>();
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
            // hier check je meteen een Declaration ipv Selector;
            if (child instanceof Declaration) {
                System.out.println("checking declaration - " + child);
                checkDeclaration(child);
            }
        }   
    }

    private void checkDeclaration(ASTNode astNode) {
        System.out.println("---- checking declaration of " + astNode);
        Declaration declaration = (Declaration) astNode;
        ExpressionType expressionType = checkExpression(declaration.expression);

        switch (declaration.property.name) {
            case "color":
            case "background-color":
                if (expressionType != ExpressionType.COLOR) {
                    astNode.setError(declaration.property.name + " waarde moet een #HEX kleur zijn.");
                }
                break;
            case "width":
            case "height":
                if (expressionType != ExpressionType.PIXEL && expressionType != ExpressionType.PERCENTAGE) {
                    astNode.setError(declaration.property.name + " waarde moet een pixel of percentage zijn.");
                }
                break;
            default:
                astNode.setError("De property is onbekend");
                break;
        }

    }

    private ExpressionType checkExpression(Expression expression) {
        if (expression instanceof PercentageLiteral) {
            return ExpressionType.PERCENTAGE;
        } else if (expression instanceof PixelLiteral) {
            return ExpressionType.PIXEL;
        } else if (expression instanceof ColorLiteral) {
            return ExpressionType.COLOR;
        } else if (expression instanceof ScalarLiteral) {
            return ExpressionType.SCALAR;
        } else if (expression instanceof BoolLiteral) {
            return ExpressionType.BOOL;
        }
        return ExpressionType.UNDEFINED;
    }

}

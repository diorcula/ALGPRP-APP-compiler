package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;

public class Checker {
    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        checkStyleSheet(ast.root);
    }

    private void checkStyleSheet(Stylesheet stylesheet) {
        System.out.println("checking stylesheet");

        for (ASTNode child : stylesheet.getChildren()) {
            System.out.println("checking children in stylesheet" + child.toString());
            if (child instanceof VariableAssignment) {
                checkVariableAssignment(child);
            }
            if (child instanceof Stylerule) {
                checkStyleRule(child);
            }
        }
    }

    private void checkStyleRule(ASTNode astNode) {
        for (ASTNode child : astNode.getChildren()) {
            if (child instanceof Declaration) {
                System.out.println("checking declaration - " + child);
                checkDeclaration(child);
            }
        }
    }


    /*
    CHECK 4: Controleer of bij declaraties het type van de value klopt met de property.
    Declaraties zoals `width: #ff0000` of `color: 12px` zijn natuurlijk onzin.
    */
    private void checkDeclaration(ASTNode astNode) {
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

    /*
    check van welk type een expression is.
     */
    private ExpressionType checkExpression(Expression expression) {
//        if (expression instanceof VariableReference) {
//            return checkVariableReference((VariableReference) expression); //---> kijk of hier de var reference al gedefined is.
//        } else if (expression instanceof Operation) {
//            return checkOperation((Operation) expression); //---> check of de operation wel mag.
//        } else {
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
//        }

        return ExpressionType.UNDEFINED;
    }

    /*
    CH01: Controleer of er geen variabelen worden gebruikt die niet gedefinieerd zijn.
    --> Maak gebruik van een scope (een nieuw blok) waar je variabelen kan definen.
    --> voeg variabelen toe aan de scope.
    --> Check of de variabele al bestaat in de scope.
     */
    private void addNewScope() {
        HashMap<String, ExpressionType> newScope = new HashMap<>();
        variableTypes.addFirst(newScope); // Assuming the latest scope is at the beginning.
    }

    private void addVariableToCurrentScope(String variableName, ExpressionType type) {
        if (variableTypes.getSize() == 0) {
            addNewScope(); // Ensure there's at least one scope.
        }
        HashMap<String, ExpressionType> currentScope = variableTypes.getFirst(); // Get the current scope.
        currentScope.put(variableName, type); // Add or update the variable in the current scope.
    }

    private void checkVariableAssignment(ASTNode astNode) {
        VariableAssignment variableAssignment = (VariableAssignment) astNode;
        VariableReference variableReference = variableAssignment.name;
        ExpressionType expressionType = this.checkExpression(variableAssignment.expression);

        if (expressionType == null || expressionType == ExpressionType.UNDEFINED) {
            astNode.setError("Variable assignment is undefined/null.");
            return;
        }
//
//        ExpressionType previousExpressionType = (ExpressionType) this.variableTypes.getFirst().get(variableReference.name);
//        if (checkExpressionTypeAllowed(expressionType, previousExpressionType)) {
//            astNode.setError("Variabele mag niet van type veranderen van " + previousExpressionType + " naar " + expressionType);
//        }
        addVariableToCurrentScope(variableReference.name, expressionType);
    }


    //-------------------

//    private ExpressionType checkOperation(Operation operation) {
//        ExpressionType left;
//        ExpressionType right;
//
//        if (operation.lhs instanceof Operation) {
//            left = this.checkOperation((Operation) operation.lhs);
//        } else {
//            left = checkExpression(operation.lhs);
//        }
//
//        if (operation.rhs instanceof Operation) {
//            right = this.checkOperation((Operation) operation.rhs);
//        } else {
//            right = checkExpression(operation.rhs);
//        }
//
//        if (left == ExpressionType.COLOR || right == ExpressionType.COLOR) {
//            operation.setError("Colors are not allowed in operations.");
//            return ExpressionType.UNDEFINED;
//        }
//
//        if (operation instanceof MultiplyOperation) {
//            if (left != ExpressionType.SCALAR && right != ExpressionType.SCALAR) {
//                operation.setError("Multiply is only allowed with at least one scalar literal.");
//                return ExpressionType.UNDEFINED;
//            }
//            return right != ExpressionType.SCALAR ? right : left;
//        } else if ((operation instanceof SubtractOperation || operation instanceof AddOperation) && left != right) {
//            operation.setError("You can only do add and subtract operations with the same literal.");
//            return ExpressionType.UNDEFINED;
//        }
//        return left;
//    }

}

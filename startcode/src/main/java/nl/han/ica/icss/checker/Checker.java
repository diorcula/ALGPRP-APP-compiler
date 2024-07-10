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
        for (ASTNode child : stylesheet.getChildren()) {
            if (child instanceof VariableAssignment) {
                checkVariableAssignment(child);
            }
            if (child instanceof Stylerule) {
                checkStyleRule(child);
            }
        }
    }

    private void checkStyleRule(ASTNode astNode) {
        Stylerule stylerule = (Stylerule) astNode;
        addNewScope();//---> waarom?
        checkRuleBody(stylerule);
    }

    private void checkRuleBody(ASTNode astNode) {
        for (ASTNode child : astNode.getChildren()) {
            if (child instanceof Declaration) {
                checkDeclaration(child);
            }
            if (child instanceof VariableAssignment) {
                checkVariableAssignment(child);
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

        System.out.println("----Expression type: " + expressionType + declaration.property.name);

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
        if (expression instanceof VariableReference) {
            return checkVariableReference((VariableReference) expression);
        }
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

    private ExpressionType checkVariableReference(VariableReference expression) {
        // kijk of de varReference in de scope zit.
        System.out.println("variabletypes: ------>" +variableTypes);
//        System.out.println(expression.name + "------");

        for (int i = 0; i < variableTypes.getSize(); i++) {
            HashMap<String, ExpressionType> scope = variableTypes.get(i);
            if (scope.containsKey(expression.name)) {
                return scope.get(expression.name);
            }
        }

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
        ExpressionType expressionType = checkExpression(variableAssignment.expression);

        if (expressionType == null || expressionType == ExpressionType.UNDEFINED) {
            astNode.setError("Variable assignment is undefined/null.");
            return;
        }
        addVariableToCurrentScope(variableReference.name, expressionType);
    }

    /*
    CH02: Controleer of de operanden van de operaties plus en min van gelijk type zijn.
    Je mag geen pixels bij percentages optellen bijvoorbeeld.
    Controleer dat bij vermenigvuldigen minimaal een operand een scalaire waarde is.
    Zo mag 20% * 3 en 4 * 5 wel, maar mag 2px * 3px niet.
     */


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

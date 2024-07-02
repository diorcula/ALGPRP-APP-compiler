//package nl.han.ica.icss.checker;
//
//import nl.han.ica.datastructures.HANLinkedList;
//import nl.han.ica.datastructures.IHANLinkedList;
//import nl.han.ica.icss.ast.*;
//import nl.han.ica.icss.ast.literals.*;
//import nl.han.ica.icss.ast.operations.AddOperation;
//import nl.han.ica.icss.ast.operations.MultiplyOperation;
//import nl.han.ica.icss.ast.operations.SubtractOperation;
//import nl.han.ica.icss.ast.types.ExpressionType;
//
//import java.util.HashMap;
//
//
//public class Checker {
//
//    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;
//
//    public void check(AST ast) {
//        variableTypes = new HANLinkedList<>();
//        checkStyleSheet(ast.root);
//    }
//
//    private void checkStyleSheet(Stylesheet stylesheet) {
//        for (ASTNode child : stylesheet.getChildren()) {
//            if (child instanceof Stylerule) {
//                checkStyleRule(child);
//            }
////            if (child instanceof VariableAssignment) {
////                checkVariableAssignment(child);
////            }
//
//        }
//    }
//
//    private void checkStyleRule(ASTNode astNode) {
//        for (ASTNode child : astNode.getChildren()) {
//            // hier check je meteen een Declaration ipv Selector;
//            if (child instanceof Declaration) {
//                System.out.println("checking declaration - " + child);
//                checkDeclaration(child);
//            }
//        }
//    }
//
//
//    private void checkDeclaration(ASTNode astNode) {
//        Declaration declaration = (Declaration) astNode;
//        ExpressionType expressionType = checkExpression(declaration.expression);
//
//        switch (declaration.property.name) {
//            case "color":
//            case "background-color":
//                if (expressionType != ExpressionType.COLOR) {
//                    astNode.setError(declaration.property.name + " waarde moet een #HEX kleur zijn.");
//                }
//                break;
//            case "width":
//            case "height":
//                if (expressionType != ExpressionType.PIXEL && expressionType != ExpressionType.PERCENTAGE) {
//                    astNode.setError(declaration.property.name + " waarde moet een pixel of percentage zijn.");
//                }
//                break;
//            default:
//                astNode.setError("De property is onbekend");
//                break;
//        }
//
//    }
//
//    private ExpressionType checkExpression(Expression expression) {
//
//        if (expression instanceof VariableReference) {
//            return checkVariableReference((VariableReference) expression);
//        } else if (expression instanceof Operation) {
//            //   return checkOperation((Operation) expression);
//        } else {
//            if (expression instanceof PercentageLiteral) {
//                return ExpressionType.PERCENTAGE;
//            } else if (expression instanceof PixelLiteral) {
//                return ExpressionType.PIXEL;
//            } else if (expression instanceof ColorLiteral) {
//                return ExpressionType.COLOR;
//            } else if (expression instanceof ScalarLiteral) {
//                return ExpressionType.SCALAR;
//            } else if (expression instanceof BoolLiteral) {
//                return ExpressionType.BOOL;
//            }
//        }
//        return ExpressionType.UNDEFINED;
//    }
//
//    private ExpressionType checkVariableReference(VariableReference expression) {
//        ExpressionType expressionType = (ExpressionType) variableTypes.getVariable((expression).name);
//        if (expressionType == null) {
//            expression.setError("Variable not yet declared or in scope.");
//            return null;
//        }
//        return expressionType;
//    }
//
////
////    private boolean checkExpressionTypeAllowed(ExpressionType expressionType, ExpressionType previousExpressionType) {
////        return (previousExpressionType != null) && expressionType != previousExpressionType;
////    }
////
////    private void checkVariableAssignment(ASTNode astNode) {
////        VariableAssignment variableAssignment = (VariableAssignment) astNode;
////        VariableReference variableReference = variableAssignment.name;
////        ExpressionType expressionType = this.checkExpression(variableAssignment.expression);
////
////        if (expressionType == null || expressionType == ExpressionType.UNDEFINED) {
////            astNode.setError("Variable assignment is undefined/null.");
////            return;
////        }
////
////        ExpressionType previousExpressionType = (ExpressionType) this.variableTypes.getVariable(variableReference.name);
////        if (checkExpressionTypeAllowed(expressionType, previousExpressionType)) {
////            astNode.setError("Variabele mag niet van type veranderen van " + previousExpressionType + " naar " + expressionType);
////        }
////        variableTypes.putVariable(variableReference.name, expressionType);
////    }
////
////    private ExpressionType checkOperation(Operation operation) {
////        ExpressionType left;
////        ExpressionType right;
////
////        if (operation.lhs instanceof Operation) {
////            left = this.checkOperation((Operation) operation.lhs);
////        } else {
////            left = checkExpression(operation.lhs);
////        }
////
////        if (operation.rhs instanceof Operation) {
////            right = this.checkOperation((Operation) operation.rhs);
////        } else {
////            right = checkExpression(operation.rhs);
////        }
////
////        if (left == ExpressionType.COLOR || right == ExpressionType.COLOR) {
////            operation.setError("Colors are not allowed in operations.");
////            return ExpressionType.UNDEFINED;
////        }
////
////        if (operation instanceof MultiplyOperation) {
////            if (left != ExpressionType.SCALAR && right != ExpressionType.SCALAR) {
////                operation.setError("Multiply is only allowed with at least one scalar literal.");
////                return ExpressionType.UNDEFINED;
////            }
////            return right != ExpressionType.SCALAR ? right : left;
////        } else if ((operation instanceof SubtractOperation || operation instanceof AddOperation) && left != right) {
////            operation.setError("You can only do add and subtract operations with the same literal.");
////            return ExpressionType.UNDEFINED;
////        }
////        return left;
////    }
////
//
//
////    private List<String> getList(Stylesheet root) {
////        List<String> list = new ArrayList<>();
////        for (ASTNode child : root.getChildren()) {
////            if (child instanceof VariableAssignment) {
////                for (ASTNode child2 : child.getChildren()) {
////                    if (child2 instanceof VariableReference) {
////                        list.add(((VariableReference) child2).name);
////                    }
////                }
////            }
////        }
////        return list;
////    }
//}

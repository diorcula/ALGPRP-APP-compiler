package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.HashmapTableLinked;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;

import java.util.ArrayList;
import java.util.List;

public class Evaluator implements Transform {

    private final HashmapTableLinked<String, Literal> variableValues;

    public Evaluator() {
        variableValues = new HashmapTableLinked<>();
    }

    @Override
    public void apply(AST ast) {
        Stylesheet stylesheet = ast.root;
        this.transformStylesheet(stylesheet);
    }

    private void transformStylesheet(ASTNode astNode) {
        List<ASTNode> toRemove = new ArrayList<>();

        this.variableValues.pushScope();

        for (ASTNode child : astNode.getChildren()) {
            if (child instanceof VariableAssignment) {
                this.transformVariableAssignment((VariableAssignment) child);
                toRemove.add(child);
                continue;
            }

            if (child instanceof Stylerule) {
                this.transformStylerule((Stylerule) child);
            }
        }

        this.variableValues.popScope();

        for (ASTNode rem : toRemove) {
            astNode.removeChild(rem);
        }
    }

    private void transformStylerule(Stylerule stylerule) {
        ArrayList<ASTNode> toAdd = new ArrayList<>();

        this.variableValues.pushScope();

        for (ASTNode child : stylerule.body) {
            this.transformRuleBody(child, toAdd);
        }

        this.variableValues.popScope();

        stylerule.body = toAdd;
    }

    private void transformRuleBody(ASTNode astNode, ArrayList<ASTNode> parentBody) {
        if (astNode instanceof VariableAssignment) {
            this.transformVariableAssignment((VariableAssignment) astNode);
            return;
        }

        if (astNode instanceof Declaration) {
            this.transformDeclaration((Declaration) astNode);
            parentBody.add(astNode);
            return;
        }

        if (astNode instanceof IfClause) {
            IfClause ifClause = (IfClause) astNode;
            ifClause.conditionalExpression = this.transformExpression(ifClause.conditionalExpression);

            // When IF expression is true
            if (((BoolLiteral) ifClause.conditionalExpression).value) {
                // Remove ELSE clause body when there is one.
                if (ifClause.elseClause != null) {
                    ifClause.elseClause.body = new ArrayList<>();
                }
            } else {
                // When there is no ELSE clause body, remove IF clause body.
                if (ifClause.elseClause == null) {
                    ifClause.body = new ArrayList<>();
                    return;
                } else {
                    // If there is an ELSE clause body, move it to the IF clause body.
                    ifClause.body = ifClause.elseClause.body;
                    ifClause.elseClause.body = new ArrayList<>();
                }
            }

            this.transformIfClause((IfClause) astNode, parentBody);
        }
    }

    private void transformIfClause(IfClause ifClause, ArrayList<ASTNode> parentBody) {
        for (ASTNode child : ifClause.getChildren()) {
            this.transformRuleBody(child, parentBody);
        }
    }

    private void transformDeclaration(Declaration declaration) {
        declaration.expression = this.transformExpression(declaration.expression);
    }

    // save var to viariablevalues and before is added to components to remove
    private void transformVariableAssignment(VariableAssignment variableAssignment) {
        Expression expression = variableAssignment.expression;
        variableAssignment.expression = this.transformExpression(expression);
        System.out.println(variableAssignment + "---" + variableAssignment.name +"---"+ variableAssignment.name.name);
        this.variableValues.putVariable(variableAssignment.name.name, (Literal) variableAssignment.expression);
    }


    private Literal transformExpression(Expression expression) {
        if (expression instanceof Operation) {
            return this.transformOperation((Operation) expression);
        }

        if (expression instanceof VariableReference) {
            return this.variableValues.getVariable(((VariableReference) expression).name);
        }

        return (Literal) expression;
    }

    private Literal transformOperation(Operation operation) {
        Literal left;
        Literal right;

        int leftValue;
        int rightValue;

        //bereken links tot in de dieptste child
        if (operation.lhs instanceof Operation) {
            left = this.transformOperation((Operation) operation.lhs);
        } else if (operation.lhs instanceof VariableReference) {
            left = this.variableValues.getVariable(((VariableReference) operation.lhs).name);
        } else {
            left = (Literal) operation.lhs;
        }

        //bereken rechts tot in de dieptste child
        if (operation.rhs instanceof Operation) {
            right = this.transformOperation((Operation) operation.rhs);
        } else if (operation.rhs instanceof VariableReference) {
            right = this.variableValues.getVariable(((VariableReference) operation.rhs).name);
        } else {
            right = (Literal) operation.rhs;
        }

        leftValue = this.getLiteralValue(left);
        rightValue = this.getLiteralValue(right);


        if (operation instanceof AddOperation) {
            System.out.println("add operation:: ----- " + leftValue + " + " + rightValue);
            return this.cleanLit(left, leftValue + rightValue);
        } else if (operation instanceof SubtractOperation) {
            return this.cleanLit(left, leftValue - rightValue);
        } else {
            if (right instanceof ScalarLiteral) {
                return this.cleanLit(left, leftValue * rightValue);
            } else {
                return this.cleanLit(right, leftValue * rightValue);
            }
        }
    }

    private int getLiteralValue(Literal literal) {
        if (literal instanceof PixelLiteral) {
            return ((PixelLiteral) literal).value;
        } else if (literal instanceof ScalarLiteral) {
            return ((ScalarLiteral) literal).value;
        } else if (literal instanceof PercentageLiteral) {
            return ((PercentageLiteral) literal).value;
        } else {
            return 0;
        }
    }

    private Literal cleanLit(Literal literal, int value) {
        if (literal instanceof PixelLiteral) {
            return new PixelLiteral(value);
        } else if (literal instanceof ScalarLiteral) {
            return new ScalarLiteral(value);
        } else {
            return new PercentageLiteral(value);
        }
    }
}
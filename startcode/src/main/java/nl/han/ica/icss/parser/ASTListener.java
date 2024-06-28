package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {

    //Accumulator attributes:
    private AST ast;

    //Use this to keep track of the parent nodes when recursively traversing the ast
    private IHANStack<ASTNode> currentContainer;

    public ASTListener() {
        ast = new AST();
        currentContainer = new HANStack<>();
    }

    public AST getAST() {
        return ast;
    }

    @Override
    public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
        // zet iets op de stack
        ASTNode stylesheet = new Stylesheet();
        currentContainer.push(stylesheet);
    }

    @Override
    public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
        // de return value van curr.pop() is een T, dus moet je casten naar Stylesheet
        ast.setRoot((Stylesheet) currentContainer.pop());
    }

    @Override
    public void enterStylerule(ICSSParser.StyleruleContext ctx) {
        ASTNode styleRule = new Stylerule();
        currentContainer.push(styleRule);
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx) {
        ASTNode styleRule = currentContainer.pop();
        // je gebruikt hier peek() omdat de stylerule er vanaf.
        // met peek krijg je de eerste terug van de stack,
        // waar je hem als child wil toevoegen
        currentContainer.peek().addChild(styleRule);
    }

    // gebruik je niet, zelfde als met selector/body etc.
//    @Override
//    public void enterVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
//        ASTNode variableAssignment = new VariableAssignment();
//        currentContainer.push(variableAssignment);
//    }
//
//    @Override
//    public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
//        ASTNode variableAssignment = currentContainer.pop();
//        currentContainer.peek().addChild(variableAssignment);
//    }

    @Override
    public void enterVariableReference(ICSSParser.VariableReferenceContext ctx) {
        ASTNode variableReference = new VariableReference(ctx.getText());
        currentContainer.push(variableReference);
    }

    @Override
    public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
        ASTNode variableReference = currentContainer.pop();
        currentContainer.peek().addChild(variableReference);
    }

    @Override
    public void enterTagselector(ICSSParser.TagselectorContext ctx) {
        ASTNode tagSelector = new TagSelector(ctx.getText());
        currentContainer.push(tagSelector);
    }

    @Override
    public void exitTagselector(ICSSParser.TagselectorContext ctx) {
        ASTNode tagSelector = currentContainer.pop();
        currentContainer.peek().addChild(tagSelector);
    }

    @Override
    public void enterClassselector(ICSSParser.ClassselectorContext ctx) {
        // let op hier heb je de text nodig om te weten welke class het is
        ASTNode classSelector = new ClassSelector(ctx.getText());
        currentContainer.push(classSelector);
    }

    @Override
    public void exitClassselector(ICSSParser.ClassselectorContext ctx) {
        ASTNode classSelector = currentContainer.pop();
        currentContainer.peek().addChild(classSelector);
    }

    @Override
    public void enterIdselector(ICSSParser.IdselectorContext ctx) {
        ASTNode idSelector = new IdSelector(ctx.getText());
        currentContainer.push(idSelector);
    }

    @Override
    public void exitIdselector(ICSSParser.IdselectorContext ctx) {
        ASTNode idSelector = currentContainer.pop();
        currentContainer.peek().addChild(idSelector);
    }

    @Override
    public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
        ASTNode declaration = new Declaration();
        currentContainer.push(declaration);
    }

    @Override
    public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
        ASTNode declaration = currentContainer.pop();
        currentContainer.peek().addChild(declaration);
    }

    @Override
    public void enterPropertyname(ICSSParser.PropertynameContext ctx) {
        ASTNode propertyName = new PropertyName(ctx.getText());
        currentContainer.push(propertyName);
    }

    @Override
    public void exitPropertyname(ICSSParser.PropertynameContext ctx) {
        ASTNode propertyName = currentContainer.pop();
        currentContainer.peek().addChild(propertyName);
    }

    @Override
    public void enterBoolliteral(ICSSParser.BoolliteralContext ctx) {
        ASTNode boolLiteral = new BoolLiteral(ctx.getText());
        currentContainer.push(boolLiteral);
    }

    @Override
    public void exitBoolliteral(ICSSParser.BoolliteralContext ctx) {
        ASTNode boolLiteral = currentContainer.pop();
        currentContainer.peek().addChild(boolLiteral);
    }

    @Override
    public void enterColorliteral(ICSSParser.ColorliteralContext ctx) {
        ASTNode colorLiteral = new ColorLiteral(ctx.getText());
        currentContainer.push(colorLiteral);
    }

    @Override
    public void exitColorliteral(ICSSParser.ColorliteralContext ctx) {
        ASTNode colorLiteral = currentContainer.pop();
        currentContainer.peek().addChild(colorLiteral);
    }

    @Override
    public void enterPercentageliteral(ICSSParser.PercentageliteralContext ctx) {
        ASTNode percentageLiteral = new PercentageLiteral(ctx.getText());
        currentContainer.push(percentageLiteral);
    }

    @Override
    public void exitPercentageliteral(ICSSParser.PercentageliteralContext ctx) {
        ASTNode percentageLiteral = currentContainer.pop();
        currentContainer.peek().addChild(percentageLiteral);
    }

    @Override
    public void enterPixelliteral(ICSSParser.PixelliteralContext ctx) {
        ASTNode pixelLiteral = new PixelLiteral(ctx.getText());
        currentContainer.push(pixelLiteral);
    }

    @Override
    public void exitPixelliteral(ICSSParser.PixelliteralContext ctx) {
        ASTNode pixelLiteral = currentContainer.pop();
        currentContainer.peek().addChild(pixelLiteral);
    }

    @Override
    public void enterScalarliteral(ICSSParser.ScalarliteralContext ctx) {
        ASTNode scalarLiteral = new ScalarLiteral(ctx.getText());
        currentContainer.push(scalarLiteral);
    }

    @Override
    public void exitScalarliteral(ICSSParser.ScalarliteralContext ctx) {
        ASTNode scalarLiteral = currentContainer.pop();
        currentContainer.peek().addChild(scalarLiteral);
    }
}
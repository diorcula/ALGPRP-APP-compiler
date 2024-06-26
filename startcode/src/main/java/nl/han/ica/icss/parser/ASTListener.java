package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
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
        //haal het van de stack af
        //voeg toe als child aan de volgende
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

//    @Override
//    public void enterTagselector(ICSSParser.TagselectorContext ctx) {
//        ASTNode tagSelector = new TagSelector(ctx.getText());
//        currentContainer.peek().addChild(tagSelector);
//    }
//
//    @Override
//    public void exitTagselector(ICSSParser.TagselectorContext ctx) {
//        ASTNode tagSelector = currentContainer.pop();
//        currentContainer.peek().addChild(tagSelector);
//    }
}
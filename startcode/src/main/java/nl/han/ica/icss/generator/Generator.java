package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;

public class Generator {

	private final StringBuilder sb;

	public Generator() {
		this.sb = new StringBuilder();
	}

	public String generate(AST ast) {
		this.generateNode(ast.root);
		return sb.toString();
	}

	private void generateNode(ASTNode astNode) {
		for (ASTNode node : astNode.getChildren()) {
			if (node instanceof Stylerule) {
				this.generateSelector(node);
				this.generateDeclaration(node);
				this.sb.append("}\n\n");
			}
		}
		// Remove one \n character.
		if (this.sb.length() > 1) {
			this.sb.delete(this.sb.length() - 1, this.sb.length());
		}
	}

	private void generateSelector(ASTNode astNode) {
		Stylerule stylerule = (Stylerule) astNode;

		for (var selector :stylerule.selectors){
//			sb.append(selector.toString() + ", "); //niet nodig
			sb.append(selector.toString());
		}

//		sb.delete(sb.length() -2, sb.length()); // niet nodig als er toch geen comma in zit: check dit
		this.sb.append(" {\n");
	}

	private void generateDeclaration(ASTNode astNode) {
		var ident = new StringBuilder();
		ident.append("  "); // hier spring je twee spaties in
		for (ASTNode node : astNode.getChildren()) {
			if (node instanceof Declaration) {
				Declaration declaration = (Declaration) node;
				this.sb.append(ident+ declaration.property.name + ": ")
						.append(this.expressionToString(declaration.expression) + ";\n");
			}
		}
	}

	private String expressionToString(Expression expression) {
		if (expression instanceof PercentageLiteral) {
			return ((PercentageLiteral) expression).value + "%";
		}
		if (expression instanceof ColorLiteral) {
			return (((ColorLiteral) expression).value); //haal de # weg, want die zit er al in.
		}
		if (expression instanceof PixelLiteral) {
			return ((PixelLiteral) expression).value + "px";
		}

		return ""; // scalar
	}
	
}

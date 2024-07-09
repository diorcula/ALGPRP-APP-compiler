grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';

//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;

//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';

//--- PARSER: ---
stylesheet: variableAssignment* styleRule* EOF;
styleRule: selector OPEN_BRACE ruleBody CLOSE_BRACE;
declaration: propertyName COLON expression SEMICOLON;
propertyName: LOWER_IDENT;

variableAssignment: variableReference ASSIGNMENT_OPERATOR expression+ SEMICOLON;

ifClause: IF BOX_BRACKET_OPEN (variableReference | boolLiteral) BOX_BRACKET_CLOSE OPEN_BRACE ruleBody CLOSE_BRACE elseClause?;
elseClause: ELSE OPEN_BRACE ruleBody CLOSE_BRACE;

expression: literal | expression MUL expression | expression (PLUS | MIN) expression;

boolLiteral: TRUE | FALSE;
colorLiteral: COLOR;
percentageLiteral: PERCENTAGE;
pixelLiteral: PIXELSIZE;
scalarLiteral: SCALAR;
variableReference: CAPITAL_IDENT;
literal: boolLiteral | colorLiteral | percentageLiteral | pixelLiteral | scalarLiteral | variableReference;

classSelector: CLASS_IDENT;
tagSelector: LOWER_IDENT;
idSelector: ID_IDENT | COLOR;
selector: (tagSelector | classSelector | idSelector);

ruleBody: (declaration | ifClause | variableAssignment)*;

//stylesheet: variableAssignment* stylerule* EOF;
//stylerule: selector OPEN_BRACE body CLOSE_BRACE;
//body: (declaration | variableAssignment | ifClause)*;
//
//variableAssignment: variableReference ASSIGNMENT_OPERATOR expression SEMICOLON;
//declaration: propertyname COLON expression SEMICOLON;
//expression: literal | expression MUL expression | expression (PLUS | MIN) expression;
//
//ifClause: IF BOX_BRACKET_OPEN (variableReference | boolliteral) BOX_BRACKET_CLOSE OPEN_BRACE body CLOSE_BRACE elseClause?;
//elseClause: ELSE OPEN_BRACE body CLOSE_BRACE;
//
//selector: (tagselector | idselector | classselector);
//classselector: CLASS_IDENT;
//idselector: ID_IDENT;
//tagselector: LOWER_IDENT;
//variableReference: CAPITAL_IDENT;
//propertyname: LOWER_IDENT | CAPITAL_IDENT;
//
//literal: boolliteral | variableReference | colorliteral |
//         percentageliteral | pixelliteral | scalarliteral ;
//boolliteral: TRUE|FALSE;
//colorliteral: COLOR;
//percentageliteral: PERCENTAGE;
//pixelliteral: PIXELSIZE;
//scalarliteral: SCALAR;


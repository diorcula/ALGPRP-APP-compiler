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
stylesheet: stylerule* EOF;

stylerule: selector OPEN_BRACE body CLOSE_BRACE;

selector: classselector | idselector | tagselector;
classselector: CLASS_IDENT;
idselector: ID_IDENT;
tagselector: LOWER_IDENT;

body: declaration*;
declaration: propertyname COLON  literal SEMICOLON;

propertyname: LOWER_IDENT | CAPITAL_IDENT;

literal: boolliteral | colorliteral | percentageliteral | pixelliteral | scalarliteral;
boolliteral: TRUE|FALSE;
colorliteral: COLOR;
percentageliteral: PERCENTAGE;
pixelliteral: PIXELSIZE;
scalarliteral: SCALAR;


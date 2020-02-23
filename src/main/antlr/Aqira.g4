grammar Aqira ;

top : decl* EOF ;

decl : LET ID ':' expr '=' expr
     | NORMALIZE LET ID
     | NORMALIZE expr AT expr
     ;

expr : term+
     | LET ID ':' expr '=' expr IN expr
     | LET ID '=' expr IN expr
     | '(' expr AT expr ')'
     | SUC expr
     | REC expr AT ID ARROW expr ON
       '|' ZERO ARROW expr
       '|' SUC ID ',' ID ARROW expr
     | LAM ID ARROW expr
     | '(' ID ':' expr ')' ARROW expr
     | '(' ID ':' expr ')' '*' expr
     | term ARROW expr
     | term '*' expr
     | FST expr
     | SND expr
     ;

term : '(' expr ')'
     | '(' expr AT expr ')'
     | ID
     | ZERO
     | NUMERAL
     | UNIV '[' NUMERAL ']'
     | NAT
     | '[' expr ',' expr ']'
     ;

ZERO : 'zero' ;
SUC : 'suc' ;
NAT : 'Nat' ;
LET : 'let' ;
IN : 'in' ;
ON : 'on' ;
REC : 'rec' ;
FST : 'fst' ;
SND : 'snd' ;
LAM : 'fn' ;
UNIV : 'U' ;
AT : 'at' ;
NORMALIZE : 'normalize' ;

ARROW : '->' ;

fragment ID_FIRST : [\p{Alpha}\p{General_Category=Other_Letter}] ;
ID : ID_FIRST [\p{Alnum}\p{General_Category=Other_Letter}]* ;

NUMERAL : [0-9]+ ;

WHITESPACE : [ \t\r\n]+ -> skip ;

COMMENT : '(*' ( COMMENT | . )*? '*)' -> skip ;

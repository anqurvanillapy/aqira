grammar Aqira ;

fragment ID_FIRST : [\p{Alpha}\p{General_Category=Other_Letter}] ;
ID : ID_FIRST [\p{Alnum}\p{General_Category=Other_Letter}]* ;

WHITESPACE : [ \t\r\n]+ -> skip ;

COMMENT : '(*' ( COMMENT | . )*? '*)' -> skip ;

ZERO : 'zero' ;
SUC : 'suc' ;
NAT : 'Nat' ;
LET : 'let' ;
IN : 'in' ;
WITH : 'with' ;
REC : 'rec' ;
FST : 'fst' ;
SND : 'snd' ;
LAM : 'fn' ;
UNIV : 'U' ;
DEF : 'def' ;
AT : 'at' ;
NORMALIZE : 'normalize' ;

decls : decl* EOF
      ;

decl : LET ID ':' term '=' term
     ;

term :
     | ID
     ;

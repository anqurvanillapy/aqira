grammar Aqira ;

fragment ID_FIRST : [a-zA-Z_'\\] ;
ID : ID_FIRST ( ID_FIRST | [0-9] )* ;

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
LAM : 'fun' ;
UNIV : 'U' ;
DEF : 'def' ;
AT : 'at' ;
NORMALIZE : 'normalize' ;
QUIT : 'quit' ;

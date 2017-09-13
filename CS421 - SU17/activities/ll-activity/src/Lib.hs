module Lib
    ( Exp(..)
    , parse
    ) where

import Text.Regex.TDFA

isInt :: String -> Bool
isInt i = i =~ "[0-9]+"

isSymbol :: String -> String -> Bool
isSymbol s v = s == v

isVar :: String -> Bool
isVar i = i =~ "[a-z]+"

parseSymbol s (x:xs) =
  if isSymbol s x
     then (s,xs)
     else error $ "Parse error, expected " ++ s ++ " but got " ++ x ++ "."

-- Grammar
--
-- E -> + E E
--    | int
--    | var
--    | ( E )
--    | let var = E in E end

data Exp = PlusExp Exp Exp
         | IntExp Integer
         | VarExp String
         | LetExp String Exp Exp
    deriving (Show, Eq)

parse xx = parseE (words xx)

parseE (x:xs) 
         | isSymbol "+" x =
             let (e1,r1) = parseE xs
                 (e2,r2) = parseE r1
--                 r3 = if (head r2) == "end" 
--                         then []
--                         else r2
             in (PlusExp e1 e2, r2)

--         | isSymbol "end" x = parseE []
         | isSymbol "=" x = parseE xs
         | isSymbol "in" x = parseE xs
         | isSymbol "(" x =
             let (e1,r1) = parseE xs
                 (")":r2) = r1
             in (e1,r2)
         
         | isSymbol "let" x =
             let (v,r1) = ((head xs), (tail xs))
                 (e1,r2) = parseE r1
                 (e2,r3) = parseE r2
                 r4 = if (head r3) == "end" 
                         then (tail r3)
                         else r3 
             in (LetExp v e1 e2, r4)                  
         
parseE (x:xs) 
         | isInt x = (IntExp (read x), xs)
         | isVar x = (VarExp x, xs)
         | otherwise = parseE []

{-|
parse "+ 7 (+ 8 10)"
[+,7,(,+,8,10,)]
-}           

{-|
parse "let x = 20 in ( + x 10 ) end"
[let,x,=,20,in,(,+,x,10,),end]
let:[x,=,20,in,(,+,x,10,),end]
x:[=,20,in,(,+,x,10,),end]
=:[20,in,(,+,x,10,),end]
20:[in,(,+,x,10,),end]
in:[(,+,x,10,),end]
(:[+,x,10,),end]
+:[x,10,),end]
x:[10,),end]
10:[),end]
):[end]
end:[]
-}

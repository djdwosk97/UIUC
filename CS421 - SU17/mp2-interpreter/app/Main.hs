--- Getting Started
--- ===============

--- Relevant Files
--- --------------

module Main where

import System.IO (hFlush, stdout)

import Data.HashMap.Strict as H (HashMap, empty, fromList, insert, lookup, union)
import Data.Functor.Identity

import Text.ParserCombinators.Parsec hiding (Parser)
import Text.Parsec.Prim (ParsecT)


--- Given Code
--- ==========

--- Data Types
--- ----------

--- ### Environments and Results

type Env  = H.HashMap String Val
type PEnv = H.HashMap String Stmt

type Result = (String, PEnv, Env)

--- ### Values

data Val = IntVal Int
         | BoolVal Bool
         | CloVal [String] Exp Env
         | ExnVal String
    deriving (Eq)

instance Show Val where
    show (IntVal i) = show i
    show (BoolVal i) = show i
    show (CloVal xs body env) = "<" ++ show xs   ++ ", "
                                    ++ show body ++ ", "
                                    ++ show env  ++ ">"
    show (ExnVal s) = "exn: " ++ s

--- ### Expressions

data Exp = IntExp Int
         | BoolExp Bool
         | FunExp [String] Exp
         | LetExp [(String,Exp)] Exp
         | AppExp Exp [Exp]
         | IfExp Exp Exp Exp
         | IntOpExp String Exp Exp
         | BoolOpExp String Exp Exp
         | CompOpExp String Exp Exp
         | VarExp String
    deriving (Show, Eq)

--- ### Statements

data Stmt = SetStmt String Exp
          | PrintStmt Exp
          | QuitStmt
          | IfStmt Exp Stmt Stmt
          | ProcedureStmt String [String] Stmt
          | CallStmt String [Exp]
          | SeqStmt [Stmt]
    deriving (Show, Eq)

--- Primitive Functions
--- -------------------

intOps :: H.HashMap String (Int -> Int -> Int)
intOps = H.fromList [ ("+", (+))
                    , ("-", (-))
                    , ("*", (*))
                    , ("/", (div))
                    ]

boolOps :: H.HashMap String (Bool -> Bool -> Bool)
boolOps = H.fromList [ ("and", (&&))
                     , ("or", (||))
                     ]

compOps :: H.HashMap String (Int -> Int -> Bool)
compOps = H.fromList [ ("<", (<))
                     , (">", (>))
                     , ("<=", (<=))
                     , (">=", (>=))
                     , ("/=", (/=))
                     , ("==", (==))
                     ]

--- Parser
--- ------

-- Pretty name for Parser types
type Parser = ParsecT String () Identity

-- for testing a parser directly
run :: Parser a -> String -> a
run p s =
    case parse p "<stdin>" s of
        Right x -> x
        Left x  -> error $ show x

-- Lexicals

symbol :: String -> Parser String
symbol s = do string s
              spaces
              return s

int :: Parser Int
int = do digits <- many1 digit <?> "an integer"
         spaces
         return (read digits :: Int)

var :: Parser String
var = do v <- many1 letter <?> "an identifier"
         spaces
         return v

parens :: Parser a -> Parser a
parens p = do symbol "("
              pp <- p
              symbol ")"
              return pp

-- Expressions

intExp :: Parser Exp
intExp = do i <- int
            return $ IntExp i

boolExp :: Parser Exp
boolExp =    ( symbol "true"  >> return (BoolExp True)  )
         <|> ( symbol "false" >> return (BoolExp False) )

varExp :: Parser Exp
varExp = do v <- var
            return $ VarExp v

opExp :: (String -> Exp -> Exp -> Exp) -> String -> Parser (Exp -> Exp -> Exp)
opExp ctor str = symbol str >> return (ctor str)

mulOp :: Parser (Exp -> Exp -> Exp)
mulOp = let mulOpExp = opExp IntOpExp
        in  mulOpExp "*" <|> mulOpExp "/"

addOp :: Parser (Exp -> Exp -> Exp)
addOp = let addOpExp = opExp IntOpExp
        in  addOpExp "+" <|> addOpExp "-"

andOp :: Parser (Exp -> Exp -> Exp)
andOp = opExp BoolOpExp "and"

orOp :: Parser (Exp -> Exp -> Exp)
orOp = opExp BoolOpExp "or"

compOp :: Parser (Exp -> Exp -> Exp)
compOp = let compOpExp s = symbol s >> return (CompOpExp s)
         in     try (compOpExp "<=")
            <|> try (compOpExp ">=")
            <|> compOpExp "/="
            <|> compOpExp "=="
            <|> compOpExp "<"
            <|> compOpExp ">"

ifExp :: Parser Exp
ifExp = do try $ symbol "if"
           e1 <- expr
           symbol "then"
           e2 <- expr
           symbol "else"
           e3 <- expr
           symbol "fi"
           return $ IfExp e1 e2 e3

funExp :: Parser Exp
funExp = do try $ symbol "fn"
            symbol "["
            params <- var `sepBy` (symbol ",")
            symbol "]"
            body <- expr
            symbol "end"
            return $ FunExp params body

letExp :: Parser Exp
letExp = do try $ symbol "let"
            symbol "["
            params <- (do v <- var
                          symbol ":="
                          e <- expr
                          return (v,e)
                      )
                      `sepBy` (symbol ";")
            symbol "]"
            body <- expr
            symbol "end"
            return $ LetExp params body

appExp :: Parser Exp
appExp = do try $ symbol "apply"
            efn <- expr
            symbol "("
            exps <- expr `sepBy` (symbol ",")
            symbol ")"
            return $ AppExp efn exps

expr :: Parser Exp
expr = let disj = conj `chainl1` andOp
           conj = arith `chainl1` compOp
           arith = term `chainl1` addOp
           term = factor `chainl1` mulOp
           factor = atom
       in  disj `chainl1` orOp

atom :: Parser Exp
atom = intExp
   <|> funExp
   <|> ifExp
   <|> letExp
   <|> try boolExp
   <|> appExp
   <|> varExp
   <|> parens expr

-- Statements

quitStmt :: Parser Stmt
quitStmt = do try $ symbol "quit"
              symbol ";"
              return QuitStmt

printStmt :: Parser Stmt
printStmt = do try $ symbol "print"
               e <- expr
               symbol ";"
               return $ PrintStmt e

setStmt :: Parser Stmt
setStmt = do v <- var
             symbol ":="
             e <- expr
             symbol ";"
             return $ SetStmt v e

ifStmt :: Parser Stmt
ifStmt = do try $ symbol "if"
            e1 <- expr
            symbol "then"
            s2 <- stmt
            symbol "else"
            s3 <- stmt
            symbol "fi"
            return $ IfStmt e1 s2 s3

procStmt :: Parser Stmt
procStmt = do try $ symbol "procedure"
              name <- var
              symbol "("
              params <- var `sepBy` (symbol ",")
              symbol ")"
              body <- stmt
              symbol "endproc"
              return $ ProcedureStmt name params body

callStmt :: Parser Stmt
callStmt = do try $ symbol "call"
              name <- var
              symbol "("
              args <- expr `sepBy` (symbol ",")
              symbol ")"
              symbol ";"
              return $ CallStmt name args

seqStmt :: Parser Stmt
seqStmt = do try $ symbol "do"
             stmts <- many1 stmt
             symbol "od"
             symbol ";"
             return $ SeqStmt stmts

stmt :: Parser Stmt
stmt = quitStmt
   <|> printStmt
   <|> ifStmt
   <|> procStmt
   <|> callStmt
   <|> seqStmt
   <|> try setStmt

--- REPL
--- ----

repl :: PEnv -> Env -> [String] -> String -> IO Result
repl penv env [] _ =
  do putStr "> "
     hFlush stdout
     input <- getLine
     case parse stmt "stdin" input of
        Right QuitStmt -> do putStrLn "Bye!"
                             return ("",penv,env)
        Right x -> let (nuresult,nupenv,nuenv) = exec x penv env
                   in do {
                     putStrLn nuresult;
                     repl nupenv nuenv [] "stdin"
                   }
        Left x -> do putStrLn $ show x
                     repl penv env [] "stdin"

main :: IO Result
main = do
  putStrLn "Welcome to your interpreter!"
  repl H.empty H.empty [] "stdin"


--- Problems
--- ========

--- Lifting Functions
--- -----------------

liftIntOp :: (Int -> Int -> Int) -> Val -> Val -> Val
liftIntOp div _ (IntVal 0) = ExnVal "Division by 0"
liftIntOp op (IntVal x) (IntVal y) = IntVal $ op x y
liftIntOp _ _ _ = ExnVal "Cannot lift"

liftBoolOp :: (Bool -> Bool -> Bool) -> Val -> Val -> Val
liftBoolOp (op) (BoolVal x) (BoolVal y) = BoolVal $ op x y
liftBoolOp _ _ _ = ExnVal "Cannot lift" 

liftCompOp :: (Int -> Int -> Bool) -> Val -> Val -> Val
liftCompOp (op) (IntVal x) (IntVal y) = BoolVal $ op x y
liftCompOp _ _ _ = ExnVal "Cannot lift"

--- Eval
--- ----

eval :: Exp -> Env -> Val

--- ### Constants
eval (IntExp x) _ = IntVal x
eval (BoolExp x) _ = BoolVal x

--- ### Variables
--eval (VarExp x) env
--                  | H.lookup x env == Nothing = ExnVal "No match in env"
--                  | otherwise = H.lookup x env --Returns Maybe Val instead of Val...

eval (VarExp x) env =
   case H.lookup x env of
     Just x -> x
     Nothing -> ExnVal "No match in env"
 
--- ### Arithmetic
--eval (IntOpExp op x y) env = (liftIntOp (Just oper = H.lookup op intOps) (eval x env) (eval y env))
eval (IntOpExp op x y) env = 
    let Just oper = H.lookup op intOps
  in liftIntOp oper (eval x env) (eval y env)

--- ### Boolean and Comparison Operators
eval (BoolOpExp op x y) env = 
    let Just oper = H.lookup op boolOps
  in liftBoolOp oper (eval x env) (eval y env)

eval (CompOpExp op x y) env = 
    let Just oper = H.lookup op compOps
  in liftCompOp oper (eval x env) (eval y env)

--- ### If Expressions
eval (IfExp bool x y) env 
                      | (eval bool env) == BoolVal True = (eval x env)
                      | (eval bool env) == BoolVal False = (eval y env)
                      | otherwise = ExnVal "Condition is not a Bool"

--- ### Functions and Function Application
eval (FunExp xs def) env = CloVal xs def env

eval (AppExp e1 e2) env = case (eval e1 env) of
                                  CloVal nargs nexp nenv
                                                  | e2==[] -> eval nexp nenv
                                                  | otherwise -> let (e:es)=e2
                                                                 in case nargs of
                                                                   [x] -> eval nexp (H.insert x (eval e env) nenv)
                                                                   (x:xs) -> eval (AppExp (FunExp xs nexp) es) (H.insert x (eval e env) nenv)
                                  otherwise -> ExnVal "Apply to non-closure"

--- ### Let Expressions
eval (LetExp xs def) env 
                        | xs==[]= eval def env
                        | otherwise = eval def (H.union (H.fromList (map (\(v,e)->(v, eval e env)) xs)) env)
--                        | (length xs == 1)= eval def (H.union (H.fromList (map (\(v,e)->(v, eval e env)) xs)) env)
--                        | (length xs > 1)= eval def (H.union (H.fromList (map (\(v,e)->(v, eval e env)) xs)) env)


--- Statements
--- ----------

exec :: Stmt -> PEnv -> Env -> Result
exec (PrintStmt e) penv env = (val, penv, env)
    where val = show $ eval e env

--- ### Set Statements
exec (SetStmt x y) penv env = ("", penv, (H.insert x (eval y env) env))

--- ### Sequencing
exec (SeqStmt []) penv env = ("", penv, env)
exec (SeqStmt (x:xs)) penv env = (string1 ++ string2, penv2, env2)
    where (string1, penv1, env1) = exec x penv env
          (string2, penv2, env2) = exec (SeqStmt xs) penv1 env1
 
--- ### If Statements
exec (IfStmt bool x y) penv env 
                            | (eval bool env) == BoolVal True = exec x penv env
                            | (eval bool env) == BoolVal False = exec y penv env
                            | otherwise = (show $ ExnVal "Condition is not a Bool", penv, env)

--- ### Procedure and Call Statements
exec statement@(ProcedureStmt name args body) penv env = ("", H.insert name statement penv, env)

exec (CallStmt name args) penv env = case H.lookup name penv of
--                                           Just (ProcedureStmt stmtName stmtArgs stmtBody) -> exec (ProcedureStmt stmtName stmtArgs stmtBody) penv nenv
--                                                                                                  where nenv = (foldr (\(x,y) e -> H.insert x (eval y env) env stmtArgs
                                           Just (ProcedureStmt stmtName stmtArgs stmtBody) -> exec stmtBody penv (foldr (\(x,y) e -> H.insert x (eval y env) e) env (zip stmtArgs args))
                                           Nothing -> ("Procedure " ++ name ++ " undefined", penv, env)

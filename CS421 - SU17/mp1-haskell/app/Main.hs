--- Getting Started
--- ===============

--- Relevant Files
--- --------------

module Main where

main :: IO ()
main = return ()

--- Problems
--- ========

--- Recursion
--- ---------

--- ### mytake

-- don't forget to put the type declaration or you will lose points!
mytake :: Int -> [a] -> [a]
mytake a [] = []
mytake 0 _ = []
mytake a (x:xs)
           | a<0 = []
           | otherwise = x:(mytake (a-1) xs)

--- ### mydrop

-- don't forget to put the type declaration or you will lose points!
mydrop :: Int -> [a] -> [a]
mydrop a [] = []
mydrop 0 xs = xs
mydrop a (x:xs)
           | a<0 = (x:xs)
           | otherwise = (mydrop (a-1) xs)

--- ### rev

-- don't forget to put the type declaration or you will lose points!
rev :: [a] -> [a]
rev [] = []
rev [x] = [x]
rev (x:xs) 
      | (length xs)<1 = [x]
      | otherwise = ((last xs):(rev(x:init(xs))))
--      | otherwise = ((rev xs) ++ [x])

-- ### app

-- don't forget to put the type declaration or you will lose points!
app :: [a] -> [a] -> [a]
app [] [] = []
app xs [] = xs
app [] ys = ys
app xs ys 
       | (length xs)==1 = ((head xs):ys)
       | otherwise = (app (init xs) ((last xs):ys))


--- ### inclist

-- don't forget to put the type declaration or you will lose points!
inclist :: Num a => [a] -> [a]
inclist [] = []
inclist [x] = [x+1]
inclist (x:xs) = (x+1):(inclist xs)

--- ### sumlist

-- don't forget to put the type declaration or you will lose points!
sumlist :: Num a => [a] -> a
sumlist [] = 0
sumlist [x] = x
sumlist (x:xs) = x+(sumlist(xs))

--- ### myzip

-- don't forget to put the type declaration or you will lose points!
myzip :: [a] -> [b] -> [(a,b)]
myzip [] [] = []
myzip xs [] = [] 
myzip [] ys = []
myzip xs ys = ((head xs),(head ys)):(myzip (tail xs) (tail ys))

--- ### addpairs

-- don't forget to put the type declaration or you will lose points!
addpairs :: (Num a) => [a] -> [a] -> [a]
addpairs [] [] = [] 
addpairs xs [] = []
addpairs [] ys = []
addpairs xs ys = fst(head(myzip xs ys)) + snd(head(myzip xs ys)) : (addpairs (tail xs) (tail ys))

--- ### ones

-- don't forget to put the type declaration or you will lose points!
ones :: [Integer]
--ones 0 = []
--ones a 
--       | a>0 = 1:ones (a-1)
ones = 1:ones

--- ### nats

-- don't forget to put the type declaration or you will lose points!
nats :: [Integer]
nats = iterate (+1) 0

--- ### fib

-- don't forget to put the type declaration or you will lose points!
fib :: [Integer]
--fib = 0:1:(addpairs [0] [1])
--fib = 0:1:(addpairs [(last(init fib))] [(last fib)])
fib = 0:1:(addpairs fib (tail fib))

--- Set Theory
--- ----------

--- ### add

-- don't forget to put the type declaration or you will lose points!
add :: Ord a => a -> [a] -> [a]
add a [] = [a]
add a xs
        | a<(head xs) = a:xs
        | a==(head xs) = xs
        | otherwise = (head xs):(add a (tail xs))

--- ### union

-- don't forget to put the type declaration or you will lose points!
union :: Ord a => [a] -> [a] -> [a]
union [] [] = []
union xs [] = xs
union [] ys = ys
union xs ys
         | (head xs)<(head ys) = ((head xs):(union (tail xs) ys))
         | (head ys)<(head xs) = ((head ys):(union xs (tail ys)))
         | otherwise = ((head xs):(union (tail xs) (tail ys)))

--- ### intersect

-- don't forget to put the type declaration or you will lose points!
intersect :: Ord a => [a] -> [a] -> [a]
intersect [] [] = []
intersect xs [] = []
intersect [] ys = []
intersect xs ys
             | (head xs)==(head ys) = ((head xs):(intersect (tail xs) (tail ys)))
             | (head xs)>(head ys) = intersect xs (tail ys)
             | otherwise = intersect (tail xs) ys

--- ### powerset

-- don't forget to put the type declaration or you will lose points!
powerset :: Ord a => [a] -> [[a]]
powerset [] = [[]]
powerset xs = [[]++xs]
--powerset xs = [(head xs):p | p <- (powerset (tail xs))] ++ [(tail xs)]


--- Higher Order Functions
--- ----------------------

--- ### inclist'

-- don't forget to put the type declaration or you will lose points!
inclist' :: Num a => [a] -> [a]
inclist' [] = []
inclist' xs = map (+1) xs

--- ### sumlist'

-- don't forget to put the type declaration or you will lose points!
sumlist' :: (Num a) => [a] -> a
sunlist' [] = 0
sumlist'  = foldl (+) 0

--- Algebraic Data Types
--- --------------------

data List a = Cons a (List a)
            | Nil
  deriving (Show, Eq)

data Exp = IntExp Integer
         | PlusExp [Exp]
         | MultExp [Exp]
  deriving (Show, Eq)

--- ### list2cons

-- don't forget to put the type declaration or you will lose points!
list2cons :: [a] -> List a
list2cons [] = Nil
list2cons (x:xs) = (Cons x (list2cons xs))

--- ### cons2list

-- don't forget to put the type declaration or you will lose points!
cons2list :: List a -> [a]
cons2list Nil = []
cons2list (Cons x xs) = x:(cons2list xs)

--- ### eval

-- don't forget to put the type declaration or you will lose points!
eval :: Exp -> Integer
--eval (IntExp a) = a
--eval (PlusExp []) = 0
--eval (MultExp []) = 1
--eval (PlusExp (x:xs)) = (eval x)+(eval xs)
eval (IntExp a) = a
eval (PlusExp []) = 0
eval (MultExp []) = 1
eval (PlusExp xs) = sumlist (map eval xs)
eval (MultExp xs) = foldr (*) (1) (map eval xs)

--- ### list2cons'

-- don't forget to put the type declaration or you will lose points!
list2cons' :: [a] -> List a
list2cons' [] = Nil
list2cons' xs = foldr Cons Nil xs

--- ### BinTree

-- BinTree
data BinTree a = Node a (BinTree a) (BinTree a)
               | Leaf
  deriving (Show, Eq)

--- ### sumTree
sumTree :: Num a => BinTree a -> a
sumTree Leaf = 0
sumTree (Node x y z) = x+sumTree (y)+sumTree (z)

--- ### SimpVal
data SimpVal = IntVal Integer
             | BoolVal Bool
             | StrVal String
             | ExnVal String
  deriving (Show, Eq)

-- SimpVal

--- ### liftIntOp
liftIntOp :: (Integer -> Integer -> Integer) -> SimpVal -> SimpVal -> SimpVal
liftIntOp (a) (IntVal x) (IntVal y) = IntVal (a x y)
liftIntOp _ _ _ = ExnVal "not an IntVal!"

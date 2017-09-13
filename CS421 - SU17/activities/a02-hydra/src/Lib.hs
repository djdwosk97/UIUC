module Lib
    ( someFunc
    , chop
    ) where

-- Your code here!
-- Feel free to write helper functions.

-- chop :: [Int] -> [Int]

-- chop :: [Int] -> [Int]
-- chop [1,0,0]  =  [0,2,0] 

{-|
chop :: [Int] -> [Int]
chop [] = []
chop [0] = [0]
--chop [1] = [0]
chop [x] = [x-1]
chop (x:xs) 
--   | x>0 = x-1 & head(xs)+1
	| x>0 = x-1
	| otherwise = chop xs
-}

chop :: [Int] -> [Int]
chop [] = []
chop [0] = [0]
chop [x] = [x-1]
chop (x:xs)
	| x>0 = (x-1):((head xs)+(length xs)):(tail xs)
	| otherwise = x:chop xs

someFunc :: IO ()
someFunc = putStrLn "someFunc"

module Lib
    ( Calc(..)
    , calc
    ) where

data Calc a = Add a
            | Sub a
   deriving (Eq,Show)


calc :: Num a => [Calc a] -> a -> (a -> a) -> (a -> a) -> a
--calc xx init ka ks = aux xx init ka ks
--   where aux [] init newka newks = newka (newks init)
--         aux ((Add i):xs) init newka newks = aux xs init (\v ->newka (v+i)) newks 
--         aux ((Sub i):xs) init newka newks = aux xs init newka (\v -> newks (v-i))



calc xx init ka ks = aux xx init ka ks
   where aux [] init newka newks = (newka init) - (newks 0)
--   where aux [] init newka newks = newka init - newks (newka init)
--   where aux [] init newka newks = newks (newka init)
         aux ((Add i):xs) init newka newks = aux xs init (\v ->newka (v+i)) newks
         aux ((Sub i):xs) init newka newks = aux xs init newka (\v -> newks (v+i))



--calc xx init ka ks = aux xx init ka ks 
--   where aux [] init newka newks = newka newks init
--         aux ((Add i):xs) init newka newks = aux xs init (\v -> newka (v+i)) newks
--         aux ((Sub i):xs) init newka newks = aux xs init newka (\v -> newks (v-i))

--calc init xx ka ks = aux xx init ka ks
--   where aux init [] newka newks = newka newks init
--         aux init ((Add i):xs) newka newks = aux init xs (\v -> newka (v+i)) newks
--         aux init ((Sub i):xs) newks newka = aux init xs (\v -> newks (v-i)) newka

--calc xx z0 ka ks = aux xx z0 ka ks
--   where aux [] z0 newka newks           = newks newka z0
--         aux ((Add i):xs) z0 newka newks = aux xs z0 (\v -> newka (v+i)) newks
--         aux (Sub _:_) _ newka  newks    = 0


--calc = undefined


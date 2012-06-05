(ns jlk.utility.test.core
  (:use [jlk.utility.core])
  (:use [clojure.test]))

(deftest bulkdefn-test
  (bulkdefn [x y] add (+ x y) sub (- x y) mul (* x y) div (/ x y))
  (is (= (add 1 2) 3))
  (is (= (sub 1 2) -1))
  (is (= (mul 1 2) 2))
  (is (= (div 1 2) 1/2)))

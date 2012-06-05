(ns jlk.validation)

;;
;; this is a library for checking function arguments
;; 
;; original idea from:
;; http://stackoverflow.com/questions/1640311/should-i-use-a-function-or-a-macro-to-validate-arguments-in-clojure
;;

(defmulti -validate (fn [_ x & args] x))
(defmethod -validate :string [x _] (string? x))
(defmethod -validate :number [x _] (number? x))
(defmethod -validate :between [x _ a b] (and (> x a)
                                             (< x b)))
(defmethod -validate :number-sequence [x _] (and (seq? x)
                                                 (every? number? x)))
(defmethod -validate :keyword-keys [x _] (and (map? x)
                                              (every? keyword? (keys x))))
(defmethod -validate :keyword-keys-string-vals [x _] (and (map? x)
                                                          (every? keyword? (keys x))
                                                          (every? string? (vals x))))

(defmacro validate
  "use {:pre [(validate x :test arg arg...)]} to test input to function with argument x.

use defmethod jlk.validate/-validate :xxx to define new tests.

example usage:
(defn validation-test
  [x y]
  {:pre [(validate x :number) (validate y :between 1 20)]}
  (* x y))
"
  [val type & args]
  `(let [r# (-validate ~val ~type ~@args)]
     (when (not r#)
       (throw (Exception. (str "validation failed: " ~val " " ~type " " (vector ~@args)))))
     r#))


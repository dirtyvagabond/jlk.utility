(ns jlk.utility.core)

;;
;; programming utility functions
;;

(defn exception
  "utility wrapper around the (throw (Exception. (format ...))) form"
  [s & args]
  (throw (Exception. (apply format s args))))

(defn not-nil?
  [x]
  (not (nil? x)))

(defn get-noisy
  "like get, but raise exception if key not found"
  ([map key]
     (get-noisy map key "key %s not found in map" key))
  ([map key message & args]
     (if-let [rs (get map key)]
       rs
       (apply exception message args))))

(defmacro bulkdefn
  "bulk define simple functions.  eg. (bulkdefn [x y] add (+ x y) sub (- x y) mul (* x y) div (/ x y))"
  [args & fns]
  (conj (map (fn [[name body]] `(defn ~name ~args ~body)) (partition 2 fns)) 'do))

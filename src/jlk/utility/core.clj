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

(defn enforced-get
  "like get, but raise exception if key not found"
  ([map key]
     (get-noisy map key "key %s not found in map" key))
  ([map key msg & args]
     (if-let [rs (get map key)]
       rs
       (apply exception msg args))))

(defmacro bulkdefn
  "bulk define simple functions.  eg. (bulkdefn [x y] add (+ x y) sub (- x y) mul (* x y) div (/ x y))"
  [doc args & fns]
  (conj (map (fn [[name body]] `(defn ~name ~doc ~args ~body)) (partition 2 fns)) 'do))

(defn enforced-split-at
  "like split-at but throws an exception when there aren't enough elements"
  [n coll msg & args]
  (let [[s e] (split-at n coll)]
    (if (< (count s) n)
      (apply exception msg args))
    [s e]))

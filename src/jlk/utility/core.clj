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
  [map key]
  (if-let [rs (get map key)]
    rs
    (exception "key %s not found in map" key)))

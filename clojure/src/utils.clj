(ns utils
  (:import java.io.File)
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn- parent-ns
  ([] (parent-ns *ns*))
  ([ns]
   (when-let [name (try (-> ns ns-name str) (catch Exception _))]
     (->> (str/split name #"\.")
          butlast
          (map munge)
          (interpose File/separator)
          (apply str)))))

(defn read-input
  ([] (read-input "input.txt"))
  ([file]
   (->
     (parent-ns)
     (str File/separator file)
     io/resource
     slurp)))

(defn split-double-lines
  [^CharSequence s]
  (str/split s #"\r?\n\r?\n"))

(defn parse-int [s] (Integer/parseInt (str s)))

(defn parse-ints [coll] (mapv parse-int coll))

(defn split-without [pred coll]
  [(take-while pred coll) (next (drop-while pred coll))])

(defn range'
  "Returns a lazy seq with a length from start"
  [start length]
  (take length (drop start (range))))

(defn fields [^CharSequence s]
  (str/split s #"[\t|\n|\v|\f|\r| ]+"))

(defn transpose [matrix]
  (apply mapv vector matrix))

(defn queue
  ([] (clojure.lang.PersistentQueue/EMPTY))
  ([coll] (reduce conj clojure.lang.PersistentQueue/EMPTY coll)))

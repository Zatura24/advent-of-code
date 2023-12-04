(ns day4.day4
  (:require [utils]
            [clojure.math]
            [clojure.set]
            [clojure.string :as str]))

(defn parse-cards []
  (->>
    (utils/read-input)
    str/split-lines
    (map (comp next (partial re-seq #"\d+|\|")))
    (map (partial utils/split-with #(not= % "|")))
    (map (partial map set))))

(defn part-1 []
  (reduce
    (fn [acc [winning have]]
      (let [overlap (clojure.set/intersection winning have)]
        (if (zero? (count overlap))
          acc
          (+ acc (int (clojure.math/pow 2 (dec (count overlap))))))))
    0
    (parse-cards)))

(defn part-2 []
  (let [cards (parse-cards)
        results (loop [[card & res] cards
                       card-num 1
                       acc {}]
                  (if card
                    (let [overlap (clojure.set/intersection (first card) (second card))]
                      (recur res (inc card-num) (assoc acc card-num (take (count overlap) (drop (inc card-num) (range))))))
                    acc))]
    (loop [[x & xs] (range 1 (inc (count cards)))
           acc 0]
      (if x
        (recur (into xs (get results x)) (inc acc))
        acc))))

(comment
  (part-1)

  (part-2))

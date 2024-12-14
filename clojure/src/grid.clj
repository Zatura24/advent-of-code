(ns grid)

(def UP [-1 0])
(def RIGHT [0 1])
(def DOWN [1 0])
(def LEFT [0 -1])

(def TOP-RIGHT [[-1 0] [-1 1] [0 1]])
(def BOTTOM-RIGHT [[0 1] [1 1] [1 0]])
(def BOTTOM-LEFT [[1 0] [1 -1] [0 -1]])
(def TOP-LEFT [[0 -1] [-1 -1] [-1 0]])

(defn indexed [items]
  (map-indexed vector items))

(defn parse
  ([lines] (parse lines identity))
  ([lines modify-fn]
   (into {}
         (for [[r line] (indexed lines)
               [c v] (indexed line)]
           [[r c] (modify-fn v)]))))

(defn move [position direction]
  (mapv + position direction))

(defn neighbours [grid pos]
  (into []
        (comp
          (map (partial move pos))
          #_(filter grid))
        [UP RIGHT DOWN LEFT]))

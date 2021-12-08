(ns aoc2021.day07
  (:require
   [clojure.string :as str]))

(defn parse [input]
  (map read-string (str/split input #",")))

(defn fuel-cost [cost-fn positions target]
  (->>
   positions
   (map #(cost-fn (Math/abs (- %1 target))))
   (reduce +)))

(defn find-min-cost [cost-fn input]
  (apply min
         (map
          #(fuel-cost cost-fn input %1)
          (range (apply min input) (apply max input)))))

(defn part-1 [input] (find-min-cost identity input))
(defn part-2 [input] (find-min-cost #(/ (* %1 (+ %1 1)) 2) input))
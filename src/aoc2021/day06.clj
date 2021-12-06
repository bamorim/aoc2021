(ns aoc2021.day06
  (:require
   [clojure.string :as str]))

(defn parse [input]
  (map read-string (str/split input #",")))

(defn next-day [state]
  {;; New lanternfishes
   8 (state 0 0)
   7 (state 8 0)
   ;; Reset lanterfishes
   6 (+ (state 7 0) (state 0 0))
   5 (state 6 0)
   4 (state 5 0)
   3 (state 4 0)
   2 (state 3 0)
   1 (state 2 0)
   0 (state 1 0)})

(defn after-n-days [n state]
  (if (> n 0) (after-n-days (- n 1) (next-day state)) state))

(defn total-fishes [state]
  (reduce + (map second state)))

(defn part-1 [input] (total-fishes (after-n-days 80 (frequencies input))))
(defn part-2 [input] (total-fishes (after-n-days 256 (frequencies input))))
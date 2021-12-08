(ns aoc2021.day08
  (:require
   [clojure.string :as str]
   [clojure.set :refer [superset? difference]]))

(defn parse-patterns [patterns]
  (map set (str/split patterns #" ")))

(defn parse-display-note [line]
  (->>
   (str/split line #" \| ")
   (map parse-patterns)))

(defn parse [input]
  (map parse-display-note (str/split-lines input)))

(defn is-unique-digit? [pattern]
  (contains? #{2 3 4 7} (count pattern)))

(defn part-1 [input]
  (->>
   input
   (map second)
   flatten
   (filter is-unique-digit?)
   count))

(defn map-segments [patterns]
  (let
   [by-seg-count (group-by count patterns)

    one (first (by-seg-count 2))
    seven (first (by-seg-count 3))
    four (first (by-seg-count 4))
    eight (first (by-seg-count 7))

    six (first (filter #(not (superset? %1 one)) (by-seg-count 6)))
    nine (first (filter #(superset? %1 four) (by-seg-count 6)))
    zero (first (difference (set (by-seg-count 6)) #{six nine}))

    three (first (filter #(superset? %1 one) (by-seg-count 5)))
    five (first (filter #(superset? six %1) (by-seg-count 5)))
    two (first (difference (set (by-seg-count 5)) #{three five}))]
    {zero 0
     one 1
     two 2
     three 3
     four 4
     five 5
     six 6
     seven 7
     eight 8
     nine 9}))

(defn digit-list-to-number [digits]
  (reduce #(+ (* 10 %1) %2) 0 digits))

(defn get-output-value [[patterns digit-patterns]]
  (digit-list-to-number (map (map-segments patterns) digit-patterns)))

(defn part-2 [input] (reduce + (map get-output-value input)))
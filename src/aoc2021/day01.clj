(ns aoc2021.day01
  (:require
   [clojure.string :as str]
   [clojure.edn :as edn]))

(defn parse [input]
  (->> input
       (str/split-lines)
       (map edn/read-string)))

(defn count-increments [numbers]
  (->> numbers
       (partition 2 1)
       (filter (fn [[a b]] (> b a)))
       (count)))

(def part-1 count-increments)

(defn part-2 [numbers]
  (->> numbers
       (partition 3 1)
       (map #(reduce + %1))
       (count-increments)))
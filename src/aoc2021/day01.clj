(ns aoc2021.day01
  (:require
   [clojure.string :as str]
   [clojure.edn :as edn]))

(defn parse [input]
  (->> input
       (str/split-lines)
       (map edn/read-string)))

(defn count-increments [numbers]
  (let
   [reducer (fn [[count last] current]
              [(if (and (not (nil? last)) (> current last)) (+ count 1) count) current])]
    (nth (reduce reducer [0, nil] numbers) 0)))

(def part-1 count-increments)

(defn part-2 [numbers]
  (->> numbers
       (partition 3 1)
       (map #(reduce + %1))
       (count-increments)))
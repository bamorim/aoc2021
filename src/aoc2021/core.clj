(ns aoc2021.core
  (:gen-class)
  (:require
   [clojure.edn :as edn]
   [aoc2021.day01 :as day01]
   [aoc2021.day02 :as day02]
   [aoc2021.day03 :as day03]
   [aoc2021.day04 :as day04]
   [aoc2021.day05 :as day05]
   [aoc2021.day06 :as day06]
   [aoc2021.day07 :as day07]))

(def default-day "7")

(defn get-day [number]
  (case number
    01 [day01/parse day01/part-1 day01/part-2]
    02 [day02/parse day02/part-1 day02/part-2]
    03 [day03/parse day03/part-1 day03/part-2]
    04 [day04/parse day04/part-1 day04/part-2]
    05 [day05/parse day05/part-1 day05/part-2]
    06 [day06/parse day06/part-1 day06/part-2]
    07 [day07/parse day07/part-1 day07/part-2]
    ((println "Invalid day!")
     (System/exit 1))))

(defn -main
  "Runs an advent of code day"
  [& args]
  (let [day (edn/read-string (or (nth args 0) default-day))
        [parse part-1 part-2] (get-day day)
        parsed (parse (slurp (format "inputs/%02d.txt" day)))]
    (println "Part 1: " (part-1 parsed))
    (println "Part 2: " (part-2 parsed))))
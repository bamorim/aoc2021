(ns aoc2021.core
  (:gen-class)
  (:require
   [clojure.edn :as edn]
   [aoc2021.day01 :as day01]))

(defn get-day [number]
  (case number
    1 [day01/parse day01/part-1 day01/part-2]
    ((println "Invalid day!")
     (System/exit 1))))

(defn -main
  "Runs an advent of code day"
  [& args]
  (let [day (edn/read-string (or (nth args 0) "1"))
        [parse part-1 part-2] (get-day day)
        parsed (parse (slurp (format "inputs/%02d.txt" day)))]
    (println "Part 1: " (part-1 parsed))
    (println "Part 2: " (part-2 parsed))))
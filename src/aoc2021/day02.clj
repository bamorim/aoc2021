(ns aoc2021.day02
  (:require
   [clojure.string :as str]
   [clojure.spec.alpha :as s]
   [clojure.edn :as edn]))

(s/def ::instruction (s/cat ::direction #{:forward :up :down}
                            ::distance (s/and number? pos?)))

(defn parse-instruction [[direction distance]]
  (let
   [instruction [(keyword direction) (edn/read-string distance)]]
    (if (s/valid? ::instruction instruction)
      instruction
      (throw (AssertionError. "Invalid Instruction")))))

(defn parse [input]
  (->> input
       (str/split-lines)
       (map #(str/split %1 #"\s+"))
       (map parse-instruction)))

(defn navigate [instructions]
  (reduce
   (fn [[pos depth] [direction distance]]
     (case direction
       :forward [(+ pos distance) depth]
       :up [pos (- depth distance)]
       :down [pos (+ depth distance)]))
   [0 0]
   instructions))

(defn part-1 [instructions]
  (reduce * (navigate instructions)))

(defn navigate-with-aim [instructions]
  (reduce
   (fn [[pos depth aim] [direction distance]]
     (case direction
       :forward [(+ pos distance) (+ depth (* aim distance)) aim]
       :up [pos depth (- aim distance)]
       :down [pos depth (+ aim distance)]))
   [0 0 0]
   instructions))

(defn part-2 [instructions]
  (let
   [[pos depth _] (navigate-with-aim instructions)]
    (* pos depth)))
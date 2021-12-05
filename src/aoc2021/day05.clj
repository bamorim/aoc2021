(ns aoc2021.day05
  (:require
   [clojure.string :as str]))

(defn parse-point [point]
  (mapv read-string (str/split point #",")))

(defn parse-line [line]
  (mapv parse-point (str/split line #" -> ")))

(defn parse [input]
  (map parse-line (str/split-lines input)))

(defn middle-range [a b]
  (if (> a b)
    (reverse (range b (+ 1 a)))
    (range a (+ 1 b))))

(defn line-points [[[x1 y1] [x2 y2]]]
  (cond
    (= x1 x2) (map (fn [y] [x1 y]) (middle-range y1 y2))
    (= y1 y2) (map (fn [x] [x y1]) (middle-range x1 x2))
    :else (map vector (middle-range x1 x2) (middle-range y1 y2))))

(defn get-repeated [values]
  (->>
   values
   (group-by identity)
   (filter (fn [[_ reps]] (> (count reps) 1)))
   (map first)))

(defn count-crossings [lines]
  (->>
   lines
   (map line-points)
   (apply concat)
   get-repeated
   count))

(defn horizontal? [[[_x1 y1] [_x2 y2]]] (= y1 y2))
(defn vertical? [[[x1 _y1] [x2 _y2]]] (= x1 x2))

(defn part-1 [lines]
  (->>
   lines
   (filter #(or (horizontal? %1) (vertical? %1)))
   count-crossings))

(def part-2 count-crossings)
(ns aoc2021.day03
  (:require
   [clojure.string :as str]))

(str/split "1234" #"")

;; This makes sure we only have 1 and 0s
(defn parse-digit [digit]
  (case digit
    "1" 1
    "0" 0))

(defn parse-row [row]
  (map parse-digit (str/split row #"")))

(defn parse [input]
  (->>
   input
   str/split-lines
   (map parse-row)))

(defn transpose [rows]
  (apply mapv vector rows))

(->>
 [[1 2] [2 3] [3 2] [4 3]]
 (group-by second)
 (sort-by first)
 (reverse)
 (first)
 (second)
 (map first)
 (set))

(defn most-commons [elements]
  (->>
   elements
   (frequencies)
   (group-by second)
   (sort-by first)
   (reverse)
   (first)
   (second)
   (map first)
   (set)))

(defn most-common [elements preference]
  (let
   [commons (most-commons elements)]
    (if (contains? commons preference) preference (first commons))))

(defn comp-bit-array [bits]
  (map #(- 1 %1) bits))

(defn most-common-per-digit [rows]
  (->>
   rows
   (transpose)
   (mapv #(most-common %1 1))))

(defn least-common-per-digit [rows]
  (comp-bit-array (most-common-per-digit rows)))

;; Ok, this is too hacky for me, but ok :(
(defn bit-array-to-number [bits]
  (read-string (format "2r%s" (str/join bits))))

(defn part-1 [rows]
  (let
   [gamma-rate-array (most-common-per-digit rows)
    gamma-rate (bit-array-to-number gamma-rate-array)
    epsilon-rate (bit-array-to-number (comp-bit-array gamma-rate-array))]
    (* gamma-rate epsilon-rate)))

(defn get-rating [bit-criteria-fn rows]
  (->>
   rows
   (first)
   (count)
   (range)
   (reduce
    (fn [rows idx]
      (if (= 1 (count rows))
        rows
        (let
         [bit-criteria (bit-criteria-fn rows)]
          (filter #(= (nth bit-criteria idx) (nth %1 idx)) rows))))
    rows)
   (first)
   (bit-array-to-number)))

(defn oxygen-generator-rating [rows]
  (get-rating most-common-per-digit rows))

(defn co2-scrubber-rating [rows]
  (get-rating least-common-per-digit rows))

(defn part-2 [rows]
  (* (oxygen-generator-rating rows) (co2-scrubber-rating rows)))
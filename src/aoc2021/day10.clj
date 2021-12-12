(ns aoc2021.day10
  (:require
   [clojure.string :as str]))

(def parse str/split-lines)

(def closing ")]}>")
(def opening "([{<")

(def closing-for-opening
  (into {} (map vector
                (vec opening)
                (vec closing))))

(defn parse-expr
  ([line] (parse-expr '() (apply list line)))
  ([stack chars]
   (if (empty? chars)
     (if (empty? stack)
       {:result :success}
       {:result :missing :stack stack})
     (let
      [char (peek chars)
       opening? (contains? (set opening) char)
       closing? (contains? (set closing) char)
       expected-closing (closing-for-opening (peek stack))]
       (cond
         opening? (recur (conj stack char) (pop chars))
         (and closing? (= char expected-closing)) (recur (pop stack) (pop chars))
         closing? {:result :invalid :chars chars})))))

(def invalid-char-points [3 57 1197 25137])
(def points-per-invalid-char
  (into {} (map vector
                (vec closing)
                invalid-char-points)))

(defn part-1 [lines]
  (->>
   lines
   (map parse-expr)
   (filter #(= (%1 :result) :invalid))
   (map #(peek (%1 :chars)))
   (map points-per-invalid-char)
   (reduce +)))

(comment (part-1 (parse (slurp "inputs/sample10.txt"))))
(def missing-char-points [1 2 3 4])
(def points-per-missing-char
  (into {} (map vector
                (vec opening)
                missing-char-points)))

(defn autocomplete-score [stack]
  (reduce #(+ %2 (* 5 %1)) 0 (map points-per-missing-char stack)))

(defn get-autocomplete-winner [points]
  (let [sorted (sort points)
        winner-idx (- (/ (+ (count sorted) 1) 2) 1)]
    (nth sorted winner-idx)))

(defn part-2 [lines]
  (->>
   lines
   (map parse-expr)
   (filter #(= (%1 :result) :missing))
   (map #(%1 :stack))
   (map autocomplete-score)
   get-autocomplete-winner))

(comment (part-2 (parse (slurp "inputs/sample10.txt"))))
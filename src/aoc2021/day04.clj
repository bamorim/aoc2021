(ns aoc2021.day04
  (:require
   [clojure.string :as str]))

(defn parse-numbers [numbers]
  (map read-string (str/split numbers #",")))

(defn parse-board-row [row]
  (read-string (format "[%s]" row)))

(defn parse-board [board]
  (->>
   board
   str/split-lines
   (mapv parse-board-row)))

(defn parse [input]
  (let
   [[numbers & boards] (str/split input #"\n\n")]
    [(parse-numbers numbers) (map parse-board boards)]))

(defn transpose [vecvec]
  (apply mapv vector vecvec))

(defn winning-sets [board]
  (let
   [horizontal-sets (map set board)
    vertical-sets (map set (transpose board))]
    (concat horizontal-sets vertical-sets)))

(defn all-numbers [board]
  (set (flatten board)))

(defn board-state [board]
  [(winning-sets board) (all-numbers board)])

;; Win state of a board, if any
;; The win state is :
;; - The last number
;; - The amount of remaining numbers to be "drawn" (the bigger, the earlier it wins)
;; - The remaining numbers
(defn win-state [numbers [winsets remaining]]
  (if (empty? numbers) nil
      (let
       [[number & rest] numbers
        new-winsets (map #(disj %1 number) winsets)
        new-remaining (disj remaining number)
        new-state [new-winsets new-remaining]
        won (->> new-winsets (filter empty?) seq)]
        (if won [number (count rest) new-remaining] (win-state rest new-state)))))

(defn win-state-score [[last _ remaining]]
  (* last (reduce + remaining)))

;; It ended up that I didn't had to treat the nil case
;; Because it looks like all boards eventually win
;; Therefore when sorting by nth 1 it is all good
(defn part-1 [[numbers boards]]
  (->>
   boards
   (map #(win-state numbers (board-state %1)))
   (sort-by #(nth %1 1))
   last
   win-state-score))

(defn part-2 [[numbers boards]]
  (->>
   boards
   (map #(win-state numbers (board-state %1)))
   (sort-by #(nth %1 1))
   first
   win-state-score))
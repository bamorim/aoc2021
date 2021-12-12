(ns aoc2021.day09
  (:require
   [clojure.string :as str]
   [clojure.set :refer [union select difference]]))

(defn parse [input]
  (mapv #(mapv read-string (str/split %1 #""))
        (str/split-lines input)))

(defn gen-neighbors [points [x y]]
  (for
   [[dx dy] [[-1 0] [1 0] [0 -1] [0 1]]
    :let [x' (+ x dx) y' (+ y dy)]
    :when (and
           (>= x' 0)
           (< x' (count (first points)))
           (>= y' 0)
           (< y' (count points)))] [x' y']))

(defn get-point [points [x y]]
  ((points y) x))

(defn low-point? [points point]
  (let
   [neighbors (gen-neighbors points point)
    this-point (get-point points point)
    smaller-neighbors (filter
                       #(>= this-point (get-point points %1))
                       neighbors)]
    (empty? smaller-neighbors)))

(defn find-low-points [points]
  (for
   [x (range (count (first points)))
    y (range (count points))
    :when (low-point? points [x y])] [x y]))

(defn part-1 [input]
  (->>
   input
   find-low-points
   (map #(+ 1 (get-point input %1)))
   (reduce +)))

(comment (part-1 (parse (slurp "inputs/sample09.txt"))))

(defn basin-for-helper [points boundary height basin]
  (let
   [; Add boundary to the current basin
    basin (union boundary basin)
    ; Get all neighbors that are not on height 9
    neighbors (->>
               boundary
               (map #(set (gen-neighbors points %1)))
               (reduce union)
               (#(difference %1 basin))
               (select #(not= 9 (get-point points %1))))
    ; Get the neighbors with the same height
    same-height-neighbors (select #(= (get-point points %1) height) neighbors)
    ; Get the neighbors on the next height
    next-height-neighbors (select #(= (get-point points %1) (+ 1 height)) neighbors)]
    ;; Debug things because I'm stuck
    (println "\n\n---debugging ---")
    (println "boundary" boundary)
    (println "height" height)
    (println "basin" basin)
    (println "neighbors" neighbors)
    (println "same-height-neighbors" same-height-neighbors)
    (println "next-height-neighbors" next-height-neighbors)
    (cond
      ; If there are still neighbors on that same height, continue searching there
      (seq same-height-neighbors) (recur points same-height-neighbors height basin)
      ; If there are neighbors on the next height, increase height and start searching
      (seq next-height-neighbors) (recur points next-height-neighbors (+ 1 height) basin)
      :else basin)))

(defn basin-for [points point]
  (basin-for-helper points #{point} (get-point points point) #{}))

(defn part-2 [points]
  (->>
   points
   find-low-points
   (map #(basin-for points %1))
   (map count)
   (sort-by #(- %1))
   (take 3)
   (reduce *)))

(comment (part-2 (parse (slurp "inputs/sample09.txt"))))
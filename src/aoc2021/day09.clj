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

(defn basin-for-helper [points boundary basin]
  (let
   [; Add boundary to the current basin
    basin (union boundary basin)
    ; Get all neighbors that are not on height 9
    neighbors (->>
               boundary
               (map #(set (gen-neighbors points %1)))
               (reduce union)
               (#(difference %1 basin))
               (select #(not= 9 (get-point points %1))))]
    (if (empty? neighbors) basin (recur points neighbors basin))))

(defn basin-for [points point]
  (basin-for-helper points #{point} #{}))

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
(ns aoc2021.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc2021.day03 :as SUT]))

(deftest parsing
  (testing "It returns a vector of vector of characters"
    (is (=
         [[1 0 1 1] [0 0 1 1]]
         (SUT/parse "1011\n0011")))))

(deftest transpose
  (testing "It transposes a vector of vectors"
    (is (=
         [[1 10 100] [2 20 200] [3 30 300]]
         (SUT/transpose [[1 2 3] [10 20 30] [100 200 300]])))))

(deftest most-commons
  (testing "It returns the most common elements of a sequence"
    (is (=
         #{2}
         (SUT/most-commons [1 2 2 3 4])))
    (is (=
         #{2 3}
         (SUT/most-commons [1 2 2 3 3 4])))))

(deftest most-common-per-digit
  (testing "It returns a vector with the most common digits of each column"
    (is (=
         [1 0 0 1]
         (SUT/most-common-per-digit [[1 0 0 1]])))
    (is (=
         [1 0 0 1]
         (SUT/most-common-per-digit
          [[1 1 1 1]
           [1 0 0 1]
           [0 0 0 0]])))
    (is (= [1] (SUT/most-common-per-digit [[1] [0]])))))

(deftest bit-array-to-number
  (testing "Returns the number represented by the received bits"
    (is (= 2 (SUT/bit-array-to-number [1 0])))
    (is (= 3 (SUT/bit-array-to-number [1 1])))
    (is (= 4 (SUT/bit-array-to-number [1 0 0])))))

(def aoc-example
  [[0 0 1 0 0]
   [1 1 1 1 0]
   [1 0 1 1 0]
   [1 0 1 1 1]
   [1 0 1 0 1]
   [0 1 1 1 1]
   [0 0 1 1 1]
   [1 1 1 0 0]
   [1 0 0 0 0]
   [1 1 0 0 1]
   [0 0 0 1 0]
   [0 1 0 1 0]])

(deftest oxygen-generator-rating
  (testing "Example from AoC"
    (is (= 23
           (SUT/oxygen-generator-rating aoc-example)))))

(deftest co2-scrubber-rating
  (testing "Example from AoC"
    (is (= 10
           (SUT/co2-scrubber-rating aoc-example)))))
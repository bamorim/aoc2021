(ns aoc2021.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc2021.day02 :as SUT]))

(deftest parsing
  (testing "A instruction is parsed properly"
    (is (=
         [:forward 1]
         (SUT/parse-instruction ["forward" "1"])))
    (is (=
         [:up 2]
         (SUT/parse-instruction ["up" "2"])))
    (is (=
         [:down 3]
         (SUT/parse-instruction ["down" "3"])))
    (is (thrown? AssertionError (SUT/parse-instruction ["something" "1"])))
    (is (thrown? AssertionError (SUT/parse-instruction ["up" "a"])))
    (is (thrown? AssertionError (SUT/parse-instruction ["down" "0"])))))

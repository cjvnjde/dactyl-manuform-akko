(ns dactyl-keyboard.dactyl
  (:refer-clojure :exclude [use import])
  (:require [clojure.core.matrix :refer [array matrix mmul]]
            [scad-clj.scad :refer :all]
            [scad-clj.model :refer :all]
            [unicode-math.core :refer :all]))


(def keyswitch-height 14.1) ;; Was 14.1, then 14.25
(def keyswitch-width 14.4)

(def plate-thickness 5)

(def nut-width 4.8)
(def nut-height 1.65)
(def nut-thickness (+ (- keyswitch-width 14) 0.2))

(def single-plate
  (let [top-wall (->> (cube (+ keyswitch-width 3) 1.5 plate-thickness)
                      (translate [0
                                  (+ (/ 1.5 2) (/ keyswitch-height 2))
                                  (/ plate-thickness 2)]))
        left-wall (->> (cube 1.5 (+ keyswitch-height 3) plate-thickness)
                       (translate [(+ (/ 1.5 2) (/ keyswitch-width 2))
                                   0
                                   (/ plate-thickness 2)]))
        side-nub (->> (binding [*fn* 30] (cube nut-thickness nut-width nut-height))
                      (translate [(+ (/ keyswitch-width 2)) 0 (- plate-thickness (- (/ nut-height 2) 0.4))])
                      )
        plate-half (union top-wall left-wall (with-fn 100 side-nub))]
    (union plate-half
           (->> plate-half
                (mirror [1 0 0])
                (mirror [0 1 0])))))

(spit "things/hole.scad"
      (write-scad single-plate))

(defn -main [dum] 1)  ; dummy to make it easier to batch

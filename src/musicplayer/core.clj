(ns musicplayer.core
  (:use [musicplayer.midi]))

(defn -main []
  (play-midi-string "CDEAFGCCCBBBBDDDDEEEE" 2 99))


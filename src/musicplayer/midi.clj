(ns musicplayer.midi
  (:use [musicplayer.utils]
        [Clojure.contrib.seq-utils :only (indexed)])
  (:import [javax.sound.midi MidiSystem Sequence ShortMessage MidiEvent]))

;Grammer - Only simple notes supported currently.
(def grammar #{\A \B \C \D \E \F \G})

;A Midi Event.
(defrecord Event
  [command channel data1 data2 tick])

;A Midi Track with events.
(defrecord Track
  [events])

;A Midi Sequence with tracks.
(defrecord MidiSequence
  [tracks ppq])

(defn get-events-from-notes
  "Returns a vector of events for the provided notes"
  [valid-notes velocity]
	(loop [i 0
         events []
         notes valid-notes]
    (if-not (empty? notes)
      (let [note (first notes)]
      	(recur (inc i)
               (assoc events i (record Event {:command ShortMessage/NOTE_ON :data1 (int note) :data2 velocity :tick i}))
               (rest notes)))
      events)))

(defn play-midi-sequence
  "Plays a Midi Sequence."
  [midi-sequence]
	(let [s (Sequence. Sequence/PPQ (:ppq midi-sequence))]
    (doseq [track (:tracks midi-sequence)]
      (let [t (.createTrack s)]
        (doseq [event (:events track)]
					(let [m (ShortMessage.)]
						(if (:data1 event)
              (.setMessage m (:command event) (:data1 event) (:data2 event)) ;Normal Notes
              (.setMessage m (:command event))) ;Stop Note
            (.add t (MidiEvent. m (:tick event)))))))
    (doto (MidiSystem/getSequencer)
      (.open)
      (.setSequence s)
      (.start))))

(defn play-midi-string
  "Plays a Midi String."
  [text ppq velocity]
  (let [valid-notes (filter #(grammar %) text)
        events (get-events-from-notes valid-notes velocity)
        stop-event (record Event {:command ShortMessage/STOP :tick (count valid-notes)})
        midi-sequence (record MidiSequence {:tracks [(Track. (assoc events (count events) stop-event))] :ppq ppq})]
    (play-midi-sequence midi-sequence)))


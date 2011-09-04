(ns musicplayer.midi
  (:use [musicplayer.utils]))

;A simple midi message that is equivalent to Java Midi's ShortMessage.
(defrecord Message
	[command data1 data2 channel])

;A Midi Event.
(defrecord Event
  [message tick])

;A Midi Track with events.
(defrecord Track
  [events])

;A Midi Sequence with tracks.
(defrecord Sequence
  [tracks])


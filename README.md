# Clojure Music Player

A very basic music player that plays simple notes. Java's Sound Midi API is used to synthesise the notes from a string.

## Usage

(use 'musicplayer.midi)
(play-midi-string "CDEAFGCCCBBBBDDDDEEEE" 2 99)

## More Information

The music player also defines clojure records that can be used instead of Java types - Event, Track and MidiSequence. 

;A Midi Event.
(defrecord Event
  [command channel data1 data2 tick])

;A Midi Track with events.
(defrecord Track
  [events])

;A Midi Sequence with tracks.
(defrecord MidiSequence
  [tracks ppq])

A function is available to play this hierarchy of data.

(use 'musicplayer.midi)
(play-midi-string (MidiSequence.))

## License

Copyright (C) 2011 Top Monkeys

Distributed under the Eclipse Public License, the same as Clojure.

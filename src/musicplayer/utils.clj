(ns musicplayer.utils)

(defmacro record 
  "Dynamic factory for defrecords. Source: http://groups.google.com/group/clojure/msg/5206fac13144ea99" 
  ([name] `(record ~name {}) ) 
  ([name vals-map] 
     `(let [con# (first (.getDeclaredConstructors ~name)) 
            num# (alength (.getParameterTypes con#))] 
        (merge (.newInstance con# (make-array Object num#)) ~vals-map)))) 



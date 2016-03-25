(ns steam-profile.client.history
  (:import [goog.history Html5History EventType])
  (:require [goog.events    :as events]
            [secretary.core :as secretary]))

(defn- get-token []
  (str js/window.location.pathname js/window.location.search))

(defn- make-history []
  (doto (Html5History.)
    (.setUseFragment false)
    (.setPathPrefix (str js/window.location.protocol "//" js/window.location.host))))

(defn start! []
  (doto (make-history)
    (events/listen EventType.NAVIGATE
      #(secretary/dispatch! (get-token)))
    (.setEnabled true)
    (.setToken (get-token))))

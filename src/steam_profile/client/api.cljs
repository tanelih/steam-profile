(ns steam-profile.client.api
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :as async :refer [<!]]))


(defn get-profile [name]
  (println "fetching player profile...")
  (go
    (let [res (<! (http/get (str "/api/profile/" name)))]
      (println
        "...fetched profile for"
        (get-in res [:body :realname])))))

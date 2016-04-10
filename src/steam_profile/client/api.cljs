(ns steam-profile.client.api
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :as async :refer [<! >!]]))


(defn- request [url]
  (let [out (async/chan)]
    (go
      (let [res (<! (http/get url))]
        (>! out (:body res))))
    out))

(defn load-profile     [name] (request (str "/api/profile/" name)))
(defn load-friend-list [name] (request (str "/api/profile/" name "/friends")))

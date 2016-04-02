(ns steam-profile.client.api
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :as async :refer [<! >!]]))


(defn load-profile [name]
  (let [out (async/chan)]
    (go
      (let [res (<! (http/get (str "/api/profile/" name)))]
        (>! out (:body res))))
    out))

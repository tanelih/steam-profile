(ns steam-profile.client.request
  (:require [cljs.core.async :as async :refer [<! >!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

;; note that this is practically a 'core.async' playground for now :-)

(def ok-response {:status 200})
(def nok-response {:status 400})

(defn- timeout [ms val]
  (let [out   (async/chan 1)
        timer (async/timeout ms)]
    (go
      (<! timer)
      (>! out val))
    out))

(defn- request [url body]
  (timeout 200 (assoc ok-response :url url :body body)))

;; someday these dummies hope to become real functions...

(defn- get-steam-id [vanity-url]
  (request (str "ResolveVanityURL/" vanity-url) {:steam-id 42}))

(defn- get-steam-profile [steam-id]
  (request (str "GetUserProfile/" steam-id) {:login "big-boss" :steam-id steam-id}))

(defn- get-steam-achievements [steam-id]
  (request (str "GetUserAchievementList/" steam-id) {:achievements []}))

;; woah

(defn- request-series [ops]
  (let [out (async/chan)]
    (go
      (loop [args       nil
             operations ops]
        (let [response     (<! ((first operations) args))
              response-ok? (and (>= (:status response) 200) (< (:status response) 400))]
          (if response-ok?
            (if (> (count (rest operations)) 0)
              (recur (:body response) (rest operations))))
          (>! out response))))
    out))

;; just something to refer from the 'core' namespace...

(defn do-thing []
  (println "doing thing...")
  (go
    (let [val (<! (timeout 1000 "super"))]
      (println val))))

(defn do-series []
  (println "doing series...")
  (go
    (let [response (<! (request-series [#(get-steam-id "vanity-url")
                                        #(get-steam-profile (:steam-id %))
                                        #(get-steam-achievements (:steam-id %))]))]
      (println response))))

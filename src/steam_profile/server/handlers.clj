(ns steam-profile.server.handlers
  (:require [environ.core :refer [env]]
            [cheshire.core :as json]
            [clojure.core.async :as async :refer [<!!]]
            [ring.util.response :refer [response resource-response]]
            [steam-profile.server.http :refer [request request-series]]))


(def steam-api-key (env :steam-api-key))


(defn- resolve-vanity-url [url]
  (request {:url "http://api.steampowered.com/ISteamUser/ResolveVanityURL/v0001"
            :query-params {:key steam-api-key :vanityurl url}}))

(defn- get-steam-profile [steam-id]
  (request {:url "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002"
            :query-params {:key steam-api-key :steamids steam-id}}))


(defn render-app []
  (resource-response "index.html" {:root "public"}))

(defn get-profile [name]
  (let [res (<!! (request-series [(fn [res]
                                    (resolve-vanity-url name))
                                  (fn [res]
                                    (let [data     (json/parse-string (:body res) true)
                                          steam-id (get-in data [:response :steamid])]
                                      (get-steam-profile steam-id)))]))]
    (response
      (first
        (get-in
          (json/parse-string (:body res) true) [:response :players])))))

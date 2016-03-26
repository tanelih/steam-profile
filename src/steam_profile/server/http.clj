(ns steam-profile.server.http
  (:require [org.httpkit.client :as http]
            [clojure.core.async :as async :refer [<!! >!! go go-loop]]))

(defn request [options]
  (let [out (async/chan)]
    (go
      (http/request options (fn [response] (>!! out response))))
    out))

(defn request-series [reqs]
  (let [out (async/chan)]
    (go-loop [resp     nil
              requests reqs]
      (let [response     (<!! ((first requests) resp))
            response-ok? (and (>= (:status response) 200) (< (:status response) 400))]
        (if (and response-ok? (> (count (rest requests)) 0))
          (recur response (rest requests))
          (>!! out response))))
    out))

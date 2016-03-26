(ns steam-profile.server.core
  (:gen-class)
  (:require [compojure.core :refer [GET context defroutes]]
            [environ.core :refer [env]]
            [org.httpkit.server :refer [run-server]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [steam-profile.server.handlers :as handlers]
            [ring.middleware.json :refer [wrap-json-response]]))


(def port (Integer/parseInt (env :port)))

(defroutes route-map
  (GET "/" [] (fn [req] (handlers/render-app)))
  (context "/api" []
    (GET "/profile/:name" [name] (fn [req] (handlers/get-profile name)))))

(def application
  (-> route-map (wrap-json-response) (wrap-resource "public") (wrap-file-info)))

(defn -main [] (run-server application {:port port}))

(ns steam-profile.server.core
  (:gen-class)
  (:require [compojure.core            :refer :all]
            [environ.core              :refer [env]]
            [org.httpkit.server        :refer [run-server]]
            [ring.util.response        :refer [redirect resource-response]]
            [ring.middleware.resource  :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]))


(defn parse-int [str] (Integer/parseInt str))

(def port (parse-int (env :port)))
(def steam-api-key (env :steam-api-key))


(defn render-app []
  (resource-response "index.html" {:root "public"}))

(defn get-profile [name] (println name))

(defroutes route-map
  (GET "/"              []     (render-app))
  (GET "/profile/:name" [name] (get-profile name)))


(def application
  (-> route-map (wrap-resource "public") (wrap-file-info)))

(defn -main [] (run-server application {:port port}))

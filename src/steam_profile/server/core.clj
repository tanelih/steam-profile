(ns steam-profile.server.core
  (:gen-class)
  (:require [compojure.core            :refer :all]
            [org.httpkit.server        :refer [run-server]]
            [ring.util.response        :refer [redirect resource-response]]
            [ring.middleware.resource  :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]))

(defn render-app []
  (resource-response "index.html" {:root "public"}))

(defroutes route-map
  (GET "/" [] (render-app)))

(def application
  (-> route-map (wrap-resource "public") (wrap-file-info)))

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8000"))]
    (run-server application {:port port})))

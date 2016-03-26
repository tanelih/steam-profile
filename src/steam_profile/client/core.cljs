(ns steam-profile.client.core
  (:import [steam-profile.client.views index])
  (:require [reagent.core                 :as reagent]
            [secretary.core               :as secretary :refer-macros [defroute]]
            [steam-profile.client.api     :as api]
            [steam-profile.client.history :as history]))

(enable-console-print!)

(defn get-profile [name]
  (api/get-profile name))

(defonce route-state-defaults {:view index/view :params {}})

(defonce route-state
  (reagent/atom route-state-defaults))

;; routes

(defroute "/" []
  (reset! route-state route-state-defaults))

(defn router []
  [(:view @route-state) (:params @route-state)])

(history/start!)
(reagent/render [router] (js/document.getElementById "app"))

(get-profile "oscopter")

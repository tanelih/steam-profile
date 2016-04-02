(ns steam-profile.client.core
  (:import [steam-profile.client.views index profile])
  (:require [reagent.core                 :as reagent]
            [re-frame.core                :as reframe]
            [secretary.core               :as secretary :refer-macros [defroute]]
            [steam-profile.client.api     :as api]
            [steam-profile.client.state   :as state]
            [steam-profile.client.history :as history]))

(enable-console-print!)

(defroute "/" []
  (reframe/dispatch [:set-route {:view index/view :params {}}]))

(defroute "/profile/:name" [name]
  (reframe/dispatch [:set-route {:view profile/view :params {:name name}}]))

(defn router []
  (let [route (reframe/subscribe [:route])]
    [(:view @route) (:params @route)]))

(defn ^:export run []
  (history/start!)
  (reframe/dispatch-sync [:initialize])
  (reagent/render [router] (js/document.getElementById "app")))

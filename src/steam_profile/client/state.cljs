(ns steam-profile.client.state
  (:import [steam-profile.client.views index])
  (:require [re-frame.core :as reframe]
            [cljs.core.async :refer [<!]]
            [steam-profile.client.api :refer [load-profile load-friend-list]])
  (:require-macros [reagent.ratom :refer [reaction]]
                   [cljs.core.async.macros :refer [go]]))



(def initial-state {:route {:view index/view
                            :params {}}
                    :profile {}
                    :friend-list []
                    :loading-profile? false
                    :loading-friend-list? false})

;; subscriptions

(reframe/register-sub
  :route
  (fn [state _]
    (reaction (:route @state))))

(reframe/register-sub
  :loading-profile
  (fn [state _]
    (reaction (:loading-profile? @state))))

(reframe/register-sub
  :loading-friend-list
  (fn [state _]
    (reaction (:loading-friend-list? @state))))

(reframe/register-sub
  :profile
  (fn [state _]
    (reaction (:profile @state))))

;; handlers

(reframe/register-handler
  :initialize
  (fn [state _]
    (merge state initial-state)))

(reframe/register-handler
  :set-route
  (fn [state [_ route]]
    (assoc state :route route)))

(reframe/register-handler
  :load-profile
  (fn [state [_ name]]
    (go
      (let [profile (<! (load-profile name))]
        (reframe/dispatch [:receive-profile profile])))
    (assoc state :loading-profile? true)))

(reframe/register-handler
  :load-friend-list
  (fn [state [_ name]]
    (go
      (let [friend-list (<! (load-friend-list name))]
        (reframe/dispatch [:receive-friend-list friend-list])))
    (assoc state :loading-friend-list? true)))


(reframe/register-handler
  :receive-profile
  (fn [state [_ profile]]
    (assoc state :profile profile :loading-profile? false)))

(reframe/register-handler
  :receive-friend-list
  (fn [state [_ friend-list]]
    (assoc state :friend-list friend-list :loading-friend-list? false)))

(ns steam-profile.client.views.profile
  (:require [reagent.core :as reagent]
            [re-frame.core :as reframe]
            [steam-profile.client.history :refer [navigate]]))


(defn view [params]
  (reframe/dispatch [:load-profile (:name params)])

  (let [profile (reframe/subscribe [:profile])]
    (fn []
      [:article])))

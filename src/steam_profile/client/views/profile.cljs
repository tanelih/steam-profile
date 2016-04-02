(ns steam-profile.client.views.profile
  (:require [reagent.core :as reagent]
            [re-frame.core :as reframe]
            [steam-profile.client.utils :as u]
            [steam-profile.client.history :refer [navigate]]))

(defn view [params]
  (reframe/dispatch [:load-profile (:name params)])
  (let [profile (reframe/subscribe [:profile])]
    (fn []
      (u/log @profile)
      [:article
        [:pre {:style {:color "white"}}
          (u/to-string @profile)]])))

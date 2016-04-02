(ns steam-profile.client.views.index
  (:require [reagent.core :as reagent]
            [steam-profile.client.history :refer [navigate]]))


(defn- to-profile [name]
  (fn [event]
    (.preventDefault event)
    (navigate (str "/profile/" @name))))

(defn view []
  (let [name (reagent/atom "")]
    (fn []
      [:article.view-search
        [:header
          [:h1 "Steam Profile"]]
        [:section
          [:form {:on-submit (to-profile name)}
            [:input {:value @name :on-change #(reset! name (-> % .-target .-value))}]
            [:button {:type "submit"}
              "Search"]]]
        [:footer
          [:p "Powered By Steam"]]])))

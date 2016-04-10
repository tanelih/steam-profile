(ns steam-profile.client.views.index
  (:require [reagent.core :as reagent]
            [steam-profile.client.history :refer [navigate]]))


(defn- go-to-profile [name]
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
          [:form {:on-submit (go-to-profile name)}
            [:label {:for "vanity-url"}]
            [:input {:name "vanity-url"
                     :value @name
                     :on-change #(reset! name (-> % .-target .-value))}]
            [:button {:type "submit"}
              "Search"]]]
        [:footer
          [:a {:href "https://steampowered.com"}
            "Powered By Steam"]]])))

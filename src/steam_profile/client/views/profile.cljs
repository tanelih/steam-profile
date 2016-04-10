(ns steam-profile.client.views.profile
  (:require [re-frame.core :as reframe]))


(defn view [params]
  (reframe/dispatch [:load-profile     (:name params)])
  (reframe/dispatch [:load-friend-list (:name params)])

  (let [profile             (reframe/subscribe [:profile])
        is-loading-profile? (reframe/subscribe [:loading-profile])]

    (fn []
      (if @is-loading-profile?
        [:article.view-loading]
        [:article.view-profile
          [:section.content
            [:section.profile
              [:section.avatar
                [:img {:src (:avatarfull @profile)}]]
              [:section.name
                [:h1 (:personaname @profile)]]]]]))))

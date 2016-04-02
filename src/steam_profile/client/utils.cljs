(ns steam-profile.client.utils)

(defn log [obj]
  (js/console.log (clj->js obj)))

(defn to-string [obj]
  (js/JSON.stringify (clj->js obj) nil 2))

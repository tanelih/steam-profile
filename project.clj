(defproject steam-profile "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.374"]
                 [org.clojure/clojurescript "1.8.34"]
                 [ring/ring-core "1.4.0"]
                 [environ "1.0.2"]
                 [cljs-http "0.1.39"]
                 [ring/ring-json "0.4.0"]
                 [compojure "1.4.0"]
                 [http-kit "2.1.19"]
                 [cheshire "5.5.0"]
                 [reagent "0.5.1"]
                 [re-frame "0.7.0"]
                 [secretary "1.2.3"]]

  :plugins [[lein-ring "0.9.7"]
            [lein-cljsbuild "1.1.2"]
            [lein-figwheel "0.5.0-6"]]

  :prep-tasks ["compile" ["cljsbuild" "once" "production"]]

  :main steam-profile.server.core
  :ring {:handler steam-profile.server.core/application}

  :source-paths ["src"]
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]
             :server-port 8000
             :ring-handler steam-profile.server.core/application}

  :cljsbuild {:builds [{:id "development"
                        :figwheel true
                        :source-paths ["src"]
                        :compiler {:main steam-profile.client.core
                                   :asset-path "js/compiled/out"
                                   :output-to "resources/public/js/compiled/steam-profile.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :source-map-timestamp true}}
                       {:id "production"
                        :source-paths ["src"]
                        :compiler {:main steam-profile.client.core
                                   :output-to "resources/public/js/compiled/steam-profile.js"
                                   :optimizations :advanced
                                   :pretty-print false}}]})

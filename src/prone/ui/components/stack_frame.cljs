(ns prone.ui.components.stack-frame
  (:require [cljs.core.async :refer [put!]]
            [prone.ui.utils :refer [action]]
            [quiescent :as q :include-macros true]
            [quiescent.dom :as d]))

(q/defcomponent StackFrame [frame select-frame type]
  (d/li {:className (when (:selected? frame) "selected")
         :onClick (action #(put! select-frame {:id (:id frame)
                                               :type type}))}
        (d/span {:className "stroke"}
                (d/span {:className (if (:application? frame)
                                      "icon application"
                                      "icon")})
                (d/div {:className "info"}
                       (if (= (:lang frame) :clj)
                         (d/div {:className "name"}
                                (d/strong {} (:package frame))
                                (d/span {:className "method"} "/" (:method-name frame)))
                         (d/div {:className "name"}
                                (d/strong {} (:package frame) "." (:class-name frame))
                                (d/span {:className "method"} "$" (:method-name frame))))
                       (if (:file-name frame)
                         (d/div {:className "location"}
                                (:loaded-from frame) " "
                                (d/span {:className "filename"}
                                        (:file-name frame))
                                ", line "
                                (d/span {:className "line"} (:line-number frame)))
                         (d/div {:className "location"}
                                "(unknown file)"))))))

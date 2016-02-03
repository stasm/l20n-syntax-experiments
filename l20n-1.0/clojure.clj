(ns l20n)
(require '[clojure.string :as str])

(entity brand "Firefox")
(entity brand-name
  ^{:gender :masculine}
  {:nominative "Firefox"
   :accusative "Firefoksie"})

(:nominative brand-name)
(brand-name :nominative)

(meta brand-name)

(defn trait [entity name]
  (get (meta entity) name))

(trait brand-name :gender)

; (def crashed [brand-name^gender]
;  {:masculine "{{ brand-name }} uległ awarii"
;   :feminine "{{ brand-name }} uległa awarii"})

; (defentity crashed [(trait brand-name :masculine)]
;   {:masculine "{{ brand-name }} uległ awarii"
;    :feminine "{{ brand-name }} uległa awarii"})

(deftype Entity [value]
  clojure.lang.IFn
  (invoke [this] (this [])))

(defmacro entity [m index hash] nil)
(entity brand-name
  ^{:gender :masculine}
  [:nominative]
  {:nominative "Firefox"
   :accusative "Firefoksie"})

(defmacro my-macro [arg] nil)
(my-macro $n)

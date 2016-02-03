(def ^:masculine foo 1)

(meta #'foo)


(= (deref (var foo)) foo)

user/foo

:foo

::foo

(println ::foo)

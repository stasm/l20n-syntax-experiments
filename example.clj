; principles
; ----------
;
; (and how this proposal achieves them)
;
; 0. keep this minimal
;    - no attributes
;    - no globals
;    - no 'computed' expression syntax (use a built-in macro)
;    - no 'this' expression
;    - no imports
;    - no local entities
;    - no implicit default variants
; 
; 1. translations should be hard to break
;    - good error recovery
;    - args have $ sigils
;    - variant names have : sigils
;    - variants are easily recognizable by being enclosed with {}
;    - only text between quotes should be localized (important for macros' 
;      return values; see at the end)
;
; 2. naming is up to the developer and the localizer
;    - allow for more glyphs in identifiers
;    - use S-exprs to simplify the expression syntax


; keep simple things simple
<brand-name "Firefox">

<brand-name [:nominative]
  {:nominative "Firefox"}
  {:accusative "Firefoksie"}>

<brand-name [:nominative]
  ; the first contiguous comment in the entity's body becomes the docstring
  ; @desc The name of the application
  ^gender "masculine"
  {:nominative "Firefox"}
  {:accusative "Firefoksie"}>

; no attributes, just conventions not enforced by syntax and used by bindings
; triple-quotes for multiline strings
; HTML is only recognized in triple-quoted strings (not sure about this)
<brand-name.title """
  Mozilla <em>Firefox</em>
""">

; accesskeys are part of the value token: each value can be followed by an 
; optional accesskey definition prefixed with &
<brand-name "Firefox" &F>

<settings [(sys/os)]
  {:windows "Options" &O}
  {:macos "Preferences" &P}
  {:linux "Settings" &S}>

; accessors use the same syntax as definitions
; variant access with :
<about-brand-name "O {{ brand-name:accusative }}">

; trait access with ^
<crashed [brand-name^gender]
  {:masculine "{{ brand-name }} uległ awarii"}
  {:feminine "{{ brand-name }} uległa awarii"}>

; built-in macros are always namespaced: cldr/*, intl/*, os/* etc.
<you-received [(cldr/plural $new-messages) $gender]
  ; @param $new-messages Number of new messages
  ; @param $gender The gender of the recepient
  {:one
    {:masculine "Otrzymałeś 1 nową wiadomość."}
    {:feminine "Otrzymałaś 1 nową wiadomość."}}
  {:few
    {:masculine "Otrzymałeś {{ $new-messages }} nowe wiadomości."}
    {:feminine "Otrzymałaś {{ $new-messages }} nowe wiadomości."}}
  {:many
    {:masculine "Otrzymałeś {{ $new-messages }} nowych wiadomości."}
    {:feminine "Otrzymałaś {{ $new-messages }} nowych wiadomości."}}>

; _-./ are allowed in identifiers
; namespaces are also convention
<songs-list/rating-star.aria-label [(cldr/plural $n)]
  {:one "{{ $n }} star"}
  {:many "{{ $n }} stars"}>

; in English
<space-available
  ; @param $num Number of units available
  ; @param $unit Name of the unit
  "{{ $num }}{{ $unit }} available">

; in French; see the fr/byte-unit macro below
<space-available
  ; @param $num Number of units available
  ; @param $unit Name of the unit
  [(cldr/plural $num)]
  {:one "{{ $num }}{{ (fr/byte-unit $unit) }} disponible"}
  {:many "{{ $num }}{{ (fr/byte-unit $unit) }} disponibles"}>

; custom macros are defined similar to LISP functions
; the following symbols are supported for now:
;   entity variant defmacro cond
;   not and or + - * / mod max min = <> < > <= >=
; reserved for future use:
;   import true false
(defmacro fr/byte-unit [unit]
  (cond
    (= unit "B") "o"
    (= unit "KB") "Ko"
    (= unit "MB") "Mo"
    (= unit "GB") "Go"
    :else "Oops"))

; macros can return variant names
; variant names evaluate to themselves (and are truthy) so we can use :else for 
; the last condition in cond (as a convention)
(defmacro my-plural [x]
  (cond
    (= x 1) :one
    :else :many))

; macros can only be called with (name …); they cannot be referenced 
; nor passed around
<notification-count [(my-plural $n)]
  :one "{{ $n }} notification"
  :many "{{ $n }} notifications">

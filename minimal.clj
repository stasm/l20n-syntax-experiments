<brand-name
  ^gender "masculine"
  :nominative "Firefox"
  :accusative "Firefoksie"
  .command-key "F">

; variant access
<about "O {{ brand-name:accusative }}">

; trait access
<crashed [brand-name^gender]
  :masculine "{{ brand-name }} uległ awarii"
  :feminine "{{ brand-name }} uległa awarii">

; attribute access
<press-command-key "Press {{ brand-name.command-key }}.">

; nested variants are all spelled-out
; built-in macros are always namespaced: cldr/*, intl/*, os/* etc.
; macros can only be called with (name …), not referenced nor passed
<you-received [(cldr/plural $new-messages) $gender]
  :one:masculine "Otrzymałeś 1 nową wiadomość."
  :one:feminine "Otrzymałaś 1 nową wiadomość."
  :few:masculine "Otrzymałeś {{ $new-messages }} nowe wiadomości."
  :few:feminine "Otrzymałaś {{ $new-messages }} nowe wiadomości."
  :many:masculine "Otrzymałeś {{ $new-messages }} nowych wiadomości."
  :many:feminine "Otrzymałeś {{ $new-messages }} nowych wiadomości.">

; multivariant attributes
<rating-star [(cldr/plural $n)]
  .aria-label:one "{{ $n }} star"
  .aria-label:many "{{ $n }} stars">

; custom macros in the future
(macro my-plural [x]
  (cond
    (= x 1) "one"
    &true "many"))

<notification-count [(my-plural $n)]
  :one "{{ $n }} notification"
  :many "{{ $n }} notifications">

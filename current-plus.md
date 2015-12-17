#A propsal to change the current syntax

This is a proposal to make small changes to the current l20n syntax in order to
fulfill most of the goals outlined in the readme.md file.


##1. Change the placeable syntax to single braces

Single braces are rarely used in verbatim meaning.  It's also easier to escape
them unambiguously if needed.

Before:

    <about "About {{ brandName }}">
    <braces "Use \{{ and }} for string interpolation">

After:

    <about "About { brandName }">
    <braces "Use \{ and \} for string interpolation">


##2. Allow for dashes and slashes in identifiers

Dashes, slashes and dots can be used to build hierarchies in string ids.  Let's
strike a good balance by being more liberal syntacticly but encouraging strong
conventions:

  - / is for namespaces,
  - - is for separating words in identifiers,
  - . is for attributes.

Before:

    <brandName "Firefox">

After:

    <brand-name "Firefox">


##3. Allow for dashes and slashes in argument names

This is for consistency with the previous change.  It's optional but allowed.

Before:

    <connected "You are connected to {{ $wifiName }}">

After:

    <connected "You are connected to { $wifi-name }">


##4. Introduce a new data type: keywords

Keywords are just like strings but don't require quotes because they must
follow the syntax of identifiers.  They make it easy to distinguish between
user-facing strings that need to be localized and strings which are used for
logic.

Keywords can be used anywhere where strings can.  Additionally they are
required when defining keys of hashes and as values of traits.

Before:

    { one: "1 new message.",
      other: "{{ $n }} new messages."}

After:

    { :one "1 new message."
      :other "{ $n } new messages."}

##5. Introduce a new data type: traits

Traits are private attributes for entities describing their grammatical
characteristics like gender, animacy etc.  They are only available to other
entities and can't be queried by the developer.  Their values can only be
keywords to discourage storing too much content in them.

Before:

    <brand-name "Firefox"
      _gender: "masculine">

After:

    <brand-name "Firefox"
      ^gender :masculine>


##6. Simplify the key-value pair syntax

It's inconsistent that attribute maps in the old syntax don't require commas
but hash maps do.  Since keys are always special symbols (keywords or traits)
and the number of elements in the hash must be even, we can simplify and unify
the syntax by removing the colon following the key and making commas optional.  

Before:

    <brandName
      {nominative: "Firefox",
       accusative: "Firefoksie"}
      _gender: "masculine"
      _animacy: "animate">

After:

    <brand-name
      ^gender :masculine
      ^animacy :animate
      {:nominative "Firefox"
       :accusative "Firefoksie"}>


##7. Unify the accessor syntax with the definitions

Ensure consistency and instant feedback of the accessor syntax by using the
same characters as the keyword and trait sigils.

Before:

    brandName.nominative
    brandName::_gender

After:

    brand-name:nominative
    brand-name^gender


Alternative #1:  Provide built-in macros for accessing variants and traits:

    (variant brand-name :nominative)
    (trait brand-name ^gender)

Alternative #2:  Make keywords and traits callable:

    (:nominative brand-name)
    (^gender brand-name)

Alternative #3:  Make entities callable and check type of the argument:

    (brand-name :nominative)
    (brand-name ^gender)


##8. Remove the syntax for default variants

The default variant syntax for hashes is useful but can be added later on.  For
now I suggest we make explicit indexes required.  Most of the entities already
have indexes and it's rare for an entity to not need one.  A notable exception
is the branding.  I admit it's suboptimal but I'm suggesting this for the sake
of keeping this proposal minimal.

Before:

    <brandName {
     *nominative: "Firefox",
      accusative: "Firefoksie"
    }>

After:

    <brand-name [:nominative]
      {:nominative "Firefox"
       :accusative "Firefoksie"}>


##9. Expressions are LISP forms

In order to allow for dashes and slashes in identifiers I suggest we use
S-expressions for our expression syntax.  Every operator becomes a function so
essentially every expression is a call expression reducing the complexity of
the expression syntax significantly.

To keep things simple, macro identifiers follow the same rules as entity
identifiers.  They can't have `<` nor `>` in them which limited my choice of
names for the less-than and greater-than functions. I opted in for verbs for
arithmetic operations which had the added benefit of making it possible to read
the expression out in a natural manner: `(add 1 2)` reads "add 1 to 2" and to
use the question mark in the names of predicate functions: `(eq? 1 2)` reads
"are 1 and 2 equal?."

The following symbols are supported for now:

    entity variant trait defmacro
    cond not and or
    add sub mul div mod max min inv
    eq? neq? lt? gt? lte? gte?
    cldr/plural

Symbols reserved for future use:

    import
    true false
    intl/datetime intl/date intl/time


Before:

    plural($n)
    byteUnit($unit)
    1 + 2
    $hour < 18
    isEven($n)

After:

    (plural $n)
    (byte-unit $unit)
    (add 1 2)
    (lt? $hour 18)
    (even? $n)


##10. Remove globals

Use namespaced built-in macros to provide the functionality of globals and
remove an unnecessary data type.  Macros can only be called and never
referenced and passed around so there's no risk of name collisions with
entities.

Before:

    @cldr.plural($n)
    @screen.width.px
    @os

After:

    (cldr/plural $n)
    (screen/width-px)
    (sys/os)


##11. Custom macros are also LISP forms

Unify the syntax for defining macros with the proposed expression syntax.  
Custom macros must not be namespaced (checked on runtime).  Macros can return
strings or keywords.

Before:

    <byteUnit($unit) {
      $unit == "B" ? "o" :
      $unit == "KB" ? "Ko" :
      $unit == "MB" ? "Mo" :
      $unit == "GB" ? "Go" :
      "Oops"
    }>

    <myPlural($x) {
      $x == 1 ? "one" : "many"
    }>

After:

    (defmacro byte-unit [unit]
      (cond
        (eq? unit "B") "o"
        (eq? unit "KB") "Ko"
        (eq? unit "MB") "Mo"
        (eq? unit "GB") "Go"
        :else "Oops"))

    (defmacro my-plural [x]
      (cond
        (eq? x 1) :one
        :else :many))


##12. Introduce docstrings

(Optional) The first contiguous comment in the body of the entity becomes its
docstring.  This helps authoring and refactoring tools better understand the
semantics of the entity and also keeps everything in one place when the entity
is moved in the file of between files.

    <brand-name
      ; @desc The name of the application
      "Firefox">

    <space-available
      ; @param $num number The number of units available
      ; @param $unit string The name of the unit
      "{ $num }{ $unit } available">


##13.  Only allow for double-quotes around strings

Reduce the complexity and make files written by different people more similar
to each other.

Before:

    <brandName 'Firefox'>
    <brandFullName '''
      Mozilla <em title="Firefox">Firefox</em>
    '''

After:

    <brand-name "Firefox">
    <brand-full-name """
      Mozilla <em title="Firefox">Firefox</em>
    """>

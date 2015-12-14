principles
----------

(and how to achieve them)

0. keep this minimal
   - no attributes
   - no globals
   - no 'computed' expression syntax (use a built-in macro)
   - no 'this' expression
   - no imports
   - no local entities
   - no implicit default variants

1. translations should be hard to break
   - good error recovery
   - sigils for args
   - sigils for variants
   - separate strings to be translated (surrounded with quotes) from strings 
     used programmatically (:keywords)

2. naming is up to the developer and the localizer
   - allow for more glyphs in identifiers

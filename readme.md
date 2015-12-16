principles
----------

(and how to achieve them with the new syntax)

1. keep it minimal
   - no attributes
   - no globals
   - no 'computed' expression syntax (use a built-in macro)
   - no 'this' expression
   - no imports
   - no local entities
   - no implicit default variants

2. keep it simple
   - don't reinvent the wheel
   - no surprises
   - consistent
   - keep special syntax to minimum

3. translations should be hard to break
   - good error recovery
   - sigils for args
   - sigils for variants
   - separate strings to be translated (surrounded with quotes) from strings 
     used programmatically (:keywords)

4. naming is up to the developer and the localizer
   - allow for more glyphs in identifiers

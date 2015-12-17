principles
----------

(and how to achieve them with the new syntax)

1. keep it minimal
   - no attributes
   - no globals
   - no 'computed' expression syntax (use a built-in macro)
   - no 'this' expression
   - no local entities (only custom macros)
   - no imports (for now)
   - no implicit default variants (for now)

2. keep it simple
   - don't reinvent the wheel
   - no surprises
   - consistent
   - keep special syntax to minimum

3. translations should be hard to break
   - good error recovery
   - sigils for args
   - sigils for variants
   - sigils for traits
   - separate strings to be translated (surrounded with quotes) from strings 
     used programmatically (:keywords)

4. naming is up to the developer and the localizer
   - allow for more glyphs in identifiers
   - namespaces are convention

FTL (FormatToLanguage)
======================

This proposal aims at solving ICU MessageFormat's limitations.

ICU MessageFormat concerns itself uniquely with the value of the message.  In 
a resource file (here: a .properties file) MessageFormat is the part after the 
equals sign.

    hello-world = Hello World
    -----+-----   -----+-----
         \             \
          message id    MessageFormat pattern

Because of this ICU MessageFormat suffers from the double-pass parsing problem: 
the `\` escape sequence is inherent to the resource bundle and can be used to 
introduce new lines:

    files-in-folder = There {NUM, plural, \
        =0 {are no files} \
        =1 {is one file} \
        other {are {NUM,plural} files }} in {FOLDER}. \

ICU MessageFormat doesn't know about these newlines because they're escaped in 
the bundle.  It uses it's own escape sequence, the quote pair ([source][1]):

    Within a String, a pair of single quotes can be used to quote any arbitrary 
    characters except single quotes. For example, pattern string `"'{0}'"` 
    represents string `"{0}"`, not a `FormatElement`. A single quote itself 
    must be represented by doubled single quotes `''` throughout a String. For 
    example, pattern string `"'{''}'"` is interpreted as a sequence of `'{` 
    (start of quoting and a left curly brace), `''` (a single quote), and `}'` 
    (a right curly brace and end of quoting), not `'{'` and `'}'` (quoted left 
    and right curly braces): representing string `"{'}"`, not `"{}"`. 

[1]: https://docs.oracle.com/javase/8/docs/api/java/text/MessageFormat.html#patterns))

This has been improved in ICU 4.8 ([source][2]):

    ICU implements a more user-friendly apostrophe quoting syntax. In message 
    text, an apostrophe only begins quoting literal text if it immediately 
    precedes a syntax character (mostly {curly braces}).
    In the JDK MessageFormat, an apostrophe always begins quoting, which 
    requires common text like `don't` and `aujourd'hui` to be written with 
    doubled apostrophes like `don''t` and `aujourd''hui`.

[2]: http://icu-project.org/apiref/icu4j/com/ibm/icu/text/MessageFormat.html#diffsjdk

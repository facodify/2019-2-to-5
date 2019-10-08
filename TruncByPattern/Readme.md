# TruncByPattern
String manipulation to obtain a smaller chunk of a larger string.  This uses a list of regular expression patterns to identify the earliest position to truncate the first chunk; something like String split() method but with regular expressions.

This is interesting due to the map() and reduce() methods in JavaScript that makes the above solution much simpler.
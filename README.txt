README.txt
Author: Michael Higgins
Email: mhiggins13@georgefox.edu

1. This repository contains RandomWriter.java as well as kjv.txt.
2. RandomWriter is intended to be used in a command-line environment where the call:

Michael$ javac RandomWriter.java // Compile the Java file.
Michael$ java RandomWriter 10 250 kjv.txt // Run the Java file.
er out of heaven and earth, dwelleth in Israel in the days that ye shall know me, when I call to remembrance; 1:14 Knowing this, that no strange incense altar, and came to
Shiloh.

3. The purpose of this program was to become more familiar with data processing and hash maps, and
of course, to have a little fun. Try using something like Lord of The Rings with kjv.txt, the results
are quite entertaining.
4. The larger the seed is, the more reasonable your output will be 
(i.e. there are fewer possibilities the longer your starting string is).
5. Note that the overhead is almost exactly the same no matter how large your output is.
This is because Java must completely process each file argument provided each run, 
regardless if the seed size changed from the last time or not.
6. This program will catch missing argument exceptions and inform the user of the error that occurred.

Documentation from the Javadoc:
**********************************
Program that provides a random writing application based upon an integer
passed as an argument in args indicating the length of the seed to begin.

Once a seed is chosen, the program will write out a random character from
the input that may follow the given seed. The character will then be
appended to the seed and the first character of the seed will be dropped.
The above will be repeated until the length of the characters is equal to
the integer value of the second argument in args.
**********************************


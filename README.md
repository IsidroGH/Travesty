Travesty
========

Java implementation of the Travesty algorithm using a probability tree structucture.

Travesty is a method for generating scrambled text using Markov chains.

This is a free interpretation of the Travesty algorithm by Hugh Kenner and Joseph O'Rourke discussed in BYTE November 1984 (http://www.scribd.com/doc/99613420/Travesty-in-Byte) based on the paper "Richard A. O’Keefe - An introduction to Hidden Markov Models".

As this paper (www.cs.otago.ac.nz/cosc348/hmm/hmm.pdf) says:

"A kth-order travesty generator keeps a “left context” of k symbols. 

Here k = 3, one context is “fro”. At each step, we find all the
places in the text that have the same left context, pick one of them at random,
emit the character we find there, and shift the context one place to the left. For
example, the text contains “(fro)m”, so we emit “m” and shift the context to
“rom”. The text contains “p(rom)ise”, so we emit “i” and shift the context to
“omi”. The text contains “n(omi)nation”, so we emit “n” and shift the context
to “min”. The text contains “(min)e”, so we emit “e” and shift the context to
“ine”. And so we end up with “fromine”.

How is this a Markov chain? The states are (k + 1)-tuples of characters,
only those substrings that actually occur in our training text. By looking at the
output we can see what each state was. There is a transition from state s to
state t if and only if the last k symbols of s are the same as the first k symbols
of t, and the probability is proportional to the number of times t occurs in the
training text.

A Travesty generator can never generate any (local) combination it has not
seen; it cannot generalise"

Usage
========

Travesty file k out-length

where:

  file is the file to scramble

  k is the number of symbols of the left context

  out-length is the number of cahracters to generate


  

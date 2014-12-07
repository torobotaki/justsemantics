## What is this?

It's a small tool written in Java which uses the vectors calculated by either word2vec[1] or GloVe[2] in order to produce word clusters.
It is really simplistic and not at all optimized, but maybe somoene else will find it useful.


## Input

The vectors in binary and for GloVe the vocabulary file in text.


## Output

For now it doesn't write the clusters, but prints them. You can use this with a shell redirection. 


## Libraries

In this version I have used Weka's K-means implementation, so you will need a to get 3.7.5 and put its jar in lib/
You will also need apache commons cli version 1.2.


## How to use

use the -h command line option once you've built it, or just look at the code, the command line options are fairly straight-forward

(To be improved)


## Licence

Apache 2.0 should cover this


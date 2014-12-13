# just semantics : a helper for word embedding vectors

Disclaimer: This project is really really very early and you should only use it
if you are as desperate as I was when looking for something similar

## What it does

It reads the vector files produced by either word2vec or GloVe and then
clusters them using weka's kmeans.


## Installation

For now weka is a dependency. But in any case I am providing the source code,
and I trust you will know what to do. I've used weka 3.7.5 and apache-commons
cli 1.2 when writing this. You should probably do the same.


## Usage

There is some well-intendend help options included in the source. They might
help. 


## Input/Output

For word2vec you will just need the  *binary* vector file and a number of
clusters (k for kMeans)

For GloVe you will need the *binary* vector file, the vocab file in txt format
and the number of clusters (k for kMeans)

It currently writes no output files, but will print out the clusters. 


## Contributing

In the extremely improbable  but very welcome chance...

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## History

I wrote this because I was frustrated with how difficult it was to find
documentation about using the vector (binary) files. It might just be me who
felt that way, but maybe it will help someone. 

## Credits

This was created in the context of my (Dialekti VALSAMOU) PhD work. I am affiliated to two
laboratories:
* MIG - INRA
* LIMSI - CNRS

## License

Apache 2.0

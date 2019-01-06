# Multithreaded Cosine Distance Computer

A program to compare a query file against a subject file by computing the cosine distance between them.

### Running

```
 java –cp ./oop.jar ie.gmit.sw.Runner
```

## Authors

* **Ethan Horrigan** - - [Ethan Horrigan](https://github.com/ethanhorrigan)

## Credits

* https://blog.nishtahir.com/2015/09/19/fuzzy-string-matching-using-cosine-similarity/
* https://www.draw.io

### Problems

At first, I made shingles by converting the StringBuilder to hashcodes, I did not find hashCodes easy to work with when debugging the program so I opted for Strings instead.

When taking the shingles and adding into the ConcurrentMaps I found that not all Shingles were being processed, but when I tested the Parser they were being adding to the queue perfectly.

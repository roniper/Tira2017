# Tira2017

An assignment for the course Tietorakenteet (=Datastructures) in 2017/2018

Held in University of Tampere

(Due to this all of the comments in the code are in Finnish)


## The program

The program reads integers from two separate files and provides three output files in a manner of set operations OR, AND and XOR.
The main purpose of the assignment was to make an own implementation of hash table, and not use the hash table that comes with Java.
Also all of the provided output files have their own extra information about the concerned operation.

Output files have the following:

or.txt
  - First column contains each unique integer value resulted of logical operation OR
  - Second column contains information how many times an integer appears in input files
  
and.txt
  - First column contains each unique integer value resulted of logical operation AND
  - Second column contains the row number of where the integer first appeared in the first given file
  
xor.txt
  - First column contains each unique integer value resulted of logical operation XOR
  - Second column contains number 1 or 2 according to which of the files (first or second) contain the integer

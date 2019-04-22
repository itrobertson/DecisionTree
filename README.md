DecisionTree
============

A Decision Tree implementation of ID3 used in the textbook Artificial Intelligence: A Modern Approach (3rd Edition); with features of C4.5.

UPDATE:

This DecisionTree repo was written in grad school Computer Science for DecisionTrees, it is written entirely in Java and parses Census Data (csv deliminated). Having been written over 5 years ago, there are some things I would do differently, to include design, naming conventions, style and methodology; however, most of those chages would not effect the majority of the underlying business logic of the algorithms themselves. For instance, a DecisionTree like ALL supervised learning incorporates the use of what's known as a "Depth-First Search" algorithm because w/DFS the target value (i.e. end-state) is KNOWN; thus, requiring a recursive traversal of the dataset(s). However, to a person doing a code review w/o the use of good reference would be forced to spend unnecessary time trying to connect the dots of the DFS critical functionality. I recognize this fact and understand the vital importance of communcation of software development in an operational setting with other developers.

Please note that this was intended only as research of an optimum DecisionTree and is a mixture of several early papers, comments were not needed at the time because it was between my professor adviser and me and my notes were kept in documentation form due to the academic nature of this project.

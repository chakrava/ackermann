ackermann
=========
#####A program to perform the Ackermann function and provide analysis.
 
A Java program to calculate the results of the Ackermann function and the number of recursive calls to reach the solution.

This also has the framework for using memoization to save already calculated Ackermann values in a 2D array: smartAck(m,n). The memoization is enabled if the global int SIZE is > 0.
 
There is a 2D array ackMem that holds the actual solutions. Any m's or n's outside of SIZE are handled by the normal ack() method.
 
I'm able to set a println() whenever the smartAck returns an already calculated value to see smartAck action.
 
The memoization works but the runtime is not noticeably improved vs normal recursion, in fact it's often worse. Part of the issue is in overhead, I was originally using try/catch instead of range checks to make sure m and n were within the 2D array. These raised my runtime from ~10 seconds without memoization up to ~23 with. Replacing the try/catch with range checks dropped the runtime to ~13 seconds. When I removed the second array (to try and track recursive calls) the runtime actually beat the non-memoized time.
 
Once I removed the second 2D array (to store call counts) I actually managed to get the runtime of the memoized version to run in 2-3 seconds with a SIZE of 10000. I had to run these tests with m and n values that wouldn't cause stack overflows since the presence of the 2D array in memory would speed the overflow and cause falsely short runtimes.
 
Also...
 
I wasn't able to accurately calculate the total calls when loading data from the memoization array, which ruined the whole point of using the program to count calls to help analyze the function.

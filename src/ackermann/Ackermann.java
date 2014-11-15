/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ackermann;

import java.math.BigInteger;

/**
 *
 * @author Erik Storla
 *
 * A Java program to calculate the results of the Ackermann function and the
 * number of recursive calls to reach the solution.
 *
 * This also has the framework for using memoization to save already calculated
 * Ackermann values in a 2D array: smartAck(m,n). The memoization is enabled if
 * the global int SIZE is > 0.
 *
 * There is a 2D array ackMem that holds the actual solutions. Any m's or n's
 * outside of SIZE are handled by the normal ack() method.
 *
 * I'm able to set a println() whenever the smartAck returns an already
 * calculated value to see smartAck action.
 *
 * The memoization works but the runtime is not noticeably improved vs normal
 * recursion, in fact it's often worse. Part of the issue is in overhead, I was
 * originally using try/catch instead of range checks to make sure m and n were
 * within the 2D array. These raised my runtime from ~10 seconds without memoi
 * up to ~23 with. Replacing the try/catch with range checks dropped the runtime
 * to ~13 seconds. When I removed the second array (to try and track recursive
 * calls) the runtime actually beat the non-memoized time.
 *
 * Once I removed the second 2D array (to store call counts) I actually managed
 * to get the runtime of the memoized version to run in 2-3 seconds with a SIZE
 * of 10000. I had to run these tests with m and n values that wouldn't cause
 * stack overflows since the presence of the 2D array in memory would speed the
 * overflow and cause falsely short runtimes.
 *
 * Also...
 *
 * I wasn't able to accurately calculate the total calls when loading data from
 * the memoization array, which ruined the whole point of using the program to
 * count calls to help analyze the function.
 */
public class Ackermann {

    final static BigInteger one = BigInteger.ONE;
    final static BigInteger zero = BigInteger.ZERO;
    static long c;
    static long dc;

    static int SIZE = 10000;
    static BigInteger[][] ackMem = new BigInteger[SIZE][SIZE];
    //static long[][] ackCalls = new long[SIZE][SIZE];

    public static void main(String[] args) {
        System.out.println("ack(m, n) = [RETURN]\t [# of calls to reach answer] ([single recursive calls] [double recursive calls])\n");
        if (SIZE > 0) {
            System.out.println("Memoization enabled! " + SIZE + "x" + SIZE + "");
        }

        for (int m = 0; m <= 4; m++) {
            for (int n = 0; n <= 10; n++) {
                try {
                    c = 1;
                    dc = 0;
                    //System.out.println("ack(" + m + ", " + n + ") = " + smartAck(m, n) + "\t" + (c + 2 * dc) + " " + ackCalls[m][n] + " calls (" + c + ", " + dc + ")");

                    if (SIZE > 0) {
                        ackMem[m][n] = smartAck(m, n);
                        //ackCalls[m][n] = c + 2 * dc;
                        System.out.println("ack(" + m + ", " + n + ") = " + ackMem[m][n] + "\t");// + (c + 2 * dc) + " " + ackCalls[m][n] + " calls (" + c + ", " + dc + ")");
                    } else {
                        System.out.println("ack(" + m + ", " + n + ") = " + ack(m, n) + "\t" + (c + 2 * dc) + " " + "calls (" + c + ", " + dc + ")");
                    }
                } catch (StackOverflowError e) {
                    System.out.println("ack(" + m + ", " + n + ") = STACK OVERFLOW at " + (c + 2 * dc) + " calls (" + c + ", " + dc + ")");
                }
            }
        }
    }

    public static BigInteger smartAck(Object i1, Object i2) {
        BigInteger m, n;

        if (i1.getClass() == Integer.class) {
            m = BigInteger.valueOf((int) i1);
        } else {
            m = (BigInteger) i1;
        }
        if (i2.getClass() == Integer.class) {
            n = BigInteger.valueOf((int) i2);
        } else {
            n = (BigInteger) i2;
        }

//        try {
        if (m.intValue() < SIZE && n.intValue() < SIZE) {
            if ((ackMem[m.intValue()][n.intValue()] != null)) {
                //Uncomment me to see whenever a memoized value is returned
                //System.out.println("SMART " + m + ", " + n);

                //c += ackCalls[m.intValue()][n.intValue()];
                return ackMem[m.intValue()][n.intValue()];
            }
        }

        BigInteger temp = ack(m, n);
        if (m.intValue() < SIZE && n.intValue() < SIZE) {
            ackMem[m.intValue()][n.intValue()] = temp;
            //ackCalls[m.intValue()][n.intValue()] = (c + (long) 2 * dc);
        }

        return temp;
    }

    public static BigInteger ack(Object i1, Object i2) {
        BigInteger m, n;

        if (i1.getClass() == Integer.class) {
            m = BigInteger.valueOf((int) i1);
        } else {
            m = (BigInteger) i1;
        }
        if (i2.getClass() == Integer.class) {
            n = BigInteger.valueOf((int) i2);
        } else {
            n = (BigInteger) i2;
        }

        if (m.compareTo(zero) == 0) {
            return n.add(one);
        }

        if (m.compareTo(zero) > 0 && n.compareTo(zero) == 0) {
            c++;
            if (SIZE > 0) {
                return smartAck(m.subtract(one), one);
            }
            return ack(m.subtract(one), one);

        }
        dc++;
        if (SIZE > 0) {
            return smartAck(m.subtract(one), ack(m, n.subtract(one)));
        }
        return ack(m.subtract(one), ack(m, n.subtract(one)));

    }

}

package com.kph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameArray {

    static boolean[] result;
    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Gameelem> gamearry = new ArrayList<>();
        int n=0;
        try {
            n = Integer.parseInt(br.readLine());
            result = new boolean[n];
            for (int i = 0; i < n; i++) {
                String[] strsz = br.readLine().replaceAll("\\s+$", "").split(" ");
                Gameelem gameelem = new Gameelem(
                        i,
                        Integer.parseInt(strsz[0]),
                        Integer.parseInt(strsz[1]),
                        Arrays.stream(br.readLine().trim().split(" "))
                                .mapToInt(Integer::parseInt).toArray());
                gamearry.add(gameelem);
            }
            // iterate each keepd the order
            System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");
            gamearry.stream()
                    .parallel()
                    .forEach(GameArray::gameWin);

            for (boolean k: result) {
                if ( k == true)
                    System.out.println("YES");
                else
                    System.out.println("NO");
            }

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    public static void gameWin(Gameelem gameelem) {
        // read in Stdin two lines, e.g.
        // "9 4",  // [num of elements] [leap value]
        // "0 1 1 0 0 1 1 0 1"  // array of [num of elements]

        int[] gamearry = gameelem.gamearry;
        int gameSz = gameelem.size;
        int leap = gameelem.leap;
        int winln = gameSz;

        int j = 0;
        //System.out.printf("sz: %d, leap: %d\n", gameSz, leap);
        while (j + leap < winln) {
            if (gamearry[j + leap] == 0) { // jump first
                j += leap;
            } else { // step to next
                if (gamearry[j + 1] == 0) {
                    j += 1;
                } else { // back 1, then jump and check
                    //boolean flg = false;
                    if (j > 0 && gamearry[j - 1] == 0 &&
                            gamearry[j - 1 + leap] == 0) {
                        j = j - 1 + leap; // continue
                        //flg = true;
                        //break;
                    } else {// not found, return NO
                        //gameover = true;// no need to continue
                        break;
                    }
                }
            }
        }
        if (j >= winln || j + leap >= winln) {
            result[gameelem.index] = true;
        } else {
            result[gameelem.index] = false;
        }

    }
}

class Gameelem {
    public int index;
    public int size;
    public int leap;
    public int[] gamearry;

    public Gameelem(int idx, int a, int b, int[] c) {
        index = idx;
        size = a;
        leap = b;
        gamearry = c;
    }
}

/**** generated by ChatGPT
 * boolean isGameOver = IntStream.iterate(j, index -> index + leap)
 *                         .limit(winln)
 *                         .anyMatch(index -> {
 *                             if (gamearry[index] == 0) {
 *                                 j = index;
 *                                 return false; // continue iterating
 *                             } else if (gamearry[index + 1] == 0) {
 *                                 j = index + 1;
 *                                 return false; // continue iterating
 *                             } else {
 *                                 int finalIndex = index;
 *                                 boolean isJumpFound = IntStream.iterate(finalIndex - 1, i -> i >= 0, i -> i - 1)
 *                                         .anyMatch(i -> {
 *                                             if (gamearry[i] == 0 && gamearry[i + leap] == 0) {
 *                                                 j = i + leap;
 *                                                 return true; // jump found, break the loop
 *                                             } else {
 *                                                 j = i; // back one more
 *                                                 return false; // continue iterating
 *                                             }
 *                                         });
 *
 *                                 if (!isJumpFound) {
 *                                     return true; // game over, break the loop
 *                                 } else {
 *                                     return false; // continue iterating
 *                                 }
 *                             }
 *                         });
 */



/*******
 String[] inpstr = new String[]{
 //"1",    // num of input array
 //"9 4",  // [num of elements] [leap value]
 //"0 1 1 0 0 1 1 0 1"  // array of [num of elements]
1
9 4
0 1 1 0 0 1 1 0 1
//--------------------
5
11 5
0 1 1 1 0 0 0 0 0 0 1
11 5
0 1 1 1 0 0 1 1 1 0 1
11 5
0 1 1 1 1 0 1 1 1 0 1
15 7
0 1 1 1 1 0 0 0 1 1 1 1 0 1 1
15 7
0 1 1 1 1 0 1 0 1 1 1 1 0 1 1
//--------------------
9
9 41
0 0 0 0 0 1 0 1 0
13 8
0 1 1 0 0 1 0 0 0 1 0 1 1
95 5
0 0 1 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 1 1 0 1 0 0 1 0 1 1 0 0 1 0 0 0 1 1 0 1 1 0 1 0 1 1 0 0 1 0 0 0 1 0 0 1 0 1 0 1 1 0 1 0 0 1 0 1 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 1 1 0 0 1 0 1
17 65
0 0 1 0 0 1 0 0 1 1 0 1 1 1 1 0 0
86 95
0 0 1 0 0 0 1 0 0 0 0 0 1 0 0 0 1 0 0 1 0 1 1 0 1 0 0 0 0 0 0 1 0 1 1 0 0 1 0 0 1 0 0 1 1 0 1 0 0 1 1 0 1 0 1 0 1 1 0 1 1 0 0 1 0 0 1 0 1 1 0 0 1 1 1 0 1 1 0 0 0 0 0 1 0 1
6 20
0 0 0 1 1 0
64 21
0 0 1 1 0 0 0 1 1 0 0 0 0 1 1 1 1 0 1 0 1 1 1 1 1 0 1 0 1 1 1 0 0 1 1 0 1 0 1 0 0 1 1 0 1 0 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 1 1
56 52
0 0 1 1 1 1 1 1 0 1 0 1 1 0 1 1 1 1 1 1 0 0 1 0 1 0 0 0 0 1 1 1 1 0 0 1 0 1 0 0 0 0 1 1 0 1 1 1 0 0 0 1 1 1 1 0
69 52
0 0 0 1 0 1 0 0 1 1 0 0 1 1 0 1 0 0 0 1 1 1 0 1 0 1 0 1 0 1 1 0 1 1 1 1 0 1 0 1 1 0 1 0 1 0 0 0 0 0 1 1 1 1 0 1 0 1 1 1 0 0 1 1 0 0 1 0 1
--------------------------------
4
5 3
0 0 0 0 0
6 5
0 0 0 1 1 1
6 3
0 0 1 1 1 0
3 1
0 1 0
*******/
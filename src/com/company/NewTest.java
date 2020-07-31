package com.company;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewTest {
    public static int kP(int x) {
        int sum = 1;
        for (;x > 0;x--) {
            sum *= 2;
        }
        return sum;
    }
    public static String f(String exp) {
        String s = "";
        int count = 0;
        char lastChr = ' ';
        char[] expsArr = exp.toCharArray();
        for (int i = 0;i < exp.length(); i++) {
            if (expsArr[i] == lastChr) {
                count++;
                lastChr = expsArr[i];
            }
            else if (i < exp.length() - 1 && count == 2 && expsArr[i] == expsArr[i + 1]) {
                count++;
            }
            else {
                count = 1;
                lastChr = expsArr[i];
            }
            if (count == 3) {
                //不加入该字符
                count--;
                continue;
            }
            s += "" + expsArr[i];
        }
        return s;
    }

    public static void main(String[] args ) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int d = scn.nextInt();
        int[] positions = new int[n];
        for (int i =0;i < positions.length;i++) {
            positions[i] = scn.nextInt();
        }
        long sum = 0;
        for (int i = 0 ;i < positions.length - 2; i++) {
            int ap = positions[i];
            for (int j = i + 1;j < positions.length - 1;j++) {
                int bp = positions[j];
                for (int k = j + 1; k < positions.length ; k++) {
                    int cp = positions[k];
                    if (cp - ap > d) {
                        break;
                    }
                    sum++;
                }
            }
        }
        System.out.println(sum > Integer.MAX_VALUE ? sum % 99997867 : sum);
    }
}




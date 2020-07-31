package com.NowCoder;

import com.util.ListNode;
import com.util.UFInt;
import com.util.Utils;
import java.util.*;

public class Dev {
    public static long f (int n){
        long sum = 1;
        for (int i = 1;i <= n;i++) {
            sum *= i;
        }
        return sum;
    }
    public static long cnm (int n , int m) {
        if (m == 0) return 1;
        if (m == 1) return n;
        return f(n) / (f(m) * f(n - m));
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        long sum = 0;
        long fac = (long)Math.pow(10,9) + 7;
        System.out.println("fac=" + fac);
        for (int i = 0;i <= n;i++) {
            //System.out.println("cnm,n = " + n + ",i=" + i + "; res=" + cnm(n,i));
            sum += (cnm(n,i) * (long)Math.pow(m,i)) % fac;
        }
        System.out.println(sum);
    }
}


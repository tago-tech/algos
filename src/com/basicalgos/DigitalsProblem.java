package com.basicalgos;

import com.utils.Utils;

import java.lang.reflect.Modifier;
import java.util.Map;

public class DigitalsProblem {

    /**
     * 位运算实现加法
     * */
    public static int add(int a, int b) {
        //两数异或 等价于 不考虑进位的加法
        //两数向与 等价于 只考虑两数相加的进位信息
        int sum = a ^ b , overflow = a & b;
        //进位信息需要向左偏移一位，因为进位
        overflow <<= 1;
        //直到进位为空
        while (overflow != 0) {
            int cs = sum ^ overflow;
            overflow = sum & overflow;
            overflow <<= 1;
            sum = cs;
        }
        //返回结果
        return sum;
    }

    /**
     * 给定一个非负整数 N，找出小于或等于 N 的最大的整数，同时这个整数需要满足其各个位数上的数字是单调递增。
     *
     * （当且仅当每个相邻位数上的数字 x 和 y 满足 x <= y 时，我们称这个整数是单调递增的。）
     * @param N
     * @return
     */
    public static int monotoneIncreasingDigits(int N) {
        if (N < 10) return N;

        int MOST = 11;

        int[] digitals = toDigitalArray(N,MOST);

        int n = digitals[MOST - 1];

        boolean noOp = true;

        for (int i = n - 1;i >= 0;i--) {

            if (i == n - 1 || digitals[i] >= digitals[i + 1]) {
                continue;
            }

            noOp = false;

            int j = i + 1;

            while (j < n - 1 && digitals[j] == digitals[j + 1]) {
                j++;
            }

            digitals[j--]--;

            while (j > -1) {
                digitals[j--] = 9;
            }

            break;
        }

        int result = N;

        if (!noOp) {
            result = 0;
            for (int i = 0; i < n; i++) {
                result += (int)Math.pow(10,i) * digitals[i];
            }
        }

        return result;
    }

    public static int[] toDigitalArray (int N,int MOST) {

        int[] digitals = new int[MOST];

        int index = 0;

        while (N > 0) {
            digitals[index++] = N % 10;
            N /= 10;
        }

        digitals[MOST - 1] = index;

        return digitals;
    }

    public static void main(String[] args) {
        System.out.println(monotoneIncreasingDigits(332));
    }
}

package com.basicalgos;

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

}

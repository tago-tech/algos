package com.LearnLC;

//要多多温习
public class FourArithmeticOperations {
    //判断正负
    public static boolean isNeg(int n){
        return n < 0;
    }
    //求相反数
    public static int negNum(int n){
        //按位相反 并 加 1
        return add(~n,1);
    }
    //加法
    public static int add(int a,int b){
        int sum = a;
        //当进位消失
        while (b != 0){
            //不考虑进位
            sum = a ^ b;
            //考虑进位
            b = (a & b) << 1;
            a = sum;
        }
        return sum;
    }
    //减法
    public static int minus(int a,int b){
        //减法 = 加上相反数
        return add(a,negNum(b));
    }
    //乘法
    public static int multi(int a,int b){
        int res = 0;
        while(b != 0){
            if((b & 1) != 0) res = add(res,a);
            //带符号移位
            a = a << 1;
            //无符号移位
            b >>>= 1;
        }
        return res;
    }
    //除法,普通除法 无法处理 整数最小值溢出问题
    public static int divideLimit(int dividend, int divisor){
        int res = 0;
        int x = isNeg(dividend) ? negNum(dividend) : dividend;
        int y = isNeg(divisor) ? negNum(divisor) : divisor;
        for (int i = 31;i > -1;i--){
            //若此时
            if((x >> i) >= y){
                res |= (1 << i);
                x = minus(x,y << i);
            }
        }
        return isNeg(dividend) ^ isNeg(divisor) ? negNum(res) : res;
    }
    public static int divide(int dividend,int divisor){
        if(dividend == Integer.MIN_VALUE && divisor == Integer.MIN_VALUE) return 1;
        if(divisor == Integer.MIN_VALUE) return 0;
        //因为除法操作需要先将 取正,负数最小值的 绝对数不存在所以用以上办法.
        if(dividend == Integer.MIN_VALUE) {

            int frist = divideLimit(dividend + 1,divisor);
            int second  = divideLimit(dividend - frist * divisor,divisor);
            //System.out.println("F:"+frist+" S:"+second);
            //修正操作 可能产生溢出
            if(second > 0 && frist > Integer.MAX_VALUE - second) return Integer.MAX_VALUE;
            else return frist + second;
        }
        else {
            return divideLimit(dividend,divisor);
        }
    }
    public static void main(String[] args){
        System.out.println(divide(Integer.MIN_VALUE,-1));
        System.out.println("最大值:"+Integer.MAX_VALUE+" 最小值:"+Integer.MIN_VALUE);
    }
}

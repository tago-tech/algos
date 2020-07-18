package com.LearnLC;


public class MultiplyStrings {
    /*
    * 优化竖式相乘
    * 简单,明白乘法计算流程就行
    * */
    public static String multiply(String num1, String num2) {
        if(num1.equals("") || num2.equals("")) return "";
        //中间变量
        int sum,n1,n2;
        int l1 = num1.length() , l2 = num2.length();
        int[] res = new int[l1 + l2];
        for (int i = l1 - 1;i > -1;i--){
            n1 = num1.charAt(i) - '0';
            for (int j = l2 - 1;j > -1;j--){
                n2 = num2.charAt(j) - '0';
                //相乘最大为两位数 分别在 (i+j, i+j+1)
                //并且要记得存储之前的状态值
                sum = res[i + j + 1] + n1 * n2;
                res[i + j + 1] = sum % 10;
                res[i + j] += sum / 10;
            }
        }
        //少移动一位,保证结果为 0 时正常返回.
        int idx = 0;
        while (idx < l1 + l2 - 1 && res[idx] == 0){
            idx++;
        }
        //StringBuilder速度更快
        StringBuilder resultStr = new StringBuilder("");
        while (idx < res.length){
            resultStr.append(res[idx]);
            idx++;
        }
        return resultStr.toString();
    }
    public static void main(String[] args){
        String str1 = "0";
        String str2 = "0";
        System.out.println(multiply(str1,str2));
    }
}

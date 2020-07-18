package com.LearnLC;

/*
*正则表达式匹配
* */
public class ExpMatch {
    //判断字符串有效性
    public static boolean isVaild(String s,String p){
        char[] s2Arr = s.toCharArray();
        char[] p2Arr = p.toCharArray();
        for(int i = 0;i < s2Arr.length;i++){
            if(s2Arr[i] < 'a' || s2Arr[i] > 'z'){
                return false;
            }
        }
        for(int i = 0;i < p2Arr.length;i++){
            if(p2Arr[i] != '.' && p2Arr[i] != '*' && (p2Arr[i] < 'a' || p2Arr[i] > 'z')){
                return false;
            }
            if(p2Arr[i] == '*' && (i == 0 || p2Arr[i - 1] == '*')){
                return false;
            }
        }
        return true;
    }
    public static boolean isMatch(String s, String p) {
        if(!isVaild(s,p)) return false;
        //递归解法
        //return process(s,p,0,0);
        return dymicProcess(s,p);
    }
    //递归判断 字符串匹配问题
    public static boolean process(String s,String p,int si,int pi){
        //正则表达式结束
        if(pi == p.length()){
            return si == s.length();
        }
        char[] s2Arr = s.toCharArray();
        char[] p2Arr = p.toCharArray();
        //p没有出现 * 的情况
        if(pi == p2Arr.length - 1 || p.charAt(pi + 1) != '*'){
            return (si < s2Arr.length && (s2Arr[si] == p2Arr[pi] || p2Arr[pi] == '.')) && process(s,p,si + 1,pi + 1);
        }
        //p出现了 * 的情况
        while(si < s2Arr.length && (s2Arr[si] == p2Arr[pi] || p2Arr[pi] == '.')){
            if(process(s,p,si,pi + 2)){
                return true;
            }
            si++;
        }
        return process(s,p,si,pi + 2);
    }
    //动态规划
    public static boolean dymicProcess(String s,String p){
        //声明并初始化dp矩阵
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        initDp(dp,s,p);
        char[] s2Arr = s.toCharArray();
        char[] p2Arr = p.toCharArray();
        //dp[i][j]依赖于dp[i + k][ j + 2] 和 dp[i+1][j+1]
        for(int i = s2Arr.length - 1;i > -1;i--){
            for(int j = p2Arr.length - 2;j > -1;j--){
                if(p2Arr[j + 1] != '*'){
                    dp[i][j] = (i < s2Arr.length && s2Arr[i] == p2Arr[j] || p2Arr[j] == '.') && dp[i + 1][j + 1];
                }
                else {
                    int si = i;
                    while(si < s2Arr.length && (s2Arr[si] == p2Arr[j] || p2Arr[j] == '.')){
                        if(dp[si][j + 2]){
                            dp[i][j] = true;
                            break;
                        }
                        si++;
                    }
                    if(!dp[i][j]){
                        dp[i][j] = dp[si][j + 2];
                    }
                }
            }
        }
        return dp[0][0];
    }
    //初始化dp矩阵
    public static void initDp(boolean[][] dp,String s,String p){
        dp[s.length()][p.length()] = true;
        //最下一列,表示s已到终点
        for(int i = p.length() - 2;i > -1;i -= 2){
            if (p.charAt(i) != '*' && p.charAt(i + 1) == '*'){
                dp[s.length()][i] = true;
            }
            else {
                break;
            }
        }
        //右边倒数第一列:表示p已到终点,而s未到终点,因此全为false
        //右边倒数第二列:表示p还剩一位,s 剩 1 ~ s.length个元素
        if(s.length() > 0 && p.length() > 0){
            if(s.charAt(s.length() - 1) == p.charAt(p.length() - 1) || p.charAt(p.length() - 1) == '.')
                dp[s.length() - 1][p.length() - 1] = true;
        }
    }

    public static void main(String[] args){
        String s = "";
        String p = ".";
        System.out.println(isMatch(s,p));
    }
}

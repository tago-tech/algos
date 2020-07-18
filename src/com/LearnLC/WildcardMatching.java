package com.LearnLC;

public class WildcardMatching {
    //字符串格式有效性
    public static boolean isValid(String s,String p){
        char chr = '0';
        for(int i = 0;i < s.length();i++){
            chr = s.charAt(i);
            if(chr < 'a' || chr > 'z') return false;
        }
        for(int i = 0;i < p.length();i++){
            chr = p.charAt(i);
            if(chr != '?' && chr != '*' && (chr < 'a' || chr > 'z')) return false;
        }
        return true;
    }
    public static void initDp(boolean[][] dp,char[] sArr,char[] pArr){

    }
    //匹配函数
    public static boolean isMatch(String s, String p) {
        if(s.equals(p) || p == "*") return true;
        if(s.isEmpty() || p.isEmpty()) return false;


        char[] s2Arr = s.toCharArray();
        char[] p2Arr = p.toCharArray();

        return processed(s2Arr,p2Arr);
    }
    //回朔算法o(min(S,P))
    public static boolean processed(char[] sArr,char[] pArr){
        int sIdx = 0,pIdx = 0;
        int starIndex = -1,sTempIndex = -1;
        while(sIdx < sArr.length){
            //正常字符继续匹配
            if(pIdx < pArr.length && (pArr[pIdx] == '?' || pArr[pIdx] == sArr[sIdx])){
                sIdx++;
                pIdx++;
            }
            //只需要记录到上一层的 * 位置 上两层的不需要记录,肯定已经成功了.
            else if (pIdx < pArr.length && pArr[pIdx] == '*'){
                starIndex = pIdx;
                sTempIndex = sIdx;
                pIdx++;
            }
            //出现不匹配,并且无法调整 *
            else if(starIndex == -1){
                return false;
            }
            //尝试用 * 匹配更多的字符
            else {
                pIdx = starIndex;
                sIdx = sTempIndex + 1;
                sTempIndex = sIdx;
            }
        }
        while(pIdx < pArr.length){
            if (pArr[pIdx] != '*') return false;
            pIdx++;
        }
        return true;
    }
    public static void main(String[] args){
        String s = "";
        String p = "";

        System.out.println(isMatch(s,p));
    }
}


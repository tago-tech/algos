package com.LearnLC;


//重写
public class StrStr {
    /**
     * 由于 next[x] 表示的 是 next[0,...,x-1] 的 特性,而且 next[0,...,x-1] 中前缀不包括最后一个元素且后缀不包括第一个元素,
     * 因此 x = 0 和 x = 1 均无意义,而将 next[0] 设为 -1,可较好地标记处匹配到这个程度已经完全失配(标记模式串的j指针必须退回到0了);
     * **/
    public static void calNext(int[] next,String str){
        int k = -1,j = 0;
        next[0] = -1;
        while(j < str.length() -1){
            if(k == -1 || str.charAt(j) == str.charAt(k)){
                //要么失配,要么接力
                j++;
                k++;
                //实际上计算的时 next[j + 1] 的值 故要求 j < len - 1;
                next[j] = k;
            }
            else k = next[k];//利用 pmt 的特点,k一定程度退回;
        }
    }

    public static int strStr(String haystack, String needle){
        //判空
        if(needle.length() == 0) return 0;
        if(haystack.length() == 0) return -1;
        //计算next数组
        int[] next = new int[needle.length()];
        calNext(next,needle);
        int i = 0,j = 0;
        while(i < haystack.length() && j < needle.length()){
            // j == -1 表示模式串第0个字符已经失配,当前 i 已经没有匹配的可能,故 i 前进;
            // char i == j 时,继续匹配;
            if(j == -1 || haystack.charAt(i) == needle.charAt(j)){
                i++;
                j++;
            }
            else j = next[j]; //失配时寻找当前元素的pmt位置,不至于j归0,i回退;
        }
        if(j == needle.length()) return (i - needle.length());
        return -1;
    }

    //KMP算法 - 计算 next数组
    public static int[] getStrNext(String str) {
        if (str.length() == 1) {
            return null;
        }

        int[] next = new int[str.length()];

        next[0] = -1;
        if (str.length() == 1) return next;

        next[1] = 0;

        int idx = 2,cn = 1;
        //cn 表示 next[idx - 1] 对应的前缀的下一个字符,如果说cn == idx - 1.则说明+1;
        //否则的话,cn 要到 next[cn]也就是前缀的前缀中进行计算.一次循环,直到找不到.就是0
        while (idx < str.length()) {
            if (str.charAt(idx - 1) == str.charAt(cn)) {
                next[idx++] = ++cn;
            }
            else if (cn > 0) {
                cn = next[cn];
            }
            else {
                next[idx++] = 0;
            }
        }
        return next;
    }

    public static int matcherKMP(String M,String N) {
        if (M.length() == 0 || N.length() == 0 || N.length() > M.length()) return -1;
        char[] mChrs = M.toCharArray();
        char[] nChrs = N.toCharArray();
        int i = 0 , j = 0;
        int[] next = getStrNext(N);
        while (i < mChrs.length && j < nChrs.length) {
            if (mChrs[i] == nChrs[j]) {
                i++;
                j++;
            }
            else if (j > 0) {
                j = next[j];
            }
            else {
                i++;
            }
        }
        if (j == nChrs.length)
            return i - j;
        else
            return -1;
    }
    public static void main(String[] args){
        System.out.println(matcherKMP("12345","4"));
    }
}

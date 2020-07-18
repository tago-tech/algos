package com.LearnLC;

import java.util.HashMap;
import java.util.Map;

public class CheckPermutationLcci {
    public static boolean CheckPermutation(String s1, String s2) {
        if(s1.equals("") || s2.equals("") || s1.length() != s2.length()) return false;
        Map<Character,Integer> map = new HashMap<>();
        char chr;
        int count;
        for(int i = 0;i < s1.length();i++){
            chr = s1.charAt(i);
            if (map.containsKey(chr)){
                map.put(chr,map.get(chr) + 1);
            }
            else {
                map.put(chr,1);
            }
        }
        for(int i = 0;i < s2.length();i++){
            chr = s2.charAt(i);
            if(map.containsKey(chr)){
                count = map.get(chr);
                count -= 1;
                map.put(chr,count);
                if(count < 0){
                    return false;
                }
            }
            else {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args){
        String s1 = "abc";
        String s2 = "bad";
        System.out.println(CheckPermutation(s1,s2));
    }
}

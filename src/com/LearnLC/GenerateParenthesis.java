package com.LearnLC;

import java.util.ArrayList;
import java.util.List;

public class GenerateParenthesis {
    public static void travel(List<String> resultCollections,String path,int open,int close,int max){
        if(open == max && close == max){
            resultCollections.add(path);
            return;
        }
        if(open < max) travel(resultCollections,path+"(",open+1,close,max);
        if(close < open) travel(resultCollections,path+")",open,close+1,max);
    }
    public static List<String> generateParenthesis(int n) {
        List<String> resultCollections = new ArrayList<>();
        String path = "";
        travel(resultCollections,path,0,0,n);
        return resultCollections;
    }
    public static void main(String[] args){
        List<String> rs = generateParenthesis(3);
        for(String temp : rs) System.out.println(temp);
    }
}

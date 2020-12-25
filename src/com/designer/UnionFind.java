package com.designer;

import java.util.Stack;

public class UnionFind {

    private int[] fatherMap;

    private int[] rankMap;

    private int size;

    //构造函数
    public UnionFind(int size) {
        // [0 , size - 1]
        fatherMap = new int[size];
        rankMap = new int[size];
        for(int i = 0;i < fatherMap.length;i++) {
            fatherMap[i] = i;
            rankMap[i] = 1;
        }
        this.size = size;
    }
    //union
    public  void union(int e1,int e2) {
        //判断是否已经在一个簇之中
        //进行合并
        int e1Father = findFather(e1);
        int e2Father = findFather(e2);
        if (e1Father != e2Father) {
            //进行合并
            //获得rank
            int rankE1Father = rankMap[e1Father];
            int rankE2Father = rankMap[e2Father];
            int big = rankE1Father > rankE2Father ? e1Father : e2Father;
            int small = rankE1Father > rankE2Father ? e2Father : e1Father;
            //rank 合并
            rankMap[big] += rankMap[small];
            fatherMap[small] = big;
            rankMap[small] = 0;

            size--;
        }
    }

    //isSameSet
    public  boolean isSameSet(int e1,int e2) {
        //1.判断 e1 和 e2 合法性
        //2.查找父节点
        return findFather(e1) == findFather(e2);
    }
    //findFather
    public int findFather(int element) {
        Stack<Integer> stack = new Stack<>();
        while (element != fatherMap[element]) {
            stack.push(element);
            element = fatherMap[element];
        }
        //路径压缩
        while (!stack.isEmpty()) {
            fatherMap[stack.pop()] = element;
        }
        return element;
    }
    //获取质谱
    public int[] getRankMap() {
        return this.rankMap;
    }

    //size of Connected componentes.
    public int size() {
        return size;
    }
}

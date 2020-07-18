package com.LearnLC;


public class GasStation {


    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int N = gas.length;
        int wholeSpeed = 0,curSpeed = 0;
        int startIdx = 0;
        /*
        * 假设起始点 为 x
        * 整个过程分为两段, [ 1 ~ x ] 和 [ x ~ N ]
        * cur变量保证 [ x ~ N ] 有足够油量行驶,且保证在每一中间节点不中断,若中断则重启;
        * cur >= 0
        * 而 whole 变量保证 两段走下来 大于 0,明知 [ x ~ N ] > 0 , 即使 [ 1 ~ x ] < 0
        * 也能保证 两段相加 >= 0;
        * */
        for (int i = 0;i < N;i++){
            wholeSpeed += gas[i] - cost[i];
            curSpeed += gas[i] - cost[i];
            if (curSpeed < 0){
                startIdx = i + 1;
                curSpeed = 0;
            }
        }
        return wholeSpeed >= 0 ? startIdx : -1;
    }
    public static void main(String[] args){
        int[] gas = {1,2,3,4,5};
        int[] costs = {3,4,5,1,2};

        System.out.println(canCompleteCircuit(gas,costs));
    }
}

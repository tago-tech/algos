package com.LearnLC;

public class JumpGame2 {
    //动态规划 O(N2)
    public static int jump(int[] nums) {
        int[] minJump = new int[nums.length];
        int tryMove = 0;
        minJump[nums.length - 1] = 0;
        for(int idx = nums.length - 2;idx >= 0;idx--){
            minJump[idx] = Integer.MAX_VALUE;
            tryMove = 1;
            while(tryMove <= nums[idx] && idx + tryMove < nums.length){
                if(minJump[idx + tryMove] != Integer.MAX_VALUE)
                    minJump[idx] = Math.min(minJump[idx],minJump[idx + tryMove] + 1);
                tryMove++;
            }
        }
        for(int i : minJump) System.out.print(i + " ");
        System.out.println();

        return minJump[0];
    }
    //贪心算法 O(N2),每次总jump到能跳最远的地方
    public static int jumpTX(int[] nums){
        int steps = 0,maxPos = 0,idx = 0,canJump = 0,nextPos = 0;
        //idx 表示当前所在位置索引
        while(idx < nums.length - 1){
            //当前位置最远可达位置
            nextPos = idx + nums[idx];
            maxPos = idx + nums[idx];
            //若当前位置不能直接跳跃到终点
            if(maxPos < nums.length - 1){
                //寻找下一跳中能调到最远的地方
                for(int i = 1;i <= nums[idx] && idx + i < nums.length;i++){
                    canJump = nums[idx + i] + idx + i;
                    if(canJump > maxPos){
                        maxPos = canJump;
                        nextPos = idx + i;
                    }
                }
                //跳跃
                idx = nextPos;
                steps++;
            }
            else{
                //直接跳跃到终点
                steps++;
                break;
            }
        }
        return steps;
    }

    //书本上的方法
    public static int jumpBook(int[] nums){
        int next = 0,step = 0,cur = 0;
        for(int i = 0;i < nums.length;i++){
            if(cur < i){
                cur = next;
                step++;
            }
            next = Math.max(next,i + nums[i]);
        }
        return step;
    }
    public static void main(String[] args){
//        int[] nums = {2,3,1,1,4};
//        int[] nums = {2,1};
        int[] nums = {2,3,1};
        System.out.println(jumpBook(nums));
    }
}

package com.LearnLC;

public class MinPathSum {
    public static int minPathSum(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) return 0;
        //路径压缩,降低内存使用率
        int chang = grid.length,kuan = grid[0].length;
        int[][] dp = new int[chang][kuan];
        dp[0][0] = grid[0][0];
        for(int i = 1;i < chang; i++) dp[i][0] = grid[i][0] + dp[i - 1][0];
        for(int i = 1;i < kuan; i++) dp[0][i] = grid[0][i] + dp[0][i - 1];
        for (int i = 1;i < chang;i++) {
            for (int j = 1;j < kuan;j++) {
                dp[i][j] = Math.min(dp[i - 1][j],dp[i][j -1]) + grid[i][j];
            }
        }
        return dp[chang - 1][kuan - 1];
    }
    //龙与地下城游戏,最小生命值 - 174
    public static int calculateMinimumHP(int[][] dungeon) {
        if (dungeon.length == 0 || dungeon[0].length == 0) return 0;
        int baseHp = 1;
        int xMax = dungeon.length,yMax = dungeon[0].length;
        //maxCosts[i][j] 表示 从 (0,0) 到 (i,j) 过程中消耗掉最大的血
        int[][] maxCosts = new int[xMax][yMax];
        maxCosts[xMax - 1][yMax - 1] = dungeon[xMax - 1][yMax - 1] >= 0 ? 1 : 1 - dungeon[xMax - 1][yMax - 1];
        for (int i = xMax - 2;i > -1;i--) {
            maxCosts[i][yMax - 1] = maxCosts[i + 1][yMax - 1] - dungeon[i][yMax - 1];
            maxCosts[i][yMax - 1] = Math.max(maxCosts[i][yMax - 1],1);
        }
        for (int i = yMax - 2;i > -1;i--) {
            maxCosts[xMax - 1][i] = maxCosts[xMax - 1][i + 1] - dungeon[xMax - 1][i];
            maxCosts[xMax - 1][i] = Math.max(maxCosts[xMax - 1][i],1);
        }

        for (int i = xMax - 2;i > -1;i--) {
            for (int j = yMax - 2;j > -1;j--) {
                maxCosts[i][j] = Math.min(maxCosts[i + 1][j] - dungeon[i][j],maxCosts[i][j + 1] - dungeon[i][j]);
                //保证最小 = 1
                maxCosts[i][j] = Math.max(maxCosts[i][j],1);
            }
        }
        return maxCosts[0][0];
    }
    public static void main(String[] args) {
//        int[][] a = {{1,3,1},{1,5,1},{4,2,1}};
//        System.out.println(minPathSum(a));
        int[][] a = {{-2,-3,3},{-5,-10,1},{10,30,-5}};
        System.out.println(calculateMinimumHP(a));
    }
}

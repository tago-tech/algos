package com.LearnLC;

public class GameOfLife {
    //每日一题,生命游戏
    public static void gameOfLife(int[][] board) {
        if (board.length == 0) return;
        int[][] boardDump = new int[board.length][board[0].length];
        for (int i = 0;i < board.length;i++) {
            for (int j = 0;j < board[0].length;j++) {
                int count = 0;
                for (int i_mve = Math.max(0,i - 1);i_mve < Math.min(i + 2,board.length);i_mve++) {
                    for (int j_mve = Math.max(0,j - 1);j_mve < Math.min(j + 2,board[0].length);j_mve++) {
                        if (i_mve != i || j_mve != j)
                            count += board[i_mve][j_mve] == 1? 1 : 0;
                    }
                }
                if (count < 2) boardDump[i][j] = 0;
                else if (count > 3) boardDump[i][j] = 0;
                else if (count == 3) boardDump[i][j] = 1;
            }
        }
        for (int i = 0;i < board.length;i++) {
            for (int j = 0;j < board[0].length;j++) {
                board[i][j] = boardDump[i][j];
            }
        }
    }
}

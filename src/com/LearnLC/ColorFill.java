package com.LearnLC;

//颜色填充
public class ColorFill {
    public static int[][] dfs(int[][] image,int sr,int sc,int newColor,int[][] flags,int source) {
        if (image == null) return null;
        //深度优先搜索
        image[sr][sc] = newColor;
        flags[sr][sc] = 1;
        //上邻居
        if (sr > 0 && image[sr - 1][sc] == source && flags[sr - 1][sc] == 0)
            dfs(image,sr - 1,sc,newColor,flags,source);
        //下邻居
        if (sr < image.length - 1 && image[sr + 1][sc] == source && flags[sr + 1][sc] == 0)
            dfs(image,sr + 1,sc,newColor,flags,source);
        //左邻居
        if (sc > 0 && image[sr][sc - 1] == source && flags[sr][sc - 1] == 0)
            dfs(image,sr,sc - 1,newColor,flags,source);
        //右邻居
        if (sc < image[0].length - 1 && image[sr][sc + 1] == source && flags[sr][sc + 1] == 0)
            dfs(image,sr,sc + 1,newColor,flags,source);
        return image;
    }
    public static int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int[][] flags = new int[image.length][image[0].length];
        return dfs(image,sr,sc,newColor,flags,image[sr][sc]);
    }
    public static void main(String[] args) {
//        int[][] a = {{1,1,1},{1,1,0},{1,0,1}};
        int[][] a = {{0,0,0},{0,0,0}};
        a = floodFill(a,1,1,2);

        for (int i = 0;i < a.length;i++) {
            System.out.println();
            for (int j = 0;j < a[0].length;j++) {
                System.out.print(" "+a[i][j]);
            }
        }
    }
}

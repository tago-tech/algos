package com.basicalgos;

public class GeometryProblems {

    /**
     * 两个矩形的覆盖面积，中间可能会有重合的部分
     * */
    public static int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        //1.A区面积
        int aOfA = (D- B) * (C - A);
        //2.B区面积
        int aOfB = (G- E) * (H - F);
        //3.重叠区域面积 , 这部分需要减去,避免重复计算
        int width = 0;
        if (Math.min(C,G) > Math.max(A,E)) {
            width = Math.min(C,G) - Math.max(A,E);
        }
        int height = 0;
        if (Math.min(D,H) > Math.max(B,F)) {
            height = Math.min(D,H) - Math.max(B,F);
        }
        return aOfA + aOfB - width * height;
    }
}

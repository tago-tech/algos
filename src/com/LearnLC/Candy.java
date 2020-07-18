package com.LearnLC;

public class Candy {
    /*
    * 分发糖果
    * 基础是连续递增1的序列和为 n * (n + 1) / 2
    * 将序列分为上坡和下坡,坡底总为 1,能让总数最小,连续递增 1,也是递增的最小量
    * 如果上坡是 [1,2,3,4] 则分发的糖果数应该是 {1,2,3,4],但是如果紧接着下坡是[4,2,1] (共用4),则下坡对应的应该是 {3,2,1}
    * 中间值,就是坡顶的 4 节点一个值为4 一个值为3,冲突了,应该选最大的.
    * */
    public static int candy(int[] ratings) {
        if(ratings == null || ratings.length == 0) return 0;
        //找到第一个下坡的终点(坡底),若刚开始的不是下坡返回的是index = 0,即使不存在 0 也是坡底
        int index = rFinalPoint(ratings,0);
        //计算得到第一个坡底的累加和,并初始化结果res
        int res = rPart(0,index);
        //每走一个递增节点增加的分值,因为下坡时候有rPart函数计算整个下坡的分值和,这个变量只在平坦坡顶和上坡过程中变换
        //这样就记录了 上坡的长度 或者平坦坡顶的长度
        int socres = 1;
        //代表坐标 index 在移动过程中,下一跳的位置,快速下坡
        int next = 0;
        //上坡长度和下坡长度,由前面推理得到,谁的坡越长坡顶就应该按谁的标准来
        int rPartLen,lPartLen;
        //因为初始时候index是坡底,现在走过坡底,进入上坡
        index++;
        //走的过程中与前一个元素比,方便一点
        while (index < ratings.length) {
            if (ratings[index] > ratings[index - 1]) {
                //上坡过程,分数不断增加,并且不断记录
                res += ++socres;
                index++;
            }
            else if (ratings[index] < ratings[index - 1]) {
                //此时发现了下坡,因为是与前一个元素相比,index - 1处才是下坡开始的地方
                //找到这个下坡的坡底
                next = rFinalPoint(ratings,index - 1);
                //计算整个下坡的序列和
                res += rPart(index - 1,next);
                //下坡长度 = 坡底 - 坡顶的坐标 + 1
                rPartLen = next - (index - 1) + 1;
                //前一个上坡的长度正好用 socer 变量表示,因为在上坡的过程中一直递增
                lPartLen = socres;
                //坡顶重复计算了,减去对应的值,谁小减谁
                res -= rPartLen > lPartLen ? lPartLen : rPartLen;
                //下一步走出坡底
                next++;
                index = next;
                socres = 1;
            }
            else {
                //此时相邻两数相等,就是说走到了平坦的地方,平坦的地方分数总为1
                res += 1;
                socres = 1;
                index++;
            }
        }
        return res;
    }
    //从起点处找到下坡的终点
    public static int rFinalPoint(int[] rations,int start) {
        for (int i = start;i < rations.length - 1;i++) {
            if (rations[i] <= rations[i + 1]) return i;
        }
        return rations.length - 1;
    }
    //计算[right,left]这个序列的 连续递增 1 的累加和.
    public static int rPart(int right,int left){
        int len = left - right + 1;
        return len * (len + 1) / 2;
    }

    public static void main(String[] args) {
//        n(n+1)/2
        int[] a = {0,1,2,5,3,2,7};
        //         1,2,3,4,2,1,2
        System.out.println(candy(a));
    }
}

package com.LearnLC;

public class FindMedianSortedArrays {
    public static double findMedianSortedArrays(int[] A,int[] B){
        int la = A.length;
        int lb = B.length;
        //奇偶统一
        int left = (la + lb + 1) / 2; // 2
        int right = (la + lb + 2) / 2;// 3

        return (findKth(A,0,B,0,left) + findKth(A,0,B,0,right)) / 2.0;
    }
    //find K th 数
    public static double findKth(int[] A,int i,int[] B,int j,int k){
        System.out.println("i="+i+";j="+j+";k="+k);
        if(i >= A.length) return B[j + k - 1];
        if(j >= B.length) return A[i + k - 1];
        if(k == 1) return Math.min(A[i],B[j]);
        //若一方元素个数小于 K/2,则双方元素个数之和小于 K,另一方原始的K/2位置肯定是不为 K th,故舍弃另一方的前 K/2.
        int k2Arr1 = i + k / 2 - 1 < A.length ? A[i + k / 2 - 1] : Integer.MAX_VALUE;//2
        int k2Arr2 = j + k / 2 - 1 < B.length ? B[j + k / 2 - 1] : Integer.MAX_VALUE;//3
        if(k2Arr1 > k2Arr2) return findKth(A,i,B,j + k / 2,k - k / 2);
        else return findKth(A,i + k / 2,B,j,k - k / 2);
    }

    public static double findMedianSortedArrays2(int[] A,int[] B){
        int m,n,i = 0,j = 0;
        m = A.length;
        n = B.length;
        // 确保 A.len <= B.len
        if(m > n){
            int[] C = A;
            A = B;
            B = C;
            m = A.length;
            n = B.length;
        }

        int imin = 0,imax = m;
        int half = (m + n + 1) / 2;
        while( imin <= imax){
            //计算 i , j
            i = (imin + imax) / 2;
            j = half - i;
            //A[i - 1] < B[j] , B[j - 1] < A[i]
            if(i < imax && B[j - 1] > A[i]){
                // i 偏小
                imin = i + 1;
            }
            else if(i > imin && A[i - 1] > B[j]){
                // i 偏 大
                imax = i - 1;
            }
            else {
                int maxLeft = 0;
                if(i == 0) maxLeft = B[j - 1];
                else if( j == 0) maxLeft = A[i - 1];
                else maxLeft = Math.max(A[i - 1],B[j - 1]);
                if( (m +  n) % 2 == 1) return maxLeft;
                int minRight = 0;
                if(i == m) minRight = B[j];
                else if(j == n) minRight = A[i];
                else minRight = Math.min(A[i],B[j]);
                return (minRight + maxLeft) / 2.0;
            }
        }
        return 0.0;
    }
    public static void main(String[] args){
        int[] nums1 = {1};
        int[] nums2 = {};
        //{1,2,3,4}
        System.out.println(findMedianSortedArrays2(nums1,nums2));
    }
}

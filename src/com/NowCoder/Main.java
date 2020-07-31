package com.NowCoder;

import com.util.ListNode;
import com.util.TreeNode;
import com.util.UFInt;
import com.util.Utils;
import javafx.beans.property.ReadOnlyListWrapper;

import javax.imageio.stream.ImageOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    /**
     * 数字序列中某一位数字
     * https://leetcode-cn.com/problems/shu-zi-xu-lie-zhong-mou-yi-wei-de-shu-zi-lcof/
     * */

    /**
     * 约瑟夫环
     * */
    public static int lastRemaining (int n, int m) {
        return f (n,m) - 1;
    }
    public static int f(int n, int m) {
        if (n == 1) return 1;
        return (f(n - 1,m) + m - 1) % n + 1;
    }
    /**
     *
     * */
    public static int[] singleNumbers(int[] nums) {
        int[] rex = {-1,-1};
        if (nums == null || nums.length < 2) return rex;
        int xor = 0;
        for (int x : nums) {
            xor ^= x;
        }
        //xor中二进制表达中最右边1
        int dotOne = (xor) & (-xor);
        int xor2 = 0;
        for (int x : nums) {
            if ((x & dotOne) != 0) {
                xor2 ^= x;
            }
        }
        rex[1] = xor2;
        rex[0] = xor ^ xor2;
        //Utils.print(rex);
        return rex;
    }

    /**
     * 魔术索引
     * 数组整体有序
     * */
    public static int findMagicIndex(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        return findM(nums,0,nums.length - 1);
    }
    public static int findM (int[] nums , int left , int right) {
        if (left > right) return -1;
        int middle = (left + right) / 2;
        if (nums[middle] == middle) {
            int resultOfLeft = findM(nums,left,middle - 1);
            return resultOfLeft == -1 ? middle : resultOfLeft;
        }
        else {
            //当前元素不满足
            int result = findM(nums,left,middle - 1);
            if (result == -1) {
                result = findM(nums,middle + 1,right);
            }
            return result;
        }
    }

    /**
     * 数组中出现次数超过一半的
     * */
    public static int majorityElement(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        int current = Integer.MIN_VALUE , count = 0;
        for (int x : nums) {
            if (x == current) {
                count++;
            }
            else if (count <= 0){
                current = x;
                count = 1;
            }
            else {
                count--;
            }
        }
        count = 0;
        for (int x : nums) {
            if (x == current) {
                count++;
            }
        }
        return count > nums.length / 2 ? current : -1;
    }
    /**
     * 双指针法
     * right指针加入字符，left指针移除字符
     * 最多能够替换k次
     * most表示当前最多的字符的个数
     * r - l 表示 当前子串的长度
     * 其中使用map记录字符的个数
     *
     * */
    public static int characterReplacement(String s, int k) {
        if (s == null || s.length() == 0) return 0;
        Map<Character,Integer> map = new HashMap<>();
        int l = 0 , r = 0 , n = s.length();
        int most = 0 , theRight = 0;
        while (r < n) {
            char c = s.charAt(r++);
            map.put(c,map.getOrDefault(c,0) + 1);
            most = Math.max(most,map.get(c));
            int currentOfLength = r - l;
            if (currentOfLength - most > k) {
                c = s.charAt(l++);
                map.put(c,map.get(c) - 1);
            }
            currentOfLength = r - l;
            if (currentOfLength - most <= k) {
                theRight = Math.max(theRight,currentOfLength);
            }
        }
        return theRight;
    }
    /**
     * 数组排序，实现奇数下标是奇数，偶数下标是偶数
     * */
    public static int[] sortArrayByParityII(int[] nums) {
        int n = nums.length , diffn = n / 2;
        int l = 0 , r = n - 1;
        //第一步，分治排序 (奇偶形式)
        while (l < r) {
            while (l < r && (nums[l] & 1) != 0) l++;
            while (l < r && (nums[r] & 1) == 0) r--;
            if (l < r) {
                nums[l] = nums[l] ^ nums[r];
                nums[r] = nums[l] ^ nums[r];
                nums[l] = nums[l] ^ nums[r];
            }
        }
        //Utils.print(nums);
        //第二步，这时候的排序肯定是[{奇数}、{偶数}]
        for (int i = 0;i < l;i += 2) {
            int j = i + diffn;
            if ((j & 1) == 0) {
                j++;
            }
            nums[i] = nums[i] ^ nums[j];
            nums[j] = nums[i] ^ nums[j];
            nums[i] = nums[i] ^ nums[j];
        }
        //Utils.print(nums);
        return nums;
    }
    public static int[] sortArrayByParityII2(int[] nums) {
        int odd = 1 , even = 0 , n = nums.length;
        while (odd < n && even < n) {
            if ((nums[n - 1] & 1) == 0) {
                swap(nums,even,n - 1);
                even += 2;
            }
            else {
                swap(nums,odd,n - 1);
                odd += 2;
            }
        }
        Utils.print(nums);
        return nums;
    }
    public static void swap (int[] nums,int left,int right) {
        if (left == right) return;
        nums[left] =nums[left] ^ nums[right];
        nums[right] = nums[left] ^ nums[right];
        nums[left] = nums[left] ^ nums[right];
    }

    /**
     * https://leetcode-cn.com/problems/longest-substring-with-at-least-k-repeating-characters/
     * */
    public int longestSubstring(String s, int k) {
        return 1;
    }
    /**
     * 最小覆盖子串
     * */
    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) return "";
        Map<Character,Integer> tmap = new HashMap<>();
        for (char c : t.toCharArray()) {
            tmap.put(c,tmap.getOrDefault(c,0) +1);
        }
        System.out.println("tmap=" + tmap);
        int l = 0 , r = 0 , minl = Integer.MAX_VALUE , n = s.length();
        int start = -1 , end = -1;
        Map<Character,Integer> smap = new HashMap<>();
        while (r < n) {
            char c = s.charAt(r++);
            smap.put(c,smap.getOrDefault(c,0) + 1);
            char tail = s.charAt(l);
            while ((l < r) && (!tmap.containsKey(tail) || tmap.get(tail) < smap.get(tail))) {
                smap.put(tail,smap.get(tail) - 1);
                if (l == n - 1) {
                    break;
                }
                tail = s.charAt(++l);
            }
            if (r - l > 0) {
                boolean contained = true;
                for (char key : tmap.keySet()) {
                    if (!smap.containsKey(key) || smap.get(key) < tmap.get(key)) {
                        contained = false;
                        break;
                    }

                }
                if (contained && r - l < minl) {
                    System.out.println("smap=" + smap);
                    System.out.println("tmap=" + tmap);
                    start = l;
                    end = r;
                    minl = r - l;
                }
            }
        }
        System.out.println("s=" + start + ",e="+end);
        String rex = "";
        if (start == -1) {
            return "";
        }
        while (start < end) {
            rex += s.charAt(start++);
        }
        return rex;
    }

    static class Point {
        int x;
        int y;
        public Point(int x1,int y1) {
            x = x1;
            y = y1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    /**
     * DFS尝试穿行
     * 陆:3
     * 海：2
     * 海陆:5
     * x1,y1表示当前在的位置
     * */
    public static int minCost (char[][] martrix ,int x1,int y1,int x2,int y2,boolean[][] history,Map<Point,Integer> map) {
        int n = martrix.length , m = martrix[0].length;

        //越界检查
        if (!postionsVaild(x1,y1,n,m)) return 100000000;
//        if (map.containsKey(new Point(x1,y1))) return map.get(new Point(x1,y1));
        if (x1 == x2 && y1 == y2) return 0;
        history[x1][y1] = true;
        int minCost = 100000000;
        //上下左右
        int[] dx = {-1,1,0,0} , dy = {0,0,-1,1};
        for (int i = 0;i < dx.length;i++) {
            int adjx = x1 + dx[i] , adjy = y1 + dy[i];
            if (postionsVaild(adjx,adjy,n,m)) {
                if (history[adjx][adjy]) continue;
                char source = martrix[x1][y1] , next = martrix[adjx][adjy];
                int stepCost = Integer.MAX_VALUE;
                if (source == 'S' && next == 'S') {
                    stepCost = 2;
                }
                else if (source  == 'C' && next == 'C') {
                    stepCost = 3;
                }
                else {
                    stepCost = 5;
                }
                int c = minCost(martrix,adjx,adjy,x2,y2,history,map) + stepCost;
                minCost = Math.min(c,minCost);
            }
        }
        history[x1][y1] = false;
        map.put(new Point(x1,y1),minCost);
        return minCost;
    }
    public static boolean postionsVaild (int x, int y,int n, int m) {
        if (x < 1) return false;
        if (x >= n) return false;
        if (y < 1) return false;
        if (y >= m) return false;
        return true;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = 0;
        int m = 0;
        int q = 0;
        String line = scn.nextLine();
        String[] segs = line.split(" ");
        n = Integer.valueOf(segs[0]);
        m = Integer.valueOf(segs[1]);
        q = Integer.valueOf(segs[2]);
        //输入图形
        char[][] martrix = new char[n + 1][m + 1];
        for (int i = 1;i <= n;i++) {
            line = scn.nextLine();
            for (int j = 1;j <= m;j++) {
                martrix[i][j] = line.charAt(j - 1);
            }
        }

        Utils.print(martrix);
        boolean[][] history = new boolean[n + 1][m + 1];
        for (int order = 0;order < q;order++) {
            int x1 = scn.nextInt();
            int y1 = scn.nextInt();
            int x2 = scn.nextInt();
            int y2 = scn.nextInt();
            Map<Point,Integer> map = new HashMap<>();
            int cost = minCost(martrix,x1,y1,x2,y2,history,map);
            System.out.println(cost);
        }
    }
}









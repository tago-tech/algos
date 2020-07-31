package com.ali;

import com.company.ListNode;

import java.util.*;

import com.sun.javafx.css.PseudoClassState;
import com.util.UFInt;
import com.util.Utils;
import com.company.TreeNode;

public class Test {
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        int n = 0 , m = 0;
        ListNode h1 = pHead1,h2 = pHead2;
        while (h1 != null) {
            h1 = h1.next;
            n++;
        }
        while (h2 != null) {
            h2 = h2.next;
            m++;
        }
        h1 = pHead1;
        h2 = pHead2;
        if (n < m) {
            h1 = pHead2;
            h2 = pHead1;
            n = n ^ m;
            m = n ^ m;
            n = n ^ m;
        }
        // h1长 ，h2短
        //让长的先走 (n - m) 步
        int step = n - m;
        while (step > 0) {
            h1 = h1.next;
            step--;
        }
        while (h1 != null && h2 != null && h1 != h2) {
            h1 = h1.next;
            h2 = h2.next;
        }
        return h1;
    }
    public int FirstNotRepeatingChar(String str) {
        HashMap<Character,Integer> map = new HashMap<>();
        for (int i = 0;i < str.length();i++) {
            map.put(str.charAt(i),map.getOrDefault(str.charAt(i),0) + 1);
        }
        for (int i = 0;i < str.length();i++) {
            if (map.get(str.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }
    public static int cutRope(int target) {
//        if (target == 2) {return 1;}
        long[] dp = new long[target + 1];
        dp[1] = 1;
        for (int i = 2;i <= target;i++) {
            //第一段从 1 - i / 2
            for (int j = 1;j <= i / 2;j++) {
                int remain = i - j;
                dp[i] = Math.max(dp[i],Math.max(dp[j],j) * Math.max(dp[remain],remain));
            }
        }
        return (int)dp[target];
    }
    //奇数在前，偶数在后
    public void reOrderArray(int [] array) {
        //奇数有 s 个 ，偶数有d个
        int s = 0,d = 0;
        for (int i = 0;i < array.length;i++) {
            s += array[i] % 2 == 1 ? 1 : 0;
            d += array[i] % 2 == 1 ? 0 : 1;
        }
        int[] temp = new int[array.length];
        int jStart = 0,ouStart = s;
        for (int i = 0;i < array.length;i++) {
            if ((array[i] & 1) == 1) {
                temp[jStart++] = array[i];
            }
            else {
                temp[ouStart++] = array[i];
            }
        }
        for (int i = 0;i < array.length;i++) {
            array[i] = temp[i];
        }
        return;
    }
    //和为 sum  连续正数序列
    public static ArrayList<ArrayList<Integer> > FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> rex = new ArrayList<>();
        int left = 1,right = 0,curSum = 0;
        while (right < sum) {
            if (curSum < sum) {
                right++;
                curSum += right;
            }
            else {
                if (curSum == sum) {
                    //记录下
                    int l = left,r = right;
                    ArrayList<Integer> temp = new ArrayList<>(r - l + 1);
                    for (int i = l;i <= r;i++) {
                        temp.add(i);
                    }
                    rex.add(temp);
                }
                curSum -= left;
                left++;
                if (left >= right) break;
            }
        }
        return rex;
    }
    public static int RectCover(int target) {
        if (target <= 1) return 1;
        int[] dp = new int[target + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2;i <= target;i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[target];
    }
    public static void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        int xorInit = 0;
        for (int i = 0;i < array.length;i++) {
            xorInit ^= array[i];
        }
        //找出xor结果中二进制中第几位为1
        int k = 0;
        while ((xorInit & (1 << k)) == 0) {
            k++;
        }
        int rightOne = (1 << k);
        int xorOne = 0;
        for (int i = 0;i < array.length;i++) {
            if ((array[i] & rightOne) != 0) {
                xorOne ^= array[i];
            }
        }
        num1[0] = xorOne;
        num2[0] = xorInit ^ xorOne;
    }
    //二叉树镜像
    public static void Mirror(TreeNode root) {
        if (root == null) return;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        Mirror(root.left);
        Mirror(root.right);
    }
    // 生成字符串的全排列组合
    public static ArrayList<String> Permutation(String str) {
        ArrayList<String> rex = new ArrayList<>();
        if (str == null || str.length() == 0) return rex;
        boolean[] history = new boolean[str.length()];
        char[] paths = new char[str.length()];
        recurPermutation(str.toCharArray(),0,history,paths,rex);
        return rex;
    }
    public static void recurPermutation(char[] chars,int idx,boolean[] history,char[] paths,ArrayList<String> rex) {
        //表示现在选择第 idx 个字符
        if (idx == paths.length) {
            rex.add(Utils.chrsToString(paths));
            //System.out.println(Tools.chrsToString(paths));
            return;
        }
        //Set for idx char ,use for un-double
        Set<Character> set = new HashSet<>();
        for (int i = 0;i < chars.length;i++) {
            if (!history[i] && !set.contains(chars[i])) {
                paths[idx] = chars[i];
                history[i] = true;
                recurPermutation(chars,idx + 1,history,paths,rex);
                set.add(chars[i]);
                history[i] = false;
            }
        }
    }

    public static int solve (int n, int k, int[][] card) {
        if (card == null || n < k) return -1;
        int minDistance = -1;
        Arrays.sort(card,(o1, o2) -> o1[1] == o2[1] ? o1[0] - o2[0] : o1[1] - o2[1]);
        //Utils.print2DArray(card);
        HashMap<Integer,Integer> map = new HashMap<>();
        int right = -1,left = 0;
        while (right < card.length - 1) {
            right++;
            map.put(card[right][0],map.getOrDefault(card[right][0],0) + 1);
            //left去重
            while (map.get(card[left][0]) > 1) {
                map.put(card[left][0],map.get(card[left][0]) - 1);
                if (map.get(card[left][0]) == 0) {
                    map.remove(card[left][0]);
                }
                left++;
            }
            if (map.size() == k) {
                //已经凑齐
                int end = card[right][1],start = card[left][1];
                minDistance = minDistance == -1 ? end - start : Math.min(minDistance,end - start);
            }
        }
        return minDistance;
    }
    /*
     * public class Point {
     *   int x;
     *   int y;
     * }
     */

    public int producemask (int n, int m, Point[][] strategy) {
        if (n == 0 || strategy == null || strategy.length == 0) return 0;
        // write code here
        int[][] dp = new int[n + 1][m + 1];
        int maxProducement = 0;
        for (int i = 1;i <= n;i++) {
            for (int j = 0;j <= m;j++) {
                //当前的策略一个都不选
                dp[i][j] = dp[i - 1][j];
                //当前生产线对应的策略集合
                Point[] stagys = strategy[i - 1];
                for (int k = 0;k < stagys.length;k++) {
                    //当前策略要求的人数以及对应的产能
                    int numsOfPeple = stagys[k].x , ability = stagys[k].y;
                    if (j >= numsOfPeple) {
                        dp[i][j] = Math.max(ability + dp[i - 1][j - numsOfPeple],dp[i][j]);
                    }
                }
                maxProducement = Math.max(dp[i][j],maxProducement);
            }
        }
        return maxProducement;
    }

    // S1 和 S2 的最小距离
    public static int GetMinDistance (String S1, String S2) {
        if (S1 == null || S2 == null) return 0;
        if (S1.equals(S2)) return 0;
        // write code here
        int[][] mars = new int[26][27];
        int idx = 0,norEq = 0;
        while (idx < S1.length()) {
            char cs1 = S1.charAt(idx);
            char cs2 = S2.charAt(idx);
            if (cs1 == cs2) {
                mars[cs1 - 'a'][26]++;
            }
            else {
                mars[cs1 - 'a'][cs2 - 'a']++;
                norEq++;
            }
            idx++;
        }
        int maxWin = 0;
        for (int i = 0;i < 26;i++) {
            for (int j = 0;j < 26;j++) {
                if (mars[i][j] > mars[i][26]) {
                    maxWin = Math.max(maxWin,mars[i][j] - mars[i][26]);
                }
            }
        }
        return norEq - maxWin;
    }

    public String solve (int[] param, Point[] edge) {
        // write code here
        int n = param[0],m = param[1];
        if (edge == null || edge.length == 0) return "No";
        //表示标记边
        boolean[] history = new boolean[edge.length];
        //从1节点出发
        //从与1有相连的节点出发
        boolean ca = dfs(edge,1,history);
        return ca ? "Yes" : "No";
    }
    public boolean dfs (Point[] edge,int start,boolean[] history) {
        //边界条件
        boolean success = false;
        //搜索可能的边
        for (int i = 0;i < edge.length;i++) {
            Point temp = edge[i];
            if (!history[i] && (temp.x == start || temp.y == start)) {
                    history[i] = true;
                    if (temp.x == start) {
                        if (temp.y == 1) return true;
                        success |= dfs(edge,temp.y,history);
                    }
                    if (temp.y == start) {
                        if (temp.x == 1) return true;
                        success |= dfs(edge,temp.x,history);
                    }
                    history[i] = false;
            }
            if (success) {
                break;
            }
        }
        return success;
    }

    public static long solve (int n, int[] u, int[] v, int[] w) {
        // write code here
        int m = u.length;
        boolean[] history = new boolean[n + 1];
        //逐个删除一条边,边的两个节点肯定不连接，然后判断两个边各有多少连同量
        long max = 0;
        for (int i = 0;i < m;i++) {
            //尝试删除第i条边
            long cost = useUF(n,u,v,w,i,u[i],v[i]);
            max = Math.max(max,cost);
        }
        return max;
    }
    public static int useUF (int n ,int[] u, int[] v, int[] w,int delIdx,int a,int b) {
        UFInt uf = new UFInt(n + 1);
        for (int i = 0;i < u.length;i++) {
            if (i == delIdx) {
                continue;
            }
            uf.union(u[i],v[i]);
        }
        int countA = 0,countB = 0;
        for (int i = 1;i <= n;i++) {
            if (uf.isSameSet(a,i)) {
                countA++;
            }
            if (uf.isSameSet(b,i)) {
                countB++;
            }
        }
        return countA * countB * w[delIdx];
    }
//    public static int connected(int start,int[] u, int[] v, int[] w,boolean[] history,int delIdx) {
//        if (history[start]) {
//            return 0;
//        }
//        int count = 1;
//        history[start] = true;
//        for (int i = 0;i < u.length;i++) {
//            if (i == delIdx) continue;
//            if (u[i] == start || v[i] == start) {
//                count += connected(u[i] == start ? v[i] : u[i],u,v,w,history,delIdx);
//            }
//        }
//        history[start] = false;
//        return count;
//    }

    class Point {
        int x;
        int y;
        public Point(int x,int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return ("#" + this .x +"#" + this.y +"#").hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Point) {
                return this.x == ((Point)obj).x && this.y == ((Point)obj).y;
            }
            return  false;
        }
    }
    public long solve2 (int n, int[] u, int[] v, int[] w) {
        // write code here
        //求任意两点间最长路径
        long theLongest = 0;
        //构造邻接矩阵
        int[][] mars = new int[n + 1][n + 1];
        //记录下两个节点间的路径
        HashMap<Point,Integer> map = new HashMap<>();
        HashMap<Integer,Integer> linC = new HashMap<>();
        for (int i = 0;i < u.length;i++) {
            linC.put(u[i],linC.getOrDefault(u[i],0) + 1);
            linC.put(v[i],linC.getOrDefault(v[i],0 + 1));
            theLongest = Math.max(theLongest,w[i]);
            mars[u[i]][v[i]] = w[i];
            mars[v[i]][u[i]] = w[i];
            int min = Math.min(u[i],v[i]);
            int max = Math.min(u[i],v[i]);
            map.put(new Point(min,max),w[i]);
        }
        //遍历得到每个点的最长点
        for (int i = 1;i <= n;i++) {
            if (linC.get(i) > 1) continue;
            theLongest = Math.max(theLongest,ds(mars,i,map));
            if (map.size() >= (n * (n - 1))) {
                break;
            }
        }
        return theLongest;
    }
    public long ds(int[][] mars,int start,Map<Point,Integer> map) {
        //从start点出发
        int n = mars.length;
        boolean[] history = new boolean[n];
        long theLongest = 0;
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(start,0));
        while (!queue.isEmpty()) {
            Point temp = queue.poll();
            int cost = temp.y;
            theLongest = Math.max(theLongest,cost);
            history[temp.x] = true;
            for (int i = 1;i < n;i++) {
                if (!history[i] && mars[temp.x][i] > 0) {
                    int min = Math.min(start,i);
                    int max = Math.max(start,i);
                    map.put(new Point(min,max),cost + mars[temp.x][i]);
                    queue.add(new Point(i,cost + mars[temp.x][i]));
                }
            }
        }
        return theLongest;
    }


    public static void main(String[] args) {
        int[] u = {1,4,5,4};
        int[] v = {5,1,2,3};
        int[] w = {9,25,30,8};
        System.out.println(solve(5,u,v,w));
    }
}

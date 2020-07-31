package com.company;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.*;

public class Coins {
    public static int characterReplacement(String s, int k) {
        if (s == null || s.length() == 0) return 0;
        int max = 0;
        int left = 0, right = -1;
        int[] chaMap = new int[26];
        int maxExistedCount = 1;
        char maxExistedChr = s.charAt(0);
        int windowsSize = 1;
        while (right < s.length() - 1) {
            right++;
            //更新 最多字符的个数 和 最多字符
            chaMap[s.charAt(right) - 'A'] += 1;
            if (chaMap[s.charAt(right) - 'A'] > maxExistedCount) {
                maxExistedCount = chaMap[s.charAt(right) - 'A'];
            }
            //当前窗口的长度
            windowsSize = right - left + 1;
            if (windowsSize - maxExistedCount > k) {
                //窗口扩张;
                //更新 最多字符的个数 和 最多字符
                chaMap[s.charAt(left) - 'A'] -= 1;
                left++;
            }
        }
        return s.length() - left;
    }
    public static boolean loongAndShortMatched(String loong,String shoort) {

        if (loong.length() < shoort.length()) return false;
        int i =0, j =0;
        while (i < loong.length() && j < shoort.length()) {
            if (loong.charAt(i) == shoort.charAt(j)) {
                j++;
            }
            i++;

        }
        return j == shoort.length() ? true : false;
    }
    public static String strCompare(String a,String b) {
        if (a == null || a.equals("")) return b;
        if (a.length() == b.length()) {
            int i = 0,j=0;
            while (i < a.length() && j < b.length()) {
                if (a.charAt(i) == b.charAt(j)) {
                    i++;
                    j++;
                }
                else if (a.charAt(i) < b.charAt(j)) {
                    return a;
                }
                else {
                    return b;
                }
            }
        }
        return a.length() - b.length() > 0 ? a : b;
    }
    public static String findLongestWord(String s, List<String> d) {
        if (s == null || d == null || d.size() == 0) return null;
        String rs = null;
        for (int i = 0;i < d.size();i++) {
            String temp = d.get(i);
            if (loongAndShortMatched(s,temp)) {
                rs = strCompare(rs,temp);
            }
        }
        return rs;
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k > nums.length) return new int[0];
        int[] rs = new int[nums.length - k + 1];
        int idx = 0;
//        Queue<Integer> q = new LinkedList<>();
        Deque<Integer> q = new LinkedList<>();
        int right = 0;
        while (right < nums.length) {
            while (!q.isEmpty() && right - q.peekFirst() > k - 1) {
                q.pollFirst();
            }
            while (!q.isEmpty() && nums[q.getLast()] < nums[right]) {
                q.pollLast();
            }
            q.addLast(right);
            if (right >= k - 1) {
                rs[idx++] = nums[q.peekFirst()];
                //System.out.print("->" + nums[q.peekFirst()]);
            }
            right++;
        }
        return rs;
    }


    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        int right = -1 , left = 0;
        int[] chrsExistedCount = new int[256];
        int max = 0;
        while (right < s.length() - 1) {
            right++;
            chrsExistedCount[s.charAt(right) - ' ']++;

            while(chrsExistedCount[s.charAt(right) - ' '] > 1) {
                chrsExistedCount[s.charAt(left) - ' ']--;
                left++;
            }
            max = Math.max(max,right - left + 1);
        }
        return max;
    }
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums == null || nums.length == 0) return false;
        int lf = 0, rh = -1;
        Set<Integer> set = new HashSet<>();
        while (rh < nums.length) {
            if (rh == nums.length - 1) break;
            if (rh  < Math.min(nums.length - 1,lf + k)) {
                rh++;
                if (set.contains(nums[rh])) {return true;}
                else set.add(nums[rh]);
            }
            else {
                set.remove(nums[lf]);
                lf++;
            }
        }
        return false;
    }
    public static int minSubArrayLen(int s, int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int min = Integer.MAX_VALUE;
        int left = 0 , right = -1 , sum = 0;
        while (left < nums.length) {
            if (sum < s && right < nums.length - 1) {
                right++;
                sum += nums[right];
            }
            else {
                if (sum >= s) {
                    min = Math.min(min,right - left + 1);
                }
                sum -= nums[left];
                left++;
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    public static boolean inOrdJudgeMirror(TreeNode root) {
        if (root == null) return true;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode c = root;
        List<TreeNode> inOrderList = new ArrayList<>();
        while (!stack.isEmpty() || c != null) {
            if (c != null) {
                stack.push(c);
                c = c.left;
            }
            else {

                c = stack.pop();
                //print c
                inOrderList.add(c);
                c = c.right;
            }
        }
        //判断遍历的结果是否是回文串
        int size = inOrderList.size();
        int left = 0 , right = size - 1;
        while (left <= right) {
            if (inOrderList.get(left) != inOrderList.get(right)) return false;
            left++;
            right--;
        }
        return true;
    }

    public boolean isSymmetric(TreeNode root) {
        return inOrdJudgeMirror(root);
    }
    //判断两颗二叉树是否是对称的
    public static boolean mirrors(TreeNode t1,TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 != null && t2 != null) {
            if (t1.val == t2.val) {
                return mirrors(t1.left,t2.right) && mirrors(t1.right,t2.left);
            }
        }
        return false;
    }

    public static int smallestDifference(int[] a, int[] b) {
        if (a == null || b == null || a.length == 0 || b.length == 0) return -1;
        int minDiff = Integer.MIN_VALUE , aIdx = 0 , bIdx = 0;
        Arrays.sort(a);
        Arrays.sort(b);
        while (aIdx < a.length && bIdx < b.length) {

            minDiff = Math.max(minDiff,-Math.abs(a[aIdx] - b[bIdx]));

            if (a[aIdx] < b[bIdx]) {
                aIdx++;
            }
            else {
                bIdx++;
            }
        }
        return Math.abs(minDiff);
    }
    public static int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (int i = 0;i < stones.length;i++) queue.add(stones[i]);
        int x , y;
        while (queue.size() > 1) {
            x = queue.poll();
            y = queue.poll();
            if (x != y) queue.add(Math.abs(x - y));
        }
        return queue.isEmpty() ? 0 : queue.peek();
    }
    //
    public static int[] prevPermOpt1(int[] A) {
        if ( A == null || A.length == 0) return A;

        for (int i = A.length - 1;i > -1;i--) {
            //较低的较大位
            int maxId = -1,maxVal = -1;
            for (int j = i + 1 ;j < A.length; j++) {
               if (A[i] > A[j]) {
                   if (maxVal < A[j]) {
                       maxId = j;
                       maxVal = A[j];
                   }
               }
            }
            //交换
            if (maxId != -1) {
                A[maxId] = A[maxId] ^ A[i];
                A[i] = A[maxId] ^ A[i];
                A[maxId] = A[maxId] ^ A[i];
                break;
            }
        }

        //for (int i = 0;i < A.length;i++) System.out.print("," + A[i]);
        return A;
    }

    static class CostRecord {
        int costDiff;
        int index;
        public CostRecord(int diff,int index) {
            this.costDiff = Math.abs(diff);
            this.index = index;
        }
    }
    public static int twoCitySchedCost(int[][] costs) {
        int sumA = 0 , sumB = 0;
        int remainA = costs.length / 2 , remainB = costs.length / 2;
        PriorityQueue<CostRecord> queue = new PriorityQueue<>(new Comparator<CostRecord>() {
            @Override
            public int compare(CostRecord o1, CostRecord o2) {
                return o2.costDiff - o1.costDiff;
            }
        });
        for (int i = 0 ;i < costs.length;i++) {
            queue.add(new CostRecord(costs[i][0] - costs[i][1],i));
        }
        while (!queue.isEmpty()) {
            CostRecord cr = queue.poll();
            int costA = costs[cr.index][0] , costB = costs[cr.index][1];
            if (remainA > 0 && remainB > 0) {
                //随便选
                if (costA < costB) {
                    //选 A
                    remainA--;
                    sumA += costA;
                }
                else {
                    remainB--;
                    sumB += costB;
                }
            }
            else if (remainA > 0) {
                //只能选A
                remainA--;
                sumA += costA;
            }
            else {
                //选B
                remainB--;
                sumB += costB;
            }
        }
        return sumA + sumB;
    }
    public static void main(String[] args) {
        int[][] a = {{10,20},{30,200},{400,50},{30,20}};
        System.out.println(twoCitySchedCost(a));
    }
}
//Tang@Gong123
// 3 1 1 3
// 1 3 1 3

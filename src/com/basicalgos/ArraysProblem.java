package com.basicalgos;

import java.util.*;

public class ArraysProblem {
    //滑动窗口的最大值
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int index = 0;
        int[] maxArray = new int[nums.length - k + 1];
        Deque<Integer> queue = new LinkedList<>();
        for (int i = 0;i < nums.length;i++) {
            //淘汰队列中过期值
            while (!queue.isEmpty() && i - queue.peekFirst() >= k) {
                queue.pollFirst();
            }
            //更新队列中最大值
            while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) {
                queue.pollLast();
            }
            queue.addLast(i);
            //输出窗口最大值
            if (i >= k - 1) {
                maxArray[index++] = nums[queue.peekFirst()];
            }
        }
        return maxArray;
    }

    //循环打印矩阵
    public static int[] spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return new int[0];
        int m = matrix.length , n = matrix[0].length , idx = 0;
        int[] rs = new int[m * n];
        boolean[][] history = new boolean[m][n];
        int[][] tensor = {{0,1},{1,0},{0,-1},{-1,0}};
        int i = 0,j = 0 , tensorP = 0;
        while (idx < m * n) {
            rs[idx++] = matrix[i][j];
            history[i][j] = true;
            //切换方向
            if (i + tensor[tensorP][0] < 0 || j + tensor[tensorP][1] < 0 ||
                    i + tensor[tensorP][0] >= m || j + tensor[tensorP][1] >= n
                    || history[i + tensor[tensorP][0]][j + tensor[tensorP][1]]
            ) {
                tensorP++;
                tensorP %= tensor.length;
            }
            i = i + tensor[tensorP][0];
            j = j + tensor[tensorP][1];
        }
        return rs;
    }

    /**
     * 最多可以参会的个数
     *
     * 给你一个数组 events，其中 events[i] = [startDayi, endDayi] ，表
     * 示会议 i 开始于 startDayi ，结束于 endDayi 。
     * 你可以在满足 startDayi <= d <= endDayi 中的任意一天 d 参加会议 i 。注意，一天只能参加一个会议。
     *
     * 请你返回你可以参加的 最大 会议数目。
     * 假设当前天数为day,
     * 核心思想是从当前可选择的空间中选择截止时间最早的
     * 一定要先按开始时间排序,不要全升序,为的是构造 可选的全空间
     * 例如数据:[1,2],[2,2],[1,3]
     * 如果是全升序的情况下，[2,2]的优先级就比[1,3] 要低.
     * */
    public static int maxEvents(int[][] events) {
        if (events == null || events.length == 0) return 0;
        int count = 0;
        //按开始时间升序
        Arrays.sort(events,(o1, o2) -> o1[0] - o2[0]);
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int idx = 0 , day = 1 , n = events.length;
        while (idx < n || !pq.isEmpty()) {
            //把当前可选的会议加入到最小堆中，根据截止时间排序
            while (idx < n && events[idx][0] <= day) {
                //按截至时间排序
                pq.add(events[idx][1]);
                idx++;
            }
            //排除掉过期的会议
            while (!pq.isEmpty() && pq.peek() < day) {
                pq.poll();
            }
            //如果有可选的会议，则弹出(选择)这个
            if (!pq.isEmpty()) {
                pq.poll();
                count++;
            }
            //无论是否有可选的都应该天数 + 1
            day++;
        }
        return count;
    }

    /**
     * 打气球的最大收益
     * dp[i][j] 表示[i...j] 区间内的最大收益
     * 假设 left 为 [i...j] 的左邻、right 为 [i...j] 的右邻;
     * 我们尝试从区间[i...j] 中找到一个点,这个点是这个区间中最后一个被打破的
     * 假设这个点在 k 处，因此当前的收益为dp[i][k - 1] + dp[k + 1][j] + left * right * nums[k];
     * k in [i,...,j] 然后遍历取最大值而即可。
     * 注意:
     *  要假设 K 点 在区间[i,...,j] 中是最后一个被弄破的，可以推出 在 弄破 k 点之前 [i,...,k - 1] 的右邻和
     *  [k + 1,...,j] 的左邻 肯定是 nums[k];
     *  相反如果假设是第一个弄破的 k 点，不太好确定，k点的左邻和右邻到底是left、right ，还是[i,...,j] 上的某一点
     * */
    public static int maxCoins(int[] nums) {
        //异常检测
        if (nums == null || nums.length == 0) return 0;
        int n = nums.length;
        //初始化dp矩阵
        int[][] dp = new int[n][n];
        /**
         * 一种编程技巧，处理区间问题
         * [i++,j++] 这种方式斜向下45度.等价于逐步扩大搜索的区间
         * 还有一点：动态规划能够避免重复的子结构运算，因此我们要从底向上遍历
         * 也就是说从小区间遍历到大空间中
         * */
        for (int cj = 0;cj < n;cj++) {
            int i = 0 , j = cj;
            while (i < n && j < n) {
                int left = i == 0 ? 1 : nums[i - 1];
                int right = j == n - 1 ? 1 : nums[j + 1];
                if (i == j) {
                    dp[i][j] = left * right * nums[i];
                }
                else {
                    //此时i与j不相等,寻找最优值.
                    for (int k = i;k <= j;k++) {
                        //分成两部分 dp[i][k - 1] dp[k + 1][j];
                        int wins = left * right * nums[k];
                        //以下两个 if 是 为了处理 k==i 或 k == j 的两个边界情况
                        if (i <= k - 1) {
                            wins += dp[i][k - 1];
                        }
                        if (j >= k + 1) {
                            wins += dp[k + 1][j];
                        }
                        dp[i][j] = Math.max(dp[i][j],wins);
                    }
                }
                //斜向下移动
                i++;
                j++;
            }
        }
        return dp[0][n - 1];
    }

    /**
     * 最少的剑射爆气球
     * */
    public static int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0) return 0;
        Arrays.sort(points,(o1, o2) -> o1[0] - o2[0]);
        int idx = 0 , swards = 0 , n = points.length;
        while (idx < n) {
            int end = points[idx][1];
            while (idx < n && points[idx][0] <= end) {
                System.out.print(idx + " ");
                end = Math.min(end,points[idx][1]);
                idx++;
            }
            swards++;
            System.out.println();
        }
        return swards;
    }

    /**
     * 数对的最长长度
     * 最长的升序序列的长度
     * 二分 -> 时间复杂度 O(N logN)
     * 给出 n 个数对。 在每一个数对中，第一个数字总是比第二个数字小。
     *
     * 现在，我们定义一种跟随关系，当且仅当 b < c 时，数对(c, d) 才可以跟在 (a, b) 后面。我们用这种形式来构造一个数对链。
     *
     * 给定一个对数集合，找出能够形成的最长数对链的长度。你不需要用到所有的数对，你可以以任何顺序选择其中的一些数对来构造。
     * */
    public static int findLongestChain(int[][] pairs) {
        //异常检测
        if (pairs == null || pairs.length == 0) return 0;
        //排序
        Arrays.sort(pairs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] != o2[0]) {
                    return o1[0] - o2[0];
                }
                return o1[1] - o2[1];
            }
        });
        int n = pairs.length , left = 0 ,right = 0;
        int[] ends = new int[n];
        ends[0] = pairs[0][1];
        for (int i = 0;i < n;i++) {
            int shouldIndex = bfind(ends,left,right,pairs[i][0]);
            if (shouldIndex > right) {
                right++;
                ends[shouldIndex] = Integer.MAX_VALUE;
            }
            ends[shouldIndex] = Math.min(pairs[i][1],ends[shouldIndex]);
        }
        return right + 1;
    }
    public static int bfind (int[] pairs , int left , int right,int target) {
        while (left <= right) {
            int middle =  (left + right) / 2;
            if (pairs[middle] < target) {
                left = middle + 1;
            }
            else {
                right = middle - 1;
            }
        }
        return left;
    }

    /**
     * 升序且等长数组的上中位数
     * 数组等长且各自升序，上中位数
     *
     * 1 ，2 ，3
     * 1~，2~，3~
     * 若 2 < 2~ 则中位数应该在[2,3] 与 [1~,2~] 之间
     * */
    public static int getUMedium (int[] nums1,int[] nums2,int l1,int r1,int l2,int r2) {
        //异常检测
        if (nums1 == null || nums2 == null || r1 - l1 != r2 - l2) return Integer.MIN_VALUE;

        int n = nums1.length , m = nums2.length;
        while (l1 <= r1) {
            int m1 = l1 + (r1 - l1) / 2;
            int m2 = l2 + (r2 - l2) / 2;
            //offset用来记录偏移量,奇偶长度带来的偏移不同。
            int offset = ((r1 - l1 + 1) & 1) == 0 ? 1 : 0;
            //当剩余一个元素，或相等时，直接返回
            if (nums1[m1] == nums2[m2] || l1 == r1) {
                return Math.min(nums1[m1],nums2[m2]);
            }
            else if (nums1[m1] < nums2[m2]) {
                l1 = m1 + offset;
                r2 = m2;
            }
            else {
                //nums[m1] > nums2[m2]
                r1 = m1;
                l2 = m2 + offset;
            }
        }
        return -1;
    }

    /**
     * 两个有序数组的第K个数
     * tips:
     * 1.数组不一定等长
     * */
    public static int findK (int[] nums1,int[] nums2,int k) {
        //异常检测
        if (nums1 == null || nums2 == null) return Integer.MIN_VALUE;
        int[] loogest = nums1.length > nums2.length ? nums1 : nums2;
        int[] shortest = nums1.length <= nums2.length ? nums1 : nums2;
        int n = loogest.length , m = shortest.length;
        //无效请求
        if (k < 1 || k > n + m) {
            return Integer.MIN_VALUE;
        }
        //若k < m ，则肯定为loog[0,k-1]与shortest[0,k-1]的上中位数
        //因为两段总共具有 2k 个元素
        if (k <= m) {
            return getUMedium(loogest,shortest,0,k - 1,0,k - 1);
        }
        else if (k > n) {
            if (shortest[k - n - 1] >= loogest[n - 1]) {
                return shortest[k - n - 1];
            }
            if (loogest[k - m - 1] >= shortest[m - 1]) {
                return loogest[k - m - 1];
            }
            return getUMedium(loogest,shortest,k - m,n - 1,k - n,m - 1);
        }
        else {
            //k > m && k < n
            if (loogest[k - m - 1] >= shortest[m - 1]) {
                return loogest[k - m - 1];
            }
            return getUMedium(loogest,shortest,k - m,k - 1,0,m - 1);
        }
    }
}

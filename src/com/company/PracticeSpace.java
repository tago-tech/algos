package com.company;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PracticeSpace {
    //简单二分查找
    public static int search(int[] nums, int target) {
        int left = 0,right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            else if (nums[mid] < target) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return -1;
    }
    //lc : 1101 求最小能够 在 D 天以内 完成运输任务
    //没有更优的办法，从起步值逐渐增大，采用 二分 在上限 sum 和 下限 maxWeight 之间搜索可能值
    public static boolean canShipIt (int[] weights,int D,int vmCanOverOn) {
        int idx = 0 , haveOnBoard = weights[0];
        int d = D;
        //然后以这个承载量装，看是否能够装满
        while (idx < weights.length) {
            while (idx < weights.length - 1 && weights[idx + 1] + haveOnBoard <= vmCanOverOn) {
                idx++;
                haveOnBoard += weights[idx];
            }
            //一批货物已经装满
            d--;
            if (idx == weights.length - 1) break;
            haveOnBoard = weights[++idx];
        }
        if (d >= 0) {
            return true;
        }
        return false;
    }
    public static int shipWithinDays(int[] weights, int D) {
        int sum = 0 , maxValye = Integer.MIN_VALUE;
        for (int i = 0;i < weights.length;i++) {
            sum += weights[i];
            maxValye = Math.max(maxValye,weights[i]);
        }
        int vmCanOverOn = sum;
        int left = maxValye,right = sum;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (canShipIt(weights,D,mid)) {
                //记录当时的承载量
                vmCanOverOn = Math.min(vmCanOverOn,mid);
                //尝试新的
                right = mid - 1;
            }
            else {
                //不能承载
                left = mid + 1;
            }
        }
        return vmCanOverOn;
    }

    //lc : 875 猴子吃香蕉
    /**
     * 和上一题一样，先计算出最小 和 最大 再在 这个范围内搜索
     * */
    public static int minEatingSpeed(int[] piles, int H) {
        int maxPilesHeap = Integer.MIN_VALUE;
        for (int i = 0;i < piles.length;i++) {
            maxPilesHeap = Math.max(maxPilesHeap,piles[i]);
        }
        int minK = maxPilesHeap, midBeLR = 0;
        int left = 1,right = maxPilesHeap;
        while (left <= right) {
            midBeLR = (left + right) / 2;
            int idx = 0 , speedH = 0;
            while (idx < piles.length) {
                int remain = piles[idx];
                //一个一个减的方法太慢
                speedH += remain > midBeLR ? (remain / midBeLR) + 1 : 1;
                idx++;
            }
            if (speedH <= H) {
                minK = Math.min(minK,midBeLR);
                right = midBeLR - 1;
            }
            else {
                left = midBeLR + 1;
            }
        }
        return minK;
    }
    //lc 搜索 二分法 元素的下标，或者不存在时，应该插入的位置
    //当不存在时，right < left,并且 left 指向 大于该点 的第一个值
    //right 指向小于该点的第一个下标,
    public static int searchInsert(int[] nums, int target) {

        int left = 0 , right = nums.length - 1 , midx = 0;
        while (left <= right) {
            midx = (left + right) / 2;
            if (nums[midx] < target) {
                left = midx + 1;
            }
            else if (nums[midx] == target) {
                return midx;
            }
            else {
                right = midx - 1;
            }
        }
        return left;
    }
    //尤其考试时候，记得用 long 或者 double 小技巧，可以防止溢出
    public static double myPow(double x, int n) {
        long absN = n;
        if (absN < 0) {
            x = 1.0 / x;
            absN = -absN;
        }
        double sum = 1.0f;
        while (absN > 0) {
            if ((absN & 1) == 1) {
                sum *= x;
            }
            absN >>= 1;
            x *= x;
        }
        return sum;
    }

    //lc:222 寻找完全二叉树中的节点个数
    public static int countNodes(TreeNode root) {
        int count = 0;
        //h表示最近访问并打印过的节点，c表示当前的栈顶节点
        TreeNode h = root,c = null;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            c = stack.peek();
            if (c.left != null && h != c.left && h != c.right) {
                //当前c节点的左右子树都未被访问过
                stack.push(c.left);
            }
            else if (c.right != null && h != c.right) {
                //已经访问过 c 的 左子树，这时候该访问右子树
                stack.push(c.right);
            }
            else {
                // c 的两颗子树都已经被访问过，弹出 c 并打印；
                count++;
                stack.pop();
                h = c;
            }
        }
        return count;
    }

    //lc:274 h指数
    public static int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) return 0;
        Arrays.sort(citations);
        int maxHi = 0;
        int left = 0,right = citations.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            int idx = searchInsert(citations,mid);
            System.out.println(mid + "- > 搜索得到插入位置 " + idx);
            //当前引用次数大于mid =
            while (idx > 0 && citations[idx - 1] == mid) {
                idx--;
            }
            //引用次数小于mid的有几篇
            if (citations.length - idx >= mid) {
                //这个引用次数合规
                maxHi = Math.max(maxHi,mid);
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return maxHi;
    }

    //稀疏字典里找字符串s，就是让 left,right,mid 三个指针遇到空字符串时，移动即可
    public static int findString(String[] words, String s) {
        if (words == null || words.length == 0) return -1;
        int left = 0 , right = words.length - 1;
        while (left <= right) {
            while (left <= right && words[left].equals("")) left++;
            while (left <= right && words[right].equals("")) right--;
            int mid = (left + right) / 2;
            while (mid > left && words[mid].equals("")) mid--;
            if (words[mid].compareTo(s) < 0) {
                left = mid + 1;
            }
            else if (words[mid].equals(s)) {
                return mid;
            }
            else {
                right = mid - 1;
            }
        }
        return -1;
    }

    /*
    * lc : 1326 ，同样也是区间问题，在所能选择的区间里面选择覆盖范围，最大最远的
    * 有点类似于跳跃游戏
    * 首先将每个点，构造成 <start,end> 形式，按start 升序,end降序排列
    * 如果遍历到下一个 <> 的 最远点小于当前点就可以直接舍弃
    * 如果下一个区间，当前maxAreaSize碰不到，就说明中间出现了空袭,return -1
    * 在能够选择的空间中，选择一个最远的。
    * end 降序排列的时候，能保证 对于相同起点的，如 <0,6> <0,5> 肯定去最优的 <0,6>
    * 但是对于 <1，4> <2,5> ,这种如果当前的maxSize >= 2 ,就不用一个一个取
    *  */
    public static int minTaps(int n, int[] ranges) {
        if (ranges == null || ranges.length == 0) return 0;
        int used = 0 , idx = 0 , maxAreaSize = 0;
        int[][] waterControlAreas = new int[n + 1][2];
        for (int i = 0;i < ranges.length;i++) {
            waterControlAreas[i][0] = Math.max(0,i - ranges[i]);
            waterControlAreas[i][1] = Math.min(n,i + ranges[i]);
        }
        Arrays.sort(waterControlAreas,(o1, o2) -> o1[0] == o2[0] ? o2[1] - o1[1] : o1[0] - o2[0]);
        while (idx <= n && maxAreaSize < n) {
            while (idx <= n && waterControlAreas[idx][1] <= maxAreaSize) {
                //这些区域已经覆盖,就不需要选择这些水龙头
                idx++;
            }
            if (maxAreaSize < n && waterControlAreas[idx][0] > maxAreaSize) {
                //中间出现空隙
                return -1;
            }
            //在所能选择的区域中选择一个最大的
            int maxRecord = maxAreaSize;
            while (idx <= n && waterControlAreas[idx][0] <= maxRecord) {
                maxAreaSize = Math.max(waterControlAreas[idx][1],maxAreaSize);
                idx++;
            }
            used++;
        }
        return used;
    }

    /**
     * lc:1338 比较简单的一道题，每次删除出现次数最多的就行
     * 使用map计数，然后用优先队列取出
     * 小技巧是 以后记得用优先队列排序，
     * 最小堆的复杂度时 logn，一个个遍历的话时 n
     * long - > int,优先队列
     * */
    public static int minSetSize(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int removeNums = 0 , haveBeRm = 0;
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0;i < arr.length;i++) {
            map.put(arr[i],map.getOrDefault(arr[i],0) + 1);
        }
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> map.get(o2) - map.get(o1));
        for (int i : map.keySet()) {
            priorityQueue.add(i);
        }
        while (haveBeRm < arr.length / 2) {
            int mostNums = priorityQueue.poll();
            //删除mostNums
            haveBeRm += map.get(mostNums);
            removeNums++;
        }
        return removeNums;
    }

    /**
     * lc : 3
     * 双指针法
     * 当移动过程中没有重复字符出现时，right一直++。
     * 当出现重复字符时，让left 一直--，直到重复字符消失
     * 并且在移动的过程中一直记录最大的窗口值
     * */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        //双指针法
        int maxLen = 1;
        int[] chrMp = new int[256];
        int left = 0,right = -1;
        while (right < s.length() - 1) {
            right++;
            chrMp[s.charAt(right) - ' ']++;
            while (chrMp[s.charAt(right) - ' '] > 1) {
                chrMp[s.charAt(left) - ' ']--;
                left++;
            }
            maxLen = Math.max(maxLen,right - left + 1);
        }
        return maxLen;
    }

    /**
     * lc : 63, 注意障碍物同时会影响 边界值
     * 如果起点，终点 为障碍物，注定不可达
     * */
    public static int uniquePaths(int m, int n) {
        if (m == 0 || n == 0) return -1;
        if (m == 1 || n == 1) return 1;
        if (m < n) {
            m = m ^ n;
            n = m ^ n;
            m = m ^ n;
        }
        int[] dp = new int[n];
        for (int i = 0;i < n;i++) dp[i] = 1;
        for (int i = 1;i < m;i++) {
            for (int j = 1;j < n;j++) {
                dp[j] += dp[j - 1];
            }
        }
        return dp[n - 1];
    }
    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length,n = obstacleGrid[0].length , var = 1;
        int[] dp = new int[n];
        for (int i = 0;i < n;i++) {
            if (obstacleGrid[0][i] == 1) {
                var = 0;
            }
            dp[i] = var;
        }
        for (int i =  1;i < m;i++) {
            for (int j = 0;j < n;j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0;
                    continue;
                }
                //障碍物
                if (obstacleGrid[i - 1][j] == 1) {
                    dp[j] = 0;
                }
                if (j > 0 && obstacleGrid[i][j - 1] == 0) {
                    dp[j] += dp[j - 1];
                }
            }
        }
        return dp[n - 1];
    }

    //简单用HashMap 和 优先队列 选出 前 top K 值
    public static int[] topKFrequent(int[] nums, int k) {
        int[] rex = new int[k];
        HashMap<Integer,Integer> mapFrequent = new HashMap<>();
        for (int i = 0;i < nums.length;i++) {
            mapFrequent.put(nums[i],mapFrequent.getOrDefault(nums[i],0) + 1);
        }
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> mapFrequent.get(o2) - mapFrequent.get(o1));
        for (int key : mapFrequent.keySet()) {
            priorityQueue.add(key);
        }
        while (k > 0) {
            rex[--k] = priorityQueue.poll();
        }
        return rex;
    }

    //lc : 53 使用动态规划，dp[i] 表示 以 i 结尾的子序列中，最大
    //当然，dp[i] 依赖于 dp[i - 1],如果dp[i - 1] > 0,则加上更好
    public static int maxSubArray(int[] nums) {
        int maxSums = nums[0];
        int dp = 0 , lastDp = 0;
        for (int i = 1;i <= nums.length;i++) {
            dp = nums[i - 1];
            if (lastDp > 0) {
                dp += lastDp;
            }
            lastDp = dp;
            maxSums = Math.max(maxSums,dp);
        }
        return maxSums;
    }

    //lc : 503 下一个更大的元素
    /**
     * 使用单调队列，但是可以遍历两边数组，确保返程，仍然可以搜索到离得最近的最大值
     *
     * */
    public static int[] nextGreaterElements(int[] nums) {
        int[] rex = new int[nums.length];
        int max = Integer.MIN_VALUE,maxId = nums.length - 1;
        if (nums == null || nums.length == 0) return rex;
        Deque<Integer> deque = new LinkedList<>();
        for (int j = 0;j <= nums.length  + maxId;j++) {
            int i = j % nums.length;
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                rex[deque.pollLast()] = nums[i];
            }
            deque.addLast(i);
            if (nums[i] > max) {
                max = nums[i];
                maxId = i;
            }
        }
        while (!deque.isEmpty()) {
            int pollId = deque.pollFirst();
            if (nums[pollId] >= max) {
                rex[pollId] = -1;
            }
        }
//        for (int i = 0;i< nums.length;i++) {
//            System.out.print("," + rex[i]);
//        }
//        System.out.println();
        return rex;
    }

    //lc:1109 残差法
    /**
     * booking[i,j,k] 表示在 i - j 站点上增加了 k 人，就表示 在 j + 1 站减少了 k 人
     * 其实是用了 等差数列， delta of delata 算法
     * */
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] booksStatics = new int[n];
        for (int i = 0;i < bookings.length;i++) {
            int start = bookings[i][0] - 1 , end = bookings[i][1] , count = bookings[i][2];
            booksStatics[start] += count;
            if (end < booksStatics.length) {
                booksStatics[end] -= count;
            }
        }
        for (int i = 1;i < booksStatics.length;i++) {
            booksStatics[i] += booksStatics[i - 1];
        }
        return booksStatics;
    }

    //lc : 每 k 个 一组 反转部分链表
    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;
        // pre 指向前一个
        ListNode pre = null , pionner = head ,soilder = head,nextSoild = null , nextPre;
        int idx = k;
        while (pionner != null) {
            idx = k;
            soilder = pionner;
            while (idx > 0 && pionner != null) {
                pionner = pionner.next;
                idx--;
            }
            if (idx > 0) break;
            if (pre == null) {
                //更新pre
                pre = soilder;
                while (soilder != pionner) {
                    nextSoild = soilder.next;
                    soilder.next = head;
                    head = soilder;
                    soilder = nextSoild;
                }
                //连接到下一组
                pre.next = pionner;
            }
            else {
                nextPre = pre.next;
                pre.next = pionner;
                while (soilder != pionner) {
                    nextSoild = soilder.next;
                    soilder.next = pre.next;
                    pre.next = soilder;
                    soilder = nextSoild;
                }
                pre = nextPre;
            }
        }
//        while (head != null) {
//            System.out.print("-> " + head.val);
//            head = head.next;
//        }
        return head;
    }

    //lc : 97 动态规划，字符串的交错组成
    /**
     * dp[i][j] 表示 s3[0,...,i + j - 1] 能够由 s1[0,..,i - 1] 和 s2[0,..,j - 1]组成
     * 如果 s3[i + j - 1] == s1[i-1] 看看dp[i-1][j] == true就行了
     * 如果 s3[i+j-1]==s2[j-1] 只用看dp[i][j-1]
     * */
    public static boolean isInterleave(String s1, String s2, String s3) {
        if (s3 == null || s1 == null || s2 == null) return false;
        if (s1.length() + s2.length() != s3.length()) return false;
        if (s1.length() == 0) return s2.equals(s3);
        if (s2.length() == 0) return s1.equals(s3);
        int m = s1.length() , n = s2.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        //不使用任何字符
        dp[0][0] = true;
        boolean var = true;
        for (int i = 0;i < m;i++) {
            if (s1.charAt(i) != s3.charAt(i)) {
                var = false;
            }
            dp[i + 1][0] = var;
        }
        var = true;
        for (int i = 0;i < n;i++) {
            if (s2.charAt(i) != s3.charAt(i)) {
                var = false;
            }
            dp[0][i + 1] = var;
        }

        for (int i = 1;i <= m;i++) {
            for (int j = 1;j <= n;j++) {
                //表示 使用 s1[0,..,i - 1] 和 s2[0,...,j - 1] 组成字符串 dp[0,..,i + j - 1]
                if (s3.charAt(i + j - 1) == s1.charAt(i - 1) && dp[i - 1][j]) {
                    dp[i][j] = true;
                }
                if (s3.charAt(i + j - 1) == s2.charAt(j - 1) && dp[i][j - 1]) {
                    dp[i][j] = true;
                }
            }
        }
        return dp[m][n];
    }

    public static List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> rex = new ArrayList<>();
        List<Integer> paths = new ArrayList<>();
        find(root,sum,paths,rex);
        return rex;
    }
    public static void find(TreeNode root,int sum,List<Integer> pathFathes,List<List<Integer>> rex) {
        //当前来到叶子节点
        if (root != null && root.left == null && root.right == null) {
            if (root.val == sum) {
                pathFathes.add(root.val);
                //加入结果集
                rex.add(pathFathes);
            }
            else {
                pathFathes = null;
            }
        }
        //产生分支
        pathFathes.add(root.val);
        List<Integer> leftPaths = new ArrayList<>();
        for (int i:pathFathes) {
            leftPaths.add(i);
        }
        List<Integer> rightPaths = new ArrayList<>();
        for (int i : pathFathes) {
            rightPaths.add(i);
        }

        if (root.left != null) {
            find(root.left,sum - root.val,leftPaths,rex);
        }
        if (root.right != null) {
            find(root.right,sum - root.val,rightPaths,rex);
        }
    }

    //改成相连的，只能 暴力求解了
    /*
    * 原始题目中首尾并不相连，相连之后，可以将首部分情况考虑
    * 继续按 DP 做法
    * */
    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        boolean[] robNor = new boolean[nums.length];
        int value = 0;
        //假设偷第一个元素,偷第一个的时候末尾肯定不偷
        //使得不偷第一个元素为 MIN，这样可以控制第二个元素不能被偷
        int stef = nums[0],norstef = Integer.MIN_VALUE;
        for (int i = 1;i < nums.length - 1;i++) {
            int lastNorStef = norstef;
            //不偷
            norstef = Math.max(lastNorStef,stef);
            //偷
            stef = lastNorStef + nums[i];
        }
        //偷第一个时候的最大值
        value = Math.max(norstef,stef);
        //假设不偷第一个元素，这时候可以偷最后一个元素
        norstef = 0;
        stef = 0;
        for (int i = 1;i < nums.length;i++) {
            int lastNorStef = norstef;
            norstef = Math.max(norstef,stef);
            stef = lastNorStef + nums[i];
        }
        //综合一下最大值
        value = Math.max(value,stef);
        value = Math.max(value,norstef);
        return value;
    }

    //lc 一个普通dfs ,但是要注意剪枝，还有有时候 访问标志可以生下了
    public static boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0) return false;
        if (word == null || word.length() == 0) return true;
        int m = board.length,n = board[0].length;
        for (int i = 0;i < m;i++) {
            for (int j = 0;j < n;j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (boardDfs(board,i,j,word,0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean boardDfs(char[][] board,int x,int y,String word,int indx) {
        //终止条件
        if (x < 0 || y < 0 || x >= board.length  || y >= board[0].length || board[x][y] != word.charAt(indx)) {
            return false;
        }
        indx++;
        if (indx == word.length()) {
            return true;
        }
        char temp = board[x][y];
        board[x][y] = '/';
        boolean canTouch = false;
        //上下左右 四个向量
        int[][] tensor = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int i = 0;i < tensor.length && !canTouch;i++) {
            int sx = x + tensor[i][0] , sy = y + tensor[i][1];
            canTouch |= boardDfs(board,sx,sy,word,indx);

        }
        board[x][y] = temp;
        return canTouch;
    }

    public static int getNumsDightSum(int n ) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    //lc ： 搜索缺失的数，二分搜索加快速度
    public static int missingNumber(int[] nums) {
        int n = nums.length + 1;
        //        int[] bitMap = new int[n / 32 + 1];
        //        for (int i = 0;i < nums.length;i++) {
        //            //先找到应该放在第几块位图中
        //            int segment = nums[i] / 32;
        //            int shift = nums[i] % 32;
        //            bitMap[segment] |= (1 << shift);
        //        }
        //        for (int segment = 0;segment < bitMap.length;segment++) {
        //            for (int shift = 0;shift < 32;shift++) {
        //                if ((bitMap[segment] & (1 << shift) ) == 0) {
        //                    return segment * 32 + shift;
        //                }
        //            }
        //        }
        //二分法搜索
        int left = 0,right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > mid) {
                //说明缺失是发生在左边的
                right = mid - 1;
            }
            else {
                left = left + 1;
            }
        }
        return right + 1;
    }

    //lc 61:扑克牌中的顺子，使用set和最大值最小值判定
    /**
     * 题解稍后写
     * 最大值和最小值的差值肯定不大于5，
     * 出现重复值的情况肯定不可以
     * */
    public boolean isStraight(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int max = 0 , min = 14;
        for (int num : nums) {
            if (num == 0) continue;
            max = Math.max(num,max);
            min = Math.min(num,min);
            if (set.contains(num)) return false;
            set.add(num);
        }
        return max - min > 5;
    }
    //lc : 38 字符串的排列
    /**
     * 尤其在dfs中要注意剪枝
     * 如 abcc 的 排列正常情况下会出现很多重复的，
     * 使用 set记录当前在 idx 选择的情况，对于已经选择过的元素就不再选择
     * 因为上层已经去过重了，下层递归，下层递归分支肯定不一样，相当于
     * 每一层都去重
     * */
    public static String[] permutation(String s) {
        Set<String> set = new HashSet<>();
        List<String> rex = new ArrayList<>();
        boolean[] historys = new boolean[s.length()];
        sStrogdfs(s,0,historys,"",rex);
        return rex.toArray(new String[rex.size()]);
    }
    public static void sStrogdfs(String s,int idx,boolean[] history,String path,List<String> rex) {
        if (idx == s.length()) {
            rex.add(path);
            return;
        }
        Set<Character> chrSet = new HashSet<>();
        for (int i = 0;i < s.length();i++) {
            if (!history[i] && !chrSet.contains(s.charAt(i))) {
                history[i] = true;
                sStrogdfs(s,idx + 1,history,path + s.charAt(i),rex);
                chrSet.add(s.charAt(i));
                history[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        permutation("abcc");
    }
}

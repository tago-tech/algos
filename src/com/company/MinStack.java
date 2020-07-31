package com.company;

import sun.reflect.generics.tree.Tree;

import java.nio.channels.Pipe;
import java.util.*;

public class MinStack {
    public static int f(String s1,String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) return -1;
        if (s1.length() == 0 && s2.length() == 0) return 0;
        int count = 0;
        char[] chr1 = s1.toCharArray();
        char[] chr2 = s2.toCharArray();
        int i = 0 , j = 0 , mi , mj;
        while (i < s1.length()) {
            if (chr1[i] == chr2[i]) {
                i++;
            }
            else {
                boolean successful = false;
                // 找不同的字符
                j = i + 1;
                while (j < s1.length()) {
                    if (chr1[j] != chr2[j] && chr1[j] != chr2[i]) break;
                    j++;
                }
                if (j < s1.length()) {
                    //找到了，并交换
                    chr1[j] = chr2[j];
                    i++;
                    successful = true;
                    count++;

                }
                //找相同的字符
                j = i + 1;
                while (!successful && j < s1.length()) {
                    if (chr1[j] != chr2[j] && chr1[j] == chr2[i]) break;
                    j++;
                }
                if (!successful && j < s1.length()) {
                    i = j + 1;
                    count += 2;
                    successful = true;
                    continue;
                }
                if (!successful) return -1;
            }
        }
        return count;
    }
    public static int[][] merge(int[][] intervals) {
        //排序
        List<List<Integer>> list = new ArrayList<>();
        int i = 0;
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) return o2[1] - o1[1];
                return o1[0] - o2[0];
            }
        });
        while (i < intervals.length) {
            int start = intervals[i][0],end = intervals[i][1];
            //连续向后合并
            while (i < intervals.length - 1 && end >= intervals[i + 1][0]) {
                end = Math.max(end,intervals[i + 1][1]);
                i++;
            }
            //加入结果集
            System.out.println("[" + start + " , "+end +"]");
            List<Integer> temp = new ArrayList<>();
            temp.add(start);
            temp.add(end);
            list.add(temp);
            i++;
        }
        int[][] res = new int[list.size()][2];
        for (i = 0;i < res.length;i++) {
            res[i][0] = list.get(i).get(0);
            res[i][1] = list.get(i).get(1);
        }
        return res;
    }


    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int count = 0;
        int m = grid.length , n = grid[0].length;
        boolean[][] history = new boolean[m][n];
        for (int i = 0;i < m;i++) {
            for (int j = 0;j < n;j++) {
                if (grid[i][j] == '0') continue;
                //为 1
                if (!history[i][j]) {
                    count++;
                    //isLandDfs(grid,i,j,history);
                    norReIncDfs(grid,i,j,history);
                }
            }
        }
        return count;
    }
    public static void isLandDfs(char[][] grid,int i,int j,boolean[][] history) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || history[i][j] || grid[i][j] == '0') {
            return;
        }
        history[i][j] = true;
        isLandDfs(grid,i - 1,j,history);
        isLandDfs(grid,i + 1,j,history);
        isLandDfs(grid,i,j - 1,history);
        isLandDfs(grid,i,j + 1,history);
    }
    static class Position {
        int x;
        int y;
        Position(int x,int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void norReIncDfs(char[][] grid,int i,int j,boolean[][] history) {
        Queue<Position> stack = new LinkedList<>();
//        Stack<Position> stack = new Stack<>();
        history[i][j] = true;
        stack.add(new Position(i,j));
        while (!stack.isEmpty()) {
            Position p = stack.poll();
            if (p.x < 0 || p.y < 0 || p.x >= grid.length || p.y >= grid[0].length || history[p.x][p.y] || grid[p.x][p.y] == '0') {
                continue;
            }
            history[p.x][p.y] = true;
            stack.add(new Position(p.x - 1,p.y));
            stack.add(new Position(p.x + 1,p.y));
            stack.add(new Position(p.x,p.y - 1));
            stack.add(new Position(p.x,p.y + 1));
        }
    }
    public static boolean isValidBST(TreeNode root) {
        return isBinTree(root,Long.MIN_VALUE,Long.MAX_VALUE);
    }
    public static boolean isBinTree(TreeNode root,long less,long than) {
        if (root == null) return true;
        boolean okr = true;
        if (root.val < less || root.val > than) return false;
        if (root.left != null) {
            okr &= root.left.val < root.val ? isBinTree(root.left,less,Math.min(root.val,than)) : false;
        }
        if (root.right != null) {
            okr &= root.right.val > root.val ? isBinTree(root.right,Math.max(root.val,less),than) : false;
        }
        return okr;
    }
    public static boolean isBalanced(TreeNode root) {
        return balanceTreeHeight(root) == -1 ? false : true;
    }
    public static int balanceTreeHeight(TreeNode root) {
        if (root == null) return 0;
        int lh = balanceTreeHeight(root.left);
        int lr = balanceTreeHeight(root.right);
        if (lh == -1 || lr == -1 || Math.abs(lh - lr) > 1) {
            return -1;
        }
        return Math.max(lh,lr) + 1;
    }
    public static int[] pondSizes(int[][] land) {

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        int m = land.length , n = land[0].length;
        boolean[][] history = new boolean[m][n];
        for (int i = 0;i < m;i++) {
            for (int j = 0;j < n;j++) {
                if (land[i][j] == 0 && !history[i][j]) {
                    queue.add(landBfs(land,history,i,j));
                }
            }
        }
        int[] rs = new int[queue.size()];
        int i = 0;
        while (!queue.isEmpty()) {
            rs[i++] = queue.poll();
            //System.out.print("," + rs[i - 1]);
        }
        queue = null;
        return rs;
    }
    public static int landBfs(int[][] land,boolean[][] history,int i,int j) {
        Queue<Position> queue = new LinkedList<>();
        queue.add(new Position(i,j));
        int count = 0;
        while (!queue.isEmpty()) {
            Position p = queue.poll();
            if (p.x < 0 || p.y < 0 || p.x >= land.length || p.y >= land[0].length || history[p.x][p.y] || land[p.x][p.y] != 0) {
                continue;
            }
            count++;
            history[p.x][p.y] = true;
            queue.add(new Position(p.x - 1,p.y - 1));
            queue.add(new Position(p.x - 1,p.y + 1));
            queue.add(new Position(p.x + 1,p.y - 1));
            queue.add(new Position(p.x + 1,p.y + 1));
            queue.add(new Position(p.x,p.y - 1));
            queue.add(new Position(p.x,p.y + 1));
            queue.add(new Position(p.x + 1,p.y));
            queue.add(new Position(p.x - 1,p.y));
        }
        return count;
    }

    public static void solve(char[][] board) {
        if (board == null || board.length < 3 || board[0].length < 3) return;
        int m = board.length , n = board[0].length;
        UnionFind<PointHash> uf = new UnionFind<>();
        uf.put(null);
        for (int i = 0 ;i < m;i++) {
            for (int j = 0;j < n;j++) {
                if (board[i][j] == 'X') continue;
                uf.put(new PointHash(i,j));
                boolean onEdge = i == 0 || j == 0 || i == m - 1 || j == n - 1;
                if (onEdge) {
                    uf.union(new PointHash(i,j),null);
                }
            }
        }
        for (int i = 0;i < m;i++) {
            for (int j = 0;j < n;j++) {
                if (board[i][j] == 'X') continue;
                //上下左右
                PointHash curPoint = new PointHash(i,j);
                if (i > 0 && board[i - 1][j] == 'O')
                    uf.union(curPoint,new PointHash(i - 1,j));
                if (j > 0 && board[i][j - 1] == 'O')
                    uf.union(curPoint,new PointHash(i,j - 1));
                if (i < m - 1 && board[i + 1][j] == 'O')
                    uf.union(curPoint,new PointHash(i + 1,j));
                if (j < n - 1 && board[i][j + 1] == 'O')
                    uf.union(curPoint,new PointHash(i,j + 1));
            }
        }
        for (int i = 0;i < m;i++) {
            for (int j = 0;j < n;j++) {
                if (board[i][j] == 'X') continue;
                if (!uf.isSameSet(null,new PointHash(i,j))) {
                    board[i][j] = 'X';
                }
            }
        }
        return;
    }

    public static int numEnclaves(int[][] A) {
        int count = 0;
        UnionFind<PointHash> uf = new UnionFind<>();
        uf.put(null);
        Set<PointHash> set = new HashSet<>();
        int m = A.length , n = A[0].length;
        for (int i = 0;i < m;i++) {
            for (int j = 0;j < n;j++) {
                if (A[i][j] == 0) continue;
                uf.put(new PointHash(i,j));
                boolean onEdge = i == 0 || j == 0 || i == m - 1 || j == n - 1;
                if (onEdge) {
                    uf.union(new PointHash(i,j),null);
                }
            }
        }
        int[][] tensor = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int i = 0;i < m;i++) {
            for (int j = 0;j < n;j++) {
                if (A[i][j] == 0) continue;
                for (int k = 0;k < tensor.length;k++) {
                    if (i + tensor[k][0] < 0 || i + tensor[k][0] >= m || j + tensor[k][1] < 0 || j + tensor[k][1] >= n) continue;
                    if (A[i + tensor[k][0]][j + tensor[k][1]] == '1') {
                        uf.union(new PointHash(i,j),new PointHash(i + tensor[k][0],j + tensor[k][1]));
                    }
                }
            }
        }
        for (int i = 0;i < m;i++) {
            for (int j = 0;j < n;j++) {
                if (A[i][j] == 0) continue;
                if (!uf.isSameSet(null,new PointHash(i,j)) && !set.contains(uf.findFather(new PointHash(i,j)))) {
                    count++;
                    set.add(uf.findFather(new PointHash(i,j)));
                }
            }
        }
        return count;
    }

    static class UFInt {
        //记录father
        int[] nums;
        //记录该节点 rank
        int[] ranks;
        int size;
        public UFInt(int size) {
            this.size = size;
            nums = new int[size];
            ranks = new int[size];
            for (int i = 0;i < nums.length;i++) {
                nums[i] = i;
                ranks[i] = 1;
            }
        }
        public boolean isSameSet(int i,int j) {
            return findFather(i) == findFather(j);
        }
        public int findFather(int i ) {
            Stack<Integer> stack = new Stack<>();
            while (nums[i] != i) {
                stack.push(i);
                i = nums[i];
            }
            while (!stack.isEmpty()) {
                nums[stack.pop()] = i;
            }
            return i;
        }
        public void union(int i,int j) {
            if (i < size && j < size) {
                int iF = findFather(i);
                int jF = findFather(j);
                if (jF != iF) {
                    int iFRank = ranks[iF];
                    int jFRank = ranks[jF];

                    int big = iFRank > jFRank ? iF : jF;
                    int small = iFRank <= jFRank ? iF : jF;

                    nums[small] = big;

                    ranks[big] = ranks[small] + ranks[big];
                    ranks[small] = -1;
                }
            }
        }

    }
    public static int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1 ) return -1;
        int opNums = 0 , remaining = 0;
        UFInt uf = new UFInt(n);
        for (int i = 0;i < connections.length;i++) {
            int x = connections[i][0],y = connections[i][1];
            if (uf.isSameSet(x,y)) {
                remaining++;
            }
            else {
                uf.union(x,y);
            }
        }
        int i = 1;
        while (i < n) {
            if (!uf.isSameSet(i,i-1) && remaining > 0) {
                opNums++;
                remaining--;
                uf.union(i,i - 1);
            }
            else if (!uf.isSameSet(i,i - 1)){
                return -1;
            }
            i++;
        }
        return opNums;
    }

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

    public static int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        if (arr1 == null || arr1.length == 0) return 0;
        if (arr2 == null || arr2.length == 0) return arr1.length;
        int count = 0 , left = 0 , right = arr2.length - 1;
        Arrays.sort(arr2);
        for (int i = 0;i < arr1.length;i++) {
            left = 0 ;
            right = arr2.length - 1;
            while (left <= right) {
                int mid = (left + right) / 2;
                if (arr2[mid] < arr1[i]) {
                    left = mid + 1;
                }
                else if (arr2[mid] > arr1[i]) {
                    right = mid - 1;
                }
                else {
                    right = mid;
                    break;
                }
            }
            if (right > -1 && right < arr2.length - 1) {
                if (Math.abs(arr2[right] - arr1[i]) > d && Math.abs(arr2[right + 1] - arr1[i]) > d) count++;
            }
            else if (right == -1) {
                if (Math.abs(arr2[right + 1] - arr1[i]) > d) count++;
            }
            else {
                if (Math.abs(arr2[right] - arr1[i]) > d) count++;
            }
        }
        return count;
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

    public static boolean juMap(HashMap<Character,Integer> map1,HashMap<Character,Integer> map2) {
        if (map1.size() < map2.size()) return false;
        for(Character c : map1.keySet()) {
            if (map1.getOrDefault(c,0) < map2.getOrDefault(c,0)) {
                return false;
            }
        }
        return true;
    }
    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() < t.length()) return "";
        int startId = -1,minLen = Integer.MAX_VALUE;
        HashMap<Character,Integer> needChrMap = new HashMap<>();
        for (int i = 0;i < t.length();i++) needChrMap.put(t.charAt(i),needChrMap.getOrDefault(t.charAt(i),0) + 1);
        String newS = "";
        List<Integer> indexMap = new ArrayList<>();
        for (int i = 0;i < s.length();i++) {
            if (needChrMap.containsKey(s.charAt(i))) {
                newS += s.charAt(i);
                indexMap.add(i);
            }
        }
        if (indexMap.size() == 0) return "";
        HashMap<Character,Integer> available = new HashMap<>();
        int left = 0,right = -1;
        String rs = "";
        while ((right == -1 || left <= right) && right < newS.length()) {
            if (right < newS.length() - 1 && !juMap(available,needChrMap)) {
                right++;
                char c = newS.charAt(right);
                if (needChrMap.containsKey(c)) {
                    available.put(c,available.getOrDefault(c,0) + 1);
                }
            }
            else {
                if (juMap(available,needChrMap) && indexMap.get(right) - indexMap.get(left) + 1 < minLen) {
                    minLen = indexMap.get(right) - indexMap.get(left) + 1;
                    startId = indexMap.get(left);
                }
                char c = newS.charAt(left);
                if (needChrMap.containsKey(c)) {
                    available.put(c,available.getOrDefault(c,0) - 1);
                    if (available.get(c) <= 0) {
                        available.remove(c);
                    }
                }
                left++;
            }
        }
        //下标 翻转
        if (startId == -1) return rs;
        int i = 0;
        while (i < minLen) {
            rs += "" + s.charAt(i + startId);
            i++;
        }
        return rs;
    }
    public static boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        if (n == 0) return true;

        boolean[] history = new boolean[n];
        //根节点入度为0
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0;i < n;i++) {
            map.put(leftChild[i],map.getOrDefault(leftChild[i],0) + 1);
            map.put(rightChild[i],map.getOrDefault(rightChild[i],0) + 1);
        }
        for (int i = 0;i < n;i++) {
            //尝试每个节点作为根节点进行搜索
            if (!map.containsKey(i) && search(i,leftChild,rightChild,history)) {
                System.out.println(i);
                return true;
            }
        }
        return false;
    }
    public static boolean search(int i, int[] leftChild, int[] rightChild,boolean[] history) {
        boolean canSuccessful = true;
        //非递归BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.add(i);
        while (!queue.isEmpty()) {
            int x = queue.poll();
            if (x == -1) continue;
            //发现回边
            if (history[x]) {
                canSuccessful = false;
                break;
            }
            history[x] = true;
            queue.add(leftChild[x]);
            queue.add(rightChild[x]);
        }
        //查看是否搜索的节点都被访问过
        for (int idx = 0;idx < history.length;idx++) {
            canSuccessful &= history[idx];
            history[idx] = false;
        }
        return canSuccessful;
    }

    //颜色分类
    public void sortColors(int[] nums) {
        int red = 0,white = 0,bule = 0;
        for (int i = 0;i < nums.length;i++) {
            if (nums[i] == 0) {
                red++;
            }
            else if (nums[i] == 1) {
                white++;
            }
            else {
                bule++;
            }
        }
        int idx = 0;
        while (red > 0) {
            nums[idx++] = 0;
        }
        while (white > 0) {
            nums[idx++] = 1;
        }
        while (bule > 0) {
            nums[idx++] = 2;
        }
    }

    public int maxEvents(int[][] events) {
        int meetings = 0;
        //开始时间升序重排
        Arrays.sort(events,(o1, o2) -> o1[0] - o2[0]);
        //优先级队列 用来维护 结束时间最小的会议
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        //id 用来表征 目前走到 第个会议 ,day用来表征当前日期，日期可以更快增长;
        int day = 0 , id = 0,n = events.length;
        while (id < n || !priorityQueue.isEmpty()) {
            if (priorityQueue.isEmpty()) {
                priorityQueue.add(events[id][1]);
                day = events[id++][0];
            }
            //新加入会议
            while (id < n && events[id][0] <= day) {
                priorityQueue.add(events[id++][1]);
            }
            //加入的会议一定会有大于当前日期的
            if (!priorityQueue.isEmpty() && priorityQueue.peek() >= day) {
                //取出最小的一天
                meetings++;
                //一天一天走,更多选择
                day++;
            }
            //被选择也行，过期也行。
            priorityQueue.poll();
        }
        return meetings;
    }
    //贪心算法！！！

    //不同种类二叉树
    public static int numTrees(int n) {
        if (n < 2) return 1;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2;i <= n;i++) {
            for (int j = 0;j < i;j++) {
                dp[i] += dp[j] * dp[i - j - 1];
            }
        }
        return dp[n];
    }

    //给定整数n,生成不同种类的二叉搜索树
    public static List<TreeNode> generateTrees(int n) {
        return generateTreeReInc(1,n);
    }
    public static List<TreeNode> generateTreeReInc(int left,int right) {
        List<TreeNode> rs = new ArrayList<>();
        if (left > right) {
            rs.add(null);
            return rs;
        }
        if (left == right) {
            rs.add(new TreeNode(left));
            return rs;
        }
        // left =< i <= right
        for (int i = left;i <= right;i++) {
            //使用left - > i - 1 构建 左子树 x种
            //使用i + 1 -> right 构建 右子树 y种
            List<TreeNode> lefts = generateTreeReInc(left,i - 1);
            List<TreeNode> rights = generateTreeReInc(i + 1,right);
            //对于每一种 左子树 和 每一种右子树,构建 根节点，并且连入
            for (int l = 0;i < lefts.size();i++) {
                for (int r = 0;r < rights.size();r++) {
                    TreeNode root = new TreeNode(i);
                    root.left = lefts.get(l);
                    root.right = rights.get(r);
                    rs.add(root);
                }
            }
        }
        return rs;
    }
    public static int leastInterval(char[] tasks, int n) {
        int today = 0;
        HashMap<Character,Integer> taskExecTime = new HashMap<>();
        HashMap<Character,Integer> taskRemaining = new HashMap<>();
        for (int i = 0;i < tasks.length;i++) {
            taskExecTime.put(tasks[i],- n - 1);
            taskRemaining.put(tasks[i],taskRemaining.getOrDefault(tasks[i],0) + 1);
        }
        char task = tasks[0];
        int minTime = Integer.MAX_VALUE;
        while (taskExecTime.size() > 0) {
            minTime = Integer.MAX_VALUE;
            //找到一个时间最小的
            for (char c : taskExecTime.keySet()) {
                if (taskExecTime.get(c) < minTime) {
                    minTime = taskExecTime.get(c);
                    task = c;
                }
            }
            //找到一个当前可执行的且 剩余数 最多的
            for (char c : taskRemaining.keySet()) {
                if (taskRemaining.get(c) > taskRemaining.get(task) && today - taskExecTime.get(c) >= n) {
                    task = c;
                }
            }
            while (today - taskExecTime.get(task) < n) {
                today++;
            }
            //执行task
            taskRemaining.put(task,taskRemaining.get(task) - 1);
            today++;
            if (taskRemaining.get(task) == 0) {
                taskRemaining.remove(task);
                taskExecTime.remove(task);
                continue;
            }
            taskExecTime.put(task,today);
        }
        return today;
    }
    public static int marCal(int[][] nums,int i,boolean row) {
        int count = 0;
        if (row) {
            //按行计数 (1的个数)
            for (int j = 0;j < nums[0].length;j++) count += nums[i][j] == 0 ? 0 : 1;
        }
        else {
            //按列计数
            for (int j = 0;j < nums.length;j++) count+= nums[j][i] == 0 ? 0 : 1;
        }
        return count;
    }
    public static void reverserMar(int[][] nums,int i,boolean row) {
        if (row) {
            //按行计数 (1的个数)
            for (int j = 0;j < nums[0].length;j++) {
                nums[i][j] = nums[i][j] == 1 ? 0 : 1;
            }
        }
        else {
            //按列计数
            for (int j = 0;j < nums.length;j++) {
                nums[j][i] = nums[j][i] == 1 ? 0 : 1;
            }
        }
    }
    public static int matrixScore(int[][] A) {
        int m = A.length,n = A[0].length;
        int sum = 0;
        //第一步处理高位，行 、 列操作 一起用
        while (marCal(A,0,false) < m) {

                //这时候整列 变换收益就比较低,选择进行行变换
                for (int i = 0;i < m;i++) {
                    if (A[i][0] == 0) {
                        //翻转这一行
                        reverserMar(A,i,true);
                    }
                }
        }
        //第二步 处理低位，只能用列操作
        for (int i = 1;i < n;i++) {
            if (marCal(A,i,false) <= m / 2) {
                reverserMar(A,i,false);
            }
        }
        //计数
        for (int i = 0;i < m;i++) {
            int base = 1;
            for (int j = n - 1;j > -1;j--) {
                if (A[i][j] == 1) {
                    sum += base;
                }
                base = base * 2;
            }
        }
        return sum;
    }

    class Pair{
        int x;
        int y;
        public Pair(int x,int y) {
            this.x = x;
            this.y = y;
        }
    }
    public int[][] reconstructQueue(int[][] people) {
        if (people == null || people.length <= 1) return people;
        int m = people.length , n = people[0].length;
//        int[][] temps = new int[m][n];
        Pair[] temp = new Pair[m];
        int idx = 0 , tahn = 0;
        PriorityQueue<Pair> queue = new PriorityQueue<>((o1, o2) -> o1.y == o2.y ? o1.x - o2.x : o1.y - o2.y);
        for (int i = 0;i < people.length;i++) {
            queue.add(new Pair(people[i][0],people[i][1]));
        }
        while (!queue.isEmpty()) {
            Pair p = queue.poll();
            tahn = 0;
            for (int i = 0;i < idx;i++) {
                if (temp[i].x >= p.x) tahn++;
            }
            //向前移动 p.y - tahn个
            int step = idx;
            while (tahn > p.y) {
                temp[step] = temp[step - 1];
                if (temp[step].x >= p.x) tahn--;
                step--;
            }
            temp[step] = p;
            idx++;
        }
        int[][] temps = new int[m][n];
        for (int i = 0;i < temp.length;i++) {
            temps[i][0] = temp[i].x;
            temps[i][1] = temp[i].y;
        }
        return temps;
    }

    public static List<Integer> partitionLabels(String S) {

        List<Integer> res = new ArrayList<>();
        if (S == null || S.length() == 0) return res;
        int left = 0 , right = 0;
        int[] posRecord = new int[26];
        for (int i = 0;i < S.length();i++) {
            posRecord[S.charAt(i) - 'a'] = i;
        }
        while (left < S.length()) {
            right = left;
            //初始时
            int limit = posRecord[S.charAt(left) - 'a'];
            while (right < limit) {
                limit = Math.max(posRecord[S.charAt(right) - 'a'],limit);
                right++;
            }
            res.add(right - left + 1);
            left = right + 1;
        }
        return res;
    }
    public static boolean isSubsequence(String s, String t) {
        //判断 s 是 t 的子序列
        if (s == null || t == null || t.length() < s.length()) return false;
        int i = 0,j = 0;
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == s.length();
    }
    public static int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0) return 0;
        int shootTime = 0;
        Arrays.sort(points,(o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]);
        int idx = 0;
        while (idx < points.length) {
            int start = points[idx][0];
            int end = points[idx][1];

            while (idx < points.length - 1 && points[idx + 1][0] <= end) {
                idx++;
                start = Math.max(points[idx][0],start);
                //end保持不变
                end = Math.min(points[idx][1],end);
            }
            shootTime++;
            idx++;
        }
        return shootTime;
    }
    public static int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length < 2) return 0;
        int erase = 0 , idx = 0;
        Arrays.sort(intervals,(o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]);
        while (idx < intervals.length) {
            int start = intervals[idx][0];
            int end = intervals[idx][1];
            //去除相同起点，并且跨度更长的区间
//            while (idx < intervals.length - 1 && intervals[idx + 1][0] == start) {
//                idx++;
//                erase++;
//            }
            //后续存在 交叉 区间,选取一个 end 最小的
            while (idx < intervals.length - 1 && intervals[idx + 1][0] < end) {
                idx++;
                if (intervals[idx][1] < end) {
                    start = intervals[idx][0];
                    end = intervals[idx][1];
                }
                erase++;
            }
            idx++;
        }
        return erase;
    }



    //切分回文串
    public static List<List<String>> partition(String s) {
        List<List<String>> rex = new ArrayList<>();
        if (s == null || s.length() == 0) return rex;
        int n = s.length();
        boolean[] splitVectors = new boolean[s.length()];
        boolean[][] dp = new boolean[n][n];
        for (int i = 0;i < n;i++) {
            int x = 0, y = i;
            while (x < n && y < n) {
                if (x == y) {
                    dp[x][y] = true;
                }
                else if (x + 1 == y) {
                    dp[x][y] = s.charAt(x) == s.charAt(y);
                }
                else {
                    dp[x][y] = s.charAt(x) == s.charAt(y) & dp[x + 1][y - 1];
                }
                x++;
                y++;
            }
        }
        splitVectors[0] = true;
        generateStrs(s.toCharArray(),1,splitVectors,rex,dp);
        return rex;
    }
    public static void generateStrs(char[] chrs,int splitPox,boolean[] splitVectors,List<List<String>> rex,boolean[][] dp) {

        //上一个切割地方
        int lastSplitPox = Math.min(splitPox,splitVectors.length - 1);
        while (!splitVectors[lastSplitPox]) {
            lastSplitPox--;
        }
        //对于一个长度为 n 的字符串 ，切割位置总共有 n - 1个
        if (splitPox == chrs.length) {
            if (dp[lastSplitPox][splitVectors.length - 1]) {
                List<String> chrList = new ArrayList<>();
                String temp = "" + chrs[0];
                for (int i = 1;i < splitVectors.length;i++) {
                    if (splitVectors[i]) {
                        chrList.add(temp);
                        temp = "" + chrs[i];
                    }
                    else {
                        temp += chrs[i];
                    }
                }
                chrList.add(temp);
                System.out.println(chrList);
                rex.add(chrList);
            }
            return;
        }
        //如果前面一部分不是回文串则不能切分
        if (!dp[lastSplitPox][splitPox - 1]) {
            splitVectors[splitPox] = false;
            generateStrs(chrs,splitPox + 1,splitVectors,rex,dp);
        }
        else {
            //如果当前点距离上个点是回文串,当前点可以进行切分
            splitVectors[splitPox] = true;
            generateStrs(chrs,splitPox + 1,splitVectors,rex,dp);
            splitVectors[splitPox] = false;
            generateStrs(chrs,splitPox + 1,splitVectors,rex,dp);
        }
    }
    //判断回文串
    public static boolean isSameStr(char[] chrs,int left,int right) {
        while (left < right) {
            if (chrs[left] != chrs[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    //找出字符串 s 中最长的回文串
    public static String longestPalindrome(String s) {
        if (s == null || s.length() == 0) return "";
        int n = s.length();
        //改写，使其 O(1) 空间复杂度
        //boolean[][] dp = new boolean[s.length()][s.length()];

        int start =   0 ,end = 0,maxLen = Integer.MIN_VALUE;
        boolean dp = false;
        int[][] moveTensor = {{0,1},{1,0}};
        int xStart = 0,yStart = 0,idx = 0;
        while (xStart < n && yStart < n) {
            int x = xStart,y = yStart;
            dp = false;
            while (x > -1 && y < n) {
                if (x == y) {
                    dp = true;
                }
                else if (x + 1 == y) {
                    dp = s.charAt(x) == s.charAt(y);
                }
                else {
                    dp = s.charAt(x) == s.charAt(y) & dp;
                }
                if (dp && y - x + 1 > maxLen) {
                    start = x;
                    end = y;
                    maxLen = y - x + 1;
                }
                x--;
                y++;
            }
            xStart += moveTensor[idx][0];
            yStart += moveTensor[idx][1];
            idx = (idx + 1) % moveTensor.length;
        }

        return s.substring(start,end + 1);
    }

    public static void main(String[] args) {
        String s = "aacaadbdbbafcbbd";
        System.out.println(longestPalindrome(s));
    }

}

import java.util.Arrays;
import java.util.Scanner;

class Solution {
    static int[] fa;
    static int[] deep;
    public static int minSwapsCouples(int[] row) {
        fa = new int[row.length / 2];
        for(int i = 0; i < row.length / 2; i++) {
            fa[i] = i;
        }
        deep = new int[row.length / 2];
        Arrays.fill(deep, 1);
        int count = 0;
        for(int i = 0; i < row.length - 1; i = i + 2) {
            int node1 = row[i] / 2, node2 = row[i + 1] / 2;
            if (node1 != node2) {
                if(find(node1) == find(node2)) {
                    count++;
                } else {
                    merge(node1, node2);
                }
            } else {
                count++;
            }
        }
        return row.length / 2 - count;

    }

    static int find(int x) {
        return fa[x] == x ? x : (fa[x] = find(fa[x]));
    }

    static void merge(int a, int b) {
        int A = find(a), B = find(b);
        if(deep[A] > deep[B]) {
            fa[B] = A;
        } else if(deep[A] == deep[B]) {
            fa[B] = A;
            deep[A] += 1;
        } else {
            fa[A] = B;
        }
    }
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int npair = scn.nextInt();
        int[] nums = new int[npair * 2];
        for (int i = 0;i < nums.length;i++) {
            nums[i] = scn.nextInt();
        }
        System.out.println(minSwapsCouples(nums));
    }
}

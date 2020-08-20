package com.basicalgos;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class SceneDesign {

    /**
     * 基本运算器 "+ - / * _"
     * 仅仅包含+ - * / 以及 空格 五种情况 ， 且都是非负正数
     * 使用双端队列实现，因为当存在 * / 高优先级的运算符时应当优先运算
     *
     * 改进办法，直接对于每个数记录正负值，直接记录到栈中，若一个前一个运算符时 + ，则压入 +num,否则-num
     * 当前一个数为 * / 时。从栈中弹出一个数 * num ,再次压入.
     * 最后累计栈中数字的和
     * */
    public static int calculate(String s) {
        if (s == null || s.length() == 0) return Integer.MAX_VALUE;
        String target = "";
        for (char c : s.toCharArray()) {
            if (c != ' ') {
                target += c;
            }
        }
        s = target;
        if (s.length() == 0) return 0;
        int n = s.length() , idx = 0;
        Deque<Integer> numsStack = new LinkedList<>();
        Deque<Character> opsStack = new LinkedList<>();
        while (idx < n) {
            char c = s.charAt(idx);
            if (c == '+' || c == '-') {
                opsStack.addLast(c);
            }
            else if (c == '*' || c == '/') {
                //直接计算两个操作数
                int frist = numsStack.pollLast();
                opsStack.addLast(c);
                int second = s.charAt(++idx) - '0';
                while (idx < n - 1 && s.charAt(idx + 1) <= '9' && s.charAt(idx + 1) >= '0') {
                    second = second * 10 + (s.charAt(++idx) - '0');
                }
                char op = opsStack.pollLast();
                if (op == '*') {
                    numsStack.addLast(frist * second);
                }
                else {
                    numsStack.addLast(frist / second);
                }
            }
            else {
                //c 是个数字字符
                int base = c - '0';
                while (idx < n - 1 && s.charAt(idx + 1) <= '9' && s.charAt(idx + 1) >= '0') {
                    base = base * 10 + (s.charAt(++idx) - '0');
                }
                numsStack.addLast(base);
            }
            idx++;
        }
        while (numsStack.size() > 1) {
            int frist = numsStack.pollFirst();
            int second = numsStack.pollFirst();
            char op = opsStack.pollFirst();
            if (op == '+') {
                numsStack.addFirst(frist + second);
            }
            else {
                numsStack.addFirst(frist - second);
            }
        }
        return numsStack.pollFirst();
    }
}

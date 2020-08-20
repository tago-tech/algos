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


    /**
     * 四则运算 + - * / ( ) _
     * 将 中序表达式 转换成 逆波兰表达式
     * rploish字符串存放逆波兰结果,操作数栈opsStack
     *
     * tips:
     *  1.逆波兰表达式中数字之间要加入 空格 ，便于区分不同的数字划分;
     *  2.
     * */
    public static int calculator (String s) {
        //异常检测
        if (s == null || s.length() == 0) {
            return Integer.MIN_VALUE;
        }
        //初始化变量\数据清洗
        int n = s.length();
        //初始化上下文,栈空间
        StringBuilder rploish = new StringBuilder("");

        Stack<Character> opsStack = new Stack<>();

        Stack<Integer> numsStack = new Stack<>();

        for (int idx = 0;idx < n;idx++) {
            char c = s.charAt(idx);
            if (c == ' ') continue;
            if (c <= '9' && c >= '0') {
                //取出连续的数
                int base = c - '0';
                while (idx < n - 1 && s.charAt(idx + 1) >= '0' && s.charAt(idx + 1) <= '9') {
                    base = base * 10 + (s.charAt(++idx) - '0');
                }
                rploish.append(base + " ");
            }
            else if (c == '(') {
                opsStack.push(c);
            }
            else if (c == ')') {
                //找到一段括弧,这段括弧里的操作符号放到 逆波兰式中
                while (opsStack.peek() != '(') {
                    rploish.append(opsStack.pop() + " ");
                }
                opsStack.pop();
            }
            //以下阶段主要是将 优先级 相等或者比自己更高优先级
            else if (c == '+') {
                while (!opsStack.isEmpty() && opsStack.peek() != '(') {
                    rploish.append(opsStack.pop() + " ");
                }
                opsStack.push(c);
            }
            else if (c == '-') {
                while (!opsStack.isEmpty() && opsStack.peek() != '(') {
                    rploish.append(opsStack.pop() + " ");
                }
                opsStack.push(c);
            }
            else {
                while (!opsStack.isEmpty() && (opsStack.peek() == '/' || opsStack.peek() == '*')  ) {
                    rploish.append(opsStack.pop() + " ");
                }
                opsStack.push(c);
            }
        }

        while (!opsStack.isEmpty()) {
            rploish.append(opsStack.pop());
        }
        //对逆波兰表达式的计算
        int m = rploish.length();
        for (int i = 0;i < m;i++) {
            char c = rploish.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (c >= '0' && c <= '9') {
                int base = c - '0';
                while (i < m - 1 && rploish.charAt(i + 1) >= '0' && rploish.charAt(i + 1) <= '9') {
                    base = base * 10 + (rploish.charAt(++i) - '0');
                }
                numsStack.push(base);
            }
            else {
                //弹出两个操作数
                int second = numsStack.pop();
                int frist = numsStack.pop();
                if (c == '+') {
                    numsStack.push(frist + second);

                }
                else if (c == '-') {
                    numsStack.push(frist - second);
                }
                else if (c == '/') {
                    numsStack.push(frist / second);
                }
                else {
                    numsStack.push(frist * second);
                }
            }
        }
        return numsStack.pop();
    }
}

package collections;

import java.util.*;
import java.lang.Integer;
import java.lang.Character;
/**
 * @author    zhangqizky
 * @apiNote   堆栈的使用：
 *            堆栈只有入栈和出栈两种操作：
 *            push(E)
 *            pop(E)
 *            peek(E)取栈顶元素但不弹出
 */

public class MyStack
{
    public static void main(String[] args)
    {
        String hex = toHex(12500);
        if (hex.equalsIgnoreCase("30D4")) {
            System.out.println("测试通过");
        } else {
            System.out.println("测试失败");
            System.out.println(hex);
        }
        String exp = "1 + 2 * (9 - 5)";
        SuffixExpression se = compile(exp);
        int result = se.execute();
        System.out.println(exp + " = " + result + " " + (result == 1 + 2 * (9 - 5) ? "✓" : "✗"));
    }
    static int increase(int x)
    {
        return increase(x) +1;
    }
    /**
     * 将整数转为十六进制
     */
    static String toHex(int n)
    {
        String res = "";
        Deque<String>hex = new LinkedList<>();
        int number = n;
        while(number > 0)
        {
            Integer t = Integer.valueOf(number%16);
            System.out.println(t.intValue());
            if(t.intValue()<10)
            {

                String tmp = Integer.toString(t);
                hex.push(tmp);
                System.out.print(tmp);
            }
            else
            {
                Integer te = Integer.valueOf(t.intValue()-10);
                char c = (char)(te.intValue() + 'A');
                Character C = Character.valueOf(c);
                hex.push(Character.toString(C));
                System.out.print(Character.toString(C));
            }
            number = number / 16;
        }
        while(hex.size()!=0)
        {
            String tmp = hex.pop();
            res = res + tmp;
        }
        return res;
    }
    static SuffixExpression compile(String exp) {
        // TODO:
        String res = "";
        Deque<Character> stack = new LinkedList<>();
        int index = 0;
        while(index < exp.length())
        {
            char c = exp.charAt(index);
            switch(c)
            {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    res+=c;
                    break;
                case '*':
                case '/':
                    stack.push(c);
                    break;
                //左括号始终push
                case '(':
                    stack.push(c);
                    break;
                //右括号，就要把和左括号之间的运算符号先都加到后缀表达式上，然后再把左括号本身弹出来
                case ')':
                    while(stack.size()!=0 && stack.peek()!='(')
                    {
                        res+=stack.pop();
                    }
                    stack.pop();
                    break;
                //运算级比较低的运算符号，要放在后面，所以先把之前的运算符号先加到后缀表达式上，左括号不动。再把当前的运算符push到队列中
                case '+':
                case '-':
                    while(stack.size()!=0)
                    {
                        char temp = stack.pop();
                        if(temp=='(')
                        {
                            stack.push('(');
                            break;
                        }
                        res+=temp;
                    }
                    stack.push(c);
                    break;
                case ' ':
                    break;
                default:
                    break;
            }
            index++;
        }
        while(!stack.isEmpty())
        {
            res+=stack.pop();
        }
        return new SuffixExpression(res);
    }
}

class SuffixExpression {
    public String s;
    public SuffixExpression(String str)
    {
        this.s = str;
    }
    int execute() {
        // TODO:
        Deque<Integer>stack = new LinkedList<>();
        int length = s.length();
        int index = 0;
        while(index<length)
        {
            switch(s.charAt(index))
            {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    stack.push((int)s.charAt(index) - 48);
                    break;
                case '+':
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(a+b);
                    break;
                case '-':
                    int a1 = stack.pop();
                    int b1 = stack.pop();
                    stack.push(a1>b1?a1-b1:b1-a1);
                    break;
                case '*':
                    int a2 = stack.pop();
                    int b2 = stack.pop();
                    stack.push(a2*b2);
                    break;
                case '/':
                    int a3 = stack.pop();
                    int b3= stack.pop();
                    stack.push(a3 / b3);
                    break;
                default:
                    System.err.println("non valid value!!!");
                    break;
            }
            index ++;
        }
        return stack.pop();
    }
}

import java.util.List;
import java.util.ArrayList;

/**
* @authur zhangqi
@ apiNote List的使用
*/

public class MyList
{
public static void main(String[]args)
{
List<String> list = new ArrayList<>();
// 构造从start到end的序列：
final int start = 10;
final int end = 20;
List<Integer> list = new ArrayList<>();
for (int i = start; i <= end; i++) {
list.add(i);
}
// 随机删除List中的一个元素:
int removed = list.remove((int) (Math.random() * list.size()));
int found = findMissingNumber1(start, end, list);
System.out.println(list.toString());
System.out.println("missing number: " + found);
System.out.println(removed == found ? "测试成功" : "测试失败");
}
/**
* 当list有序的时候，找出缺失的值
*/
static int findMissingNumber1(int start,int end,List<Integer>list)
{
int index = start;
for(Integer integer:list)
{
if(list.get(i).intValue()!=index)
{
return index;
}
index ++;
if (index>end)
{
break;
}
}
return 0;
}
/**
* 当list无序的时候，找出缺失的值
*/
static int findMissingNumber2(int start,int end,List<Integer>list)
{
int sum1 = 0;
for(int i =start;i<=end;i++)
{
sum1+=i;
}
int sum2 = 0;
for(Integer integer:list)
{
sum2+=integer.intValue();
}
return sum2-sum1;
}
}

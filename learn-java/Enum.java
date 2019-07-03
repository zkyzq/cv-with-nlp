/**
 * @authur: tangxi.zq
 * @note   :java枚举类
 */

import javax.management.RuntimeErrorException;

public class Enum
{
    public static void main(String[]args)
    {
        WeekDay week = WeekDay.SUN;
        switch(week)
        {
            case SUN:
            case SAT:
                System.out.println("Today is " + week + ". Work at home!");
                break;
            case MON:
            case TUE:
            case WED:
            case THU:
            case FRI:
                System.out.println("Today is " + week + ". Work at office!");
                break;
            default:
                throw new RuntimeException("cannot process " + week);
        }
    }
}

enum WeekDay
{
    SUN(0,"星期日"), MON(1,"星期一"), TUE(2,"星期二"), WED(3,"星期三"), THU(4,"星期四"), FRI(5,"星期五"), SAT(6,"星期六");
    public final int value;
    public final String chinese;

    private WeekDay(int value,String chinese)
    {
        this.value = value;
        this.chinese = chinese;
    }

}

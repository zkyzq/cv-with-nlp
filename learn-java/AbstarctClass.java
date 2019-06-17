/*
*****************************************************************************
* @authur      : tangxi.zq
* @time        : 2019-06-17
* @file         : 抽象类
* @Description : 如果父类方法无需实现功能，仅仅是为了定义签名，目的是让子类去覆写，那么可以把父类的方法声明为抽象方法。
                 含有抽象方法的类必须定义为抽象类，无法实例化。
                 抽象类本身被设计成只能继承，因此，抽象类可以强迫子类实现其定义的抽象方法，否则编译会报错，相当于定义了规范。

                 
                 
******************************************************************************
*/

class AbstarctClass
{
    public static void main(String[]args)
    {
        // TODO: 用抽象类给一个有工资收入和稿费收入的小伙伴算税:
        Income[] incomes = new Income[] {new SalaryIncome(7500), new RoyaltyIncome(12000) };
		double total = 0;
        // TODO:
        for (Income in:incomes)
        {
            total += in.getTax();
        }
		System.out.println(total);
    }
}
/**
 * 计税的抽象类
 */
abstract  class Income
{
    protected double income;

    public Income(double income)
    {
        this.income = income;
    }
    public abstract double getTax();
}
/**
 * 工资计税
 */
class SalaryIncome extends Income
{
    public SalaryIncome(double income)
    {
        super(income);
    }

    @Override
    public double getTax()
    {
        if (this.income <= 5000) {
            return 0;
        }
        return (this.income - 5000) * 0.2;
    }
}
/**
 * 稿费计税
 */
class RoyaltyIncome extends Income
{
    public RoyaltyIncome(double income)
    {
        super(income);
    }

    @Override
    public double getTax()
    {
        return this.income * 0.1;
    }
}
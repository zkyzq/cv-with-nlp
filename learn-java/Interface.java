/**
 * @author               :tangxi.zq
 * @Description          :用接口实现的不同收入计税,和抽象类不同。
 */

class Interface
{
    public static void main(String[]args)
    {
        // TODO: 用接口给一个有工资收入和稿费收入的小伙伴算税:
		Income[] incomes = new Income[] {new SalaryIncome(7500), new RoyaltyIncome(12000) };
		double total = 0;
        // TODO:
        for(Income in:incomes)
        {
            total += in.getTax();
        }
		System.out.println(total);
    }
}
/**
 * 计税接口
 */
interface Income
{
    public double getTax();
}

/**
 * 工资计税
 */
class SalaryIncome implements Income
{
    private double income;

    public SalaryIncome(double income)
    {
        this.income = income;
    }
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

class RoyaltyIncome implements Income
{
    private double income;

    public RoyaltyIncome(double income)
    {
        this.income = income;
    }
    public double getTax()
    {
        return this.income * 0.1;
    }
}
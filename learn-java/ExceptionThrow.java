/**
 * @author tangxi.zq
 * @apiNote 抛出异常
 */
public class ExceptionThrow
{
    public static void main(String[]args)
    {
        try{
            System.out.println(tax(2000, 0.1));
		    System.out.println(tax(-200, 0.1));
            System.out.println(tax(2000, -0.1));
            System.out.println(tax(20000,0.2));
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.out.println("End.");
        }
    }
    static double tax(int salary, double rate) 
    {
        if(salary < 0 || rate < 0)
        {
            throw new IllegalArgumentException();
        }
		return salary * rate;
	}
}

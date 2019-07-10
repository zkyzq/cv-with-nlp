/**
 * @authur  :tangxi.zq
 * @Note    :catch exception
 */

 public class CatchException
 {
     public static void main(String args[])
     {
        String a = "12";
		String b = "x9";
		// TODO: 捕获异常并处理
		try{
		int c = stringToInt(a);
		int d = stringToInt(b);
		
		System.out.println(c * d);
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			System.out.println(0);
		}
		finally
		{
			System.out.println("End.");
		}
	}

	static int stringToInt(String s) throws NumberFormatException
	{
		return Integer.parseInt(s);
	}
 }

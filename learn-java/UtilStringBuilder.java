public class UtilStringBuilder
{
    public static void main(String[]args)
    {
        //文中的练习

        // //String可以进行相加操作，但是因为string不可变，所以每次都会生成一个新的临时对象，导致效率不高，所以java提供了StringBuilder
        // StringBuilder sb = new StringBuilder(1024);
        // for (int i = 0; i < 1000; i++) {
        //     sb.append(',');
        //     sb.append(i);
        // }
        // String s = sb.toString();
        // System.out.println(s);

        // //链式操作，因为stringbuilder的append方法会返回this对象，所以可以链式操作
        // var sb1 = new StringBuilder(1024);
        // sb1.append("Mr ")
        //   .append("Bob")
        //   .append("!")
        //   .insert(0, "Hello, ");
        // System.out.println(sb1.toString());

        //文末的练习
        String[] fields = { "name", "position", "salary" };
        String table = "employee";
        String insert = buildInsertSql(table, fields);
        System.out.println(insert);
        System.out.println(
                "INSERT INTO employee (name, position, salary) VALUES (?, ?, ?)".equals(insert) ? "测试成功" : "测试失败");
        
    }
    static String buildInsertSql(String table, String[] fields) {
        // TODO:
        StringBuilder res =new StringBuilder();
        // res = res.append("("); 
        for(int i = 0; i< fields.length;i++)
        {
            res = res.append(fields[i]);
            if(i!= fields.length-1)
            {
                res.append(", ");
            }
        }
        // res = res.append(")");
        return res.toString();
    }
}
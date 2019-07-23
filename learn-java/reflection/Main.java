
/**
 * 
 * @author zhangqi
 */
public class Main {

	public static void main(String[] args) {
		String name = "Xiao Ming";
		int age = 20;
		Person p = new Person();
		// TODO: 利用反射给name和age字段赋值:
		Class c = p.getClass();
		Field f1 =c.getDeclaredField("name");
		f1.setAccessible(true);
		f1.set(p,name);
		Field f2 = c.getDeclaredField("age");
		f2.setAccessible(true);
		f2.set(p,age);
		System.out.println(p.getName()); // "Xiao Ming"
		System.out.println(p.getAge()); // 20
	}
}

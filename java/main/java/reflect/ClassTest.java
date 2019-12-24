package reflect;

public class ClassTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassTest t1 = new ClassTest();
		Class<?> c1 = t1.getClass();
		ClassTest t2 = new ClassTest();
		Class<? extends ClassTest> c2 = t2.getClass();
		System.out.println(c1 == c2);
	}

}

package vm;

public class RetrunTest {
	
	static int  i = 0;

	public static void main(String[] args) {
		System.out.println(test1());
		System.out.println(i);
		System.out.println(test2());
	}
	
	public static int test1() {
		return i++;
	}
	
	public static int test2() {
		return ++i;
	}
}

package classtest;

public class NotInitialization {

	public static void main(String[] args) {
//		System.out.println(B.value);
		B[] array = new B[10];
		Class<?> clsClass = array.getClass();
		System.out.println(clsClass.getName());
	}
}

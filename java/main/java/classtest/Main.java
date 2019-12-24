package classtest;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		A a = new B();
		Class<? extends A> cls = a.getClass();
		System.out.println(cls);
	}

}

package object;

public class point {
	protected int a;

	public static class A{
		public A(){
			this.a = "123";
		}
		
		public String a;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		A a = null;
		point(a);
		System.out.println(a.a);
	}

	public static void point(A a){
		a = new A();
	}
}

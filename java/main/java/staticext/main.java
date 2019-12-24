package staticext;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Father father = new Father();
		Son son = new Son();
		father.a = 1L;
		System.out.println(Father.a);
		System.out.println(Son.a);
	}

}

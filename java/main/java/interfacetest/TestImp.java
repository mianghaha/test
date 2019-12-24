package interfacetest;

public class TestImp implements TestInterface{

	public static void main(String[] args) {
		TestInterface t = new TestImp();
		t.test1(1);
		t.test2(2);
	}
}

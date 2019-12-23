package interfacetest;

public interface TestInterface {

	public default void test1(int i) {
		System.out.println("test1");
	}
	
	public default void test2(int i) {
		System.out.println("test2");
	}
}

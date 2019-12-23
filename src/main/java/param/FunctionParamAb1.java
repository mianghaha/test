package param;

public class FunctionParamAb1 extends FunctionParam{

	@Override
	public void test(Object... objects) {
		System.out.println(objects[0]);
		System.out.println(objects[1]);
	}
	
	public static void main(String[] args) {
		FunctionParamAb1 functionParamAb1 = new FunctionParamAb1();
		functionParamAb1.test(1, 2);
	}

}

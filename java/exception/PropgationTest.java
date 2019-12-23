package exception;

public class PropgationTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			func1();
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	public static void func1() throws Exception{
		func2();
	}

	public static void func2() throws Exception{
		func3();
	}
	
	public static void func3() throws Exception{
		throw new Exception("11111");
	}
}

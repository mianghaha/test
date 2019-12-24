package propagation;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test1(null, null);
	}
	
	public static void test1(Integer param1, Integer param2){
		Integer param3 = null;
		
		if(param1 == null){
			param1 = param3;
		}
	}

}

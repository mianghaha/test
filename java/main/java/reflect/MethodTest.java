package reflect;

import java.lang.reflect.Method;

import utils.JsonUtil;

public class MethodTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
//			Method method = MethodTest.class.getDeclaredMethod("test", int.class, int.class, String[].class);
//			System.out.println(method.getDeclaringClass().getName());
			
//			Method[] methods = MethodTest.class.getDeclaredMethods();
			Method[] methods = MethodTest.class.getMethods();
			for(Method method : methods) {
				Class<?>[] clsArray = method.getParameterTypes();
				String string = method.getName() + "---";
				for(Class<?> cls : clsArray) {
					string = string + "," + cls.getName();
				}
				System.out.println(string);
			}

			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int test(int i, int j, String...strings) {
		int k = i + j;
		return k;
	}

}

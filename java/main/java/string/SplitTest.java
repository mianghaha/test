package string;

public class SplitTest {

	public static void main(String[] args){
//		String a = "132";
//		String[] aStrings = a.split(",");
//		System.out.println(a);
//		
//		String userid = "123@456$zulong@zl";
//		String channel = "unknown";
//		int index = userid.lastIndexOf("$");
//		int index2 = userid.lastIndexOf("@");
//		if (index != -1){
//			if(index2 > index){
//				userid = userid.substring(0, index2);
//			}
//			channel = userid.substring(index + 1);
//		}
		
//		String a = "1.xml";
//		String[] arr = a.split("\\.");
		
		String a = "1.2.3.4.5";
		String[] arr = a.split("\\.");
		System.out.println(arr.length + "=====" + arr[0]);
	}
}

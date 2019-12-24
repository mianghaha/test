package string;

public class StringObject {
	
	public static void change(String s) {
		s = "aaa";
	}

	public static void main(String[] args) {
//		String s = "bbb";
		String s = new String("bbb");
		change(s);
		System.out.println(s);
	}
}

package logical;

import java.nio.channels.SelectionKey;

public class Main {

	public static void main(String[] args) {
		boolean a = true;
		boolean b = false;
		boolean c = true;
		boolean d = false;
		
		System.out.println(a && b || c);
		
//		System.out.println((a || b) && (c || d));
	}

}

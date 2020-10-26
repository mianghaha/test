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
		System.out.println(22 & -2147483648);
		System.out.println(0x80000000 & 0x7fffffff);
		System.out.println(22 & 0x80000000);
		System.out.println(-22 & 0x80000000);
		System.out.println(22 & 0x7fffffff);
		System.out.println(0x80000000);
		System.out.println(0x7fffffff);
		int count = 10;
		boolean is_request = (count & -2147483648) != 0;
		System.out.println(is_request);
		System.out.println(count | -2147483648);
		System.out.println(count & 2147483647);
		System.out.println(15 | -2147483648);
		System.out.println(0x101f);
	}

}

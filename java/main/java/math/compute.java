package math;

public class compute {

	public static void main(String[] args){
		int a = 1354951121;
		int b = ((Math.abs(a)) << 3) + 3;
		int c = (b - 3) >> 3;
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
	}
}

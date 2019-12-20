package multithread;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ThreadClass1 thread = new ThreadClass1();
		thread.start();
		System.out.println("main is over");
	}

}

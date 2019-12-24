package set;

import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentSkipListSetTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 ConcurrentSkipListSet<Long> set = new ConcurrentSkipListSet();
		 set.add(1L);
		 set.add(2L);
		 
		 Thread1 thread1 = new Thread1(1, set);
		 Thread1 thread2 = new Thread1(2, set);
		 Thread1 thread3 = new Thread1(3, set);
		 Thread1 thread4 = new Thread1(4, set);

		 thread1.start();
		 thread2.start();
		 thread3.start();
		 thread4.start();
	}

}

package lock;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ReentrantLock lock = new ReentrantLock();
//		lock.tryLock();
//		Condition c1 = lock.newCondition();
//		System.out.println("Before=" + new Date());
//		long nanos = TimeUnit.NANOSECONDS.convert(5, TimeUnit.SECONDS);
//		System.out.println("nanos=" + nanos);
//		long t1 = c1.awaitNanos(nanos);
//		System.out.println("t1=" + t1);
//		System.out.println("After=" + new Date());
//		lock.unlock();
		
		//锁未拿到
		Condition c = lock.newCondition();
//		lock.lock();
		c.await();
		System.out.println("1111");
	}

}

package cas;

public class UnSafeTest {
	public int i;
	public UnSafeTest test;

	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		Class<?> cls = UnSafeTest.class;
		
		UnSafeTest test1 = new UnSafeTest();
		test1.i = 1;
		
		UnSafeTest test2 = new UnSafeTest();
		test2.i = 2;
		
		UnSafeTest parent = new UnSafeTest();
		parent.i = 3;
		parent.test = test1;
		
		final sun.misc.Unsafe Unsafe = sun.misc.Unsafe.getUnsafe();
		long offset = Unsafe.objectFieldOffset(cls.getDeclaredField("test"));
		boolean isSwaped = Unsafe.compareAndSwapObject(parent, offset, test2, test1);
		System.out.println("isSwaped=" + isSwaped + ",parent=" + parent);
		
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UnSafeTest [i=");
		builder.append(i);
		builder.append(", test=");
		builder.append(test);
		builder.append("]");
		return builder.toString();
	}
}

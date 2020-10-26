package array;

import java.util.Arrays;

public class ObjArrayCopyTest {
	
	public static class TestObject{
		public TestObject(int i) {
			this.i = i;
		}
		int i;
	}

	public static void main(String[] args) {
		int count = 10000000;
		TestObject[] array = new TestObject[count];
		for(int i = 0;i < count; i++) {
			array[i] = new TestObject(i);
		}
		
		testArrays(array);
//		testSystem(array);
	}
	
	public static TestObject[] testArrays(TestObject[] array) {
		long startTime = System.currentTimeMillis();
		TestObject[] newArray = Arrays.copyOf(array, array.length + 10);
		long endTime = System.currentTimeMillis();
		System.out.print("time=" + (endTime - startTime));
		return newArray;
	}
	
	public static TestObject[] testSystem(TestObject[] array) {
		long startTime = System.currentTimeMillis();
		TestObject[] newArray = new TestObject[array.length + 10];
		System.arraycopy(array, 0, newArray, 0, array.length);
		long endTime = System.currentTimeMillis();
		System.out.print("time=" + (endTime - startTime));
		return newArray;
	}

}

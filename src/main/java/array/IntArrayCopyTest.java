package array;

import java.util.Arrays;

public class IntArrayCopyTest {

	public static void main(String[] args) {
		int count = 10000000;
		String[] array = new String[count];
		for(int i = 0;i < count; i++) {
			array[i] = "str" + i;
		}
		
		testArrays(array);
//		testSystem(array);
	}
	
	public static String[] testArrays(String[] array) {
		long startTime = System.currentTimeMillis();
		String[] newArray = Arrays.copyOf(array, array.length + 10);
		long endTime = System.currentTimeMillis();
		System.out.print("time=" + (endTime - startTime));
		return newArray;
	}
	
	public static String[] testSystem(String[] array) {
		long startTime = System.currentTimeMillis();
		String[] newArray = new String[array.length + 10];
		System.arraycopy(array, 0, newArray, 0, array.length);
		long endTime = System.currentTimeMillis();
		System.out.print("time=" + (endTime - startTime));
		return newArray;
	}

}

package array;

import java.util.Arrays;

public class StringArrayCopyTest {

	public static void main(String[] args) {
		int count = 100000000;
		int[] array = new int[count];
		for(int i = 0;i < count; i++) {
			array[i] = i;
		}
		
//		testArrays(array);
		testSystem(array);
	}
	
	public static int[] testArrays(int[] array) {
		long startTime = System.currentTimeMillis();
		int[] newArray = Arrays.copyOf(array, array.length + 10);
		long endTime = System.currentTimeMillis();
		System.out.print("time=" + (endTime - startTime));
		return newArray;
	}
	
	public static int[] testSystem(int[] array) {
		long startTime = System.currentTimeMillis();
		int[] newArray = new int[array.length + 10];
		System.arraycopy(array, 0, newArray, 0, array.length);
		long endTime = System.currentTimeMillis();
		System.out.print("time=" + (endTime - startTime));
		return newArray;
	}

}

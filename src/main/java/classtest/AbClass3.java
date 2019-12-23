package classtest;

import java.lang.reflect.ParameterizedType;

public class AbClass3 extends AbClass2<Integer, String>{

	public static void main(String[] args) {
		AbClass3 cls3 = new AbClass3();
		Class<?> class1 = cls3.tClass;
		System.out.println(class1.getName());
	}
}

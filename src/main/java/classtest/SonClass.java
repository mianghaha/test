package classtest;

import java.util.HashMap;
import java.util.Map;

public class SonClass extends ParentClass{
	
	//重写后两个和父类的静态变量就分离了
//	public static Map<String, String> j = new HashMap<String, String>();

	@Override
	public void test() {
		System.out.println(ParentClass.j.size());
		System.out.println(SonClass.j.size());
	}
	
	public static void main(String[] args) {
		SonClass sonClass = new SonClass();
		sonClass.test();
		SonClass.test2();
	}
	
	public static void test2() {
		System.out.println("SonClass test2");
	}
}

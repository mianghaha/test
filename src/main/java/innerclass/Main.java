package innerclass;

import innerclass.OuterClass.InnerClass;

public class Main {

	public static void main(String[] args) {
		OuterClass outerClass = new OuterClass();
		System.out.println("outerClass.hashcode=" + outerClass.hashCode());
		InnerClass innerClass = outerClass.new InnerClass();
		innerClass.test();
	}
}

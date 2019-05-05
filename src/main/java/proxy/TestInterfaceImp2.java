package proxy;

import scala.reflect.internal.Trees.This;

public class TestInterfaceImp2 implements TestInterface{

	public void test() {
		System.out.println(this.getClass().getName());
	}
}

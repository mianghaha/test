package classtest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LoadClass extends Thread{
	
	private static List<Integer> list2;
	
	static {
		System.out.println("static start");
		f();
		list2 = new ArrayList<Integer>();
		System.out.println("static end");
	}
	
	LoadClass(List<Integer> list){
		this.list = list;
		System.out.println("construct");
	}
	
	List<Integer> list;

	public static void f() {
		LoadClass l = new LoadClass(new LinkedList<Integer>());
		l.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("f is done");
	}
	
	public void run() {
		System.out.println("LoadClass is start,list=" + list);
		list2.add(1);
		System.out.println("LoadClass is end,list=" + list);
	}
	
	public static void main(String[] args) {
		LoadClass l1;
//		f();
		System.out.println("LoadClass main is run");
	}
}

package list;

import java.util.LinkedList;

public class LinkedListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList<String> list = new LinkedList();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		System.out.println(list.pop());
		System.out.println(list.pollLast());
	}

}

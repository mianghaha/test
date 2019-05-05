package list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class ArrayListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> arrayList = new ArrayList<>();
		LinkedList<Integer> linkedList = new LinkedList<>();
		Vector<Integer> vector = new Vector<>();
		Stack<Integer> stack = new Stack<>();
		
		Object[] aObjects1 = {1,2,3,4};
		Object[] aObjects2 = Arrays.copyOf(aObjects1, 10);
		
		int a = 10;
		int b = a + (a>>1);
		System.out.println(b);
		
		
//		for(int i = 0; i < 1000000; i++){
//			arrayList.add(i);
//			linkedList.add(i);
//		}
//		
//		long starttime = System.currentTimeMillis();
//		arrayList.remove(100);
//		long endtime = System.currentTimeMillis();
//		long sec = endtime - starttime;
//		System.out.println("arrayList remove time=" + sec);
//		
//		starttime = System.currentTimeMillis();
//		linkedList.remove(1000);
//		endtime = System.currentTimeMillis();
//		sec = endtime - starttime;
//		System.out.println("linkedList remove time=" + sec);
		
		arrayList.add(0);
		arrayList.add(1);
		arrayList.add(2);
		
		List<Integer> blist = new ArrayList<Integer>();
		for(Integer i : arrayList){
			blist.add(0, i);
		}
		
//		arrayList= new ArrayList<>();
		arrayList = arrayList.subList(1, arrayList.size());
		
		
		System.out.println(arrayList.size());
	}
	
	
	public static enum ERROR{
		
		SUCCESS(0, "success");

		
		private ERROR(int retcode, String msg){
			this.msg = msg;
			this.retcode = retcode;
		}
		
		private String msg;
		private int retcode;
		
		/**
		 * @return the msg
		 */
		public String getMsg() {
			return msg;
		}
		/**
		 * @param msg the msg to set
		 */
		public void setMsg(String msg) {
			this.msg = msg;
		}
		/**
		 * @return the retcode
		 */
		public int getRetcode() {
			return retcode;
		}
		/**
		 * @param retcode the retcode to set
		 */
		public void setRetcode(int retcode) {
			this.retcode = retcode;
		}
		
	}

}

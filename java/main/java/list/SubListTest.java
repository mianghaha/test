package list;

import java.util.ArrayList;
import java.util.List;

import utils.JsonUtil;

public class SubListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> a = new ArrayList<String>();
		a.add("1");
		a.add("2");
		a.add("3");
		a.add("4");
		a.add("5");
		a.add("6");
		a.add("7");
		a.add("8");
		a = a.subList(0, 1);
		try{
			System.out.println(JsonUtil.TransToJson(a));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}

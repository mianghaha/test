package json;

import java.util.HashSet;
import java.util.Set;

import utils.JsonUtil;

public class SetTest {

	public static void main(String[] args) {
		
		try{
			String a = "[1,2,3,4,5,1]";
			Set set = JsonUtil.TransToObject(a, HashSet.class);
			System.out.println(set);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}

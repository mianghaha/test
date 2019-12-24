package csproxytest.xlegend;

import java.util.SortedMap;
import java.util.TreeMap;

import utils.HttpClientUtil;

public class TestGetAllRoleServers {

	public static void main(String[] args){
		try{
			String url = "http://127.0.0.1:9097/xlegend/get_all_role_server";
			
			String xluserid = "24101003100210003268709$zilong";
			
			SortedMap<String, Object> param =  new TreeMap<String, Object>();
			param.put("xluserid", xluserid);
			
			String encode = "UTF-8";
			String result = HttpClientUtil.invokePost(url, param, encode, 10000, 10000);
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}

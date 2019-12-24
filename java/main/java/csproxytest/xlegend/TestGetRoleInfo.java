package csproxytest.xlegend;

import java.util.SortedMap;
import java.util.TreeMap;

import utils.HttpClientUtil;

public class TestGetRoleInfo {

	public static void main(String[] args){
		try{
			String url = "http://127.0.0.1:9097/xlegend/get_role_info";
			
			String sid = "19990";
			String cid  = "4295052822";
			
			SortedMap<String, Object> param =  new TreeMap<String, Object>();
			param.put("sid", sid);
			param.put("cid", cid);
			
			String encode = "UTF-8";
			String result = HttpClientUtil.invokePost(url, param, encode, 10000, 10000);
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}

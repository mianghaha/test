package csproxytest.xlegend;

import java.util.SortedMap;
import java.util.TreeMap;

import utils.HttpClientUtil;

public class TestGetUserid {

	public static void main(String[] args){
		try{
			String url = "http://127.0.0.1:9097/xlegend/get_userid";
			
//			String type = "aid";	
//			String sid = "19990";
//			String data  = "123$zulong";
			
//			String type = "cid";	
//			String sid = "19990";
//			String data  = "15224342";
			
			String type = "cname";	
			String sid = "19990";
			String data  = "寻常露易丝";
			
			SortedMap<String, Object> param =  new TreeMap<String, Object>();
			param.put("type", type);
			param.put("sid", sid);
			param.put("data", data);
			
			String encode = "UTF-8";
			String result = HttpClientUtil.invokePost(url, param, encode, 10000, 10000);
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}

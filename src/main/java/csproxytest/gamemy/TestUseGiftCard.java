package csproxytest.gamemy;

import java.util.SortedMap;
import java.util.TreeMap;

import utils.Byte2CharUtil;
import utils.HttpClientUtil;
import utils.JsonUtil;
import utils.MD5Util;

public class TestUseGiftCard {

	public static void main(String[] args){
		try{
//			String url = "http://localhost:80/gamemy/use_gift_card";
//			String url = "http://10.236.100.28:19080/gamemy/use_gift_card";
			String url = "http://52.74.215.203:8080/gamemy/use_gift_card";
//			String url = "http://210.211.122.37:8080/gamemy/use_gift_card";
			

			String str_privatekey = "5b2a731504746ef1b4b2e91c24ed998016f23c15";
			String userid = "1587461";
			int serverid  = 4000;
			String key = "123";
			long roleid = 123123;
			String title = "title";
			String content = "content";
			
			String str_sign = content + key + roleid + serverid + title  + userid + str_privatekey;
			String sign = new String(Byte2CharUtil.encodeHex(MD5Util.getMD5(str_sign.getBytes("UTF-8"))));
//			String sign = "222";
			
			SortedMap<String, Object> param =  new TreeMap<String, Object>();
			param.put("userid", userid);
			param.put("serverid", serverid);
			param.put("key", key);
			param.put("roleid", roleid);
			param.put("title", title);
			param.put("content", content);
			param.put("flag", sign);
			
//			String str_param = JsonUtil.TransToJson(param);
			String encode = "UTF-8";
			String result = HttpClientUtil.invokePost(url, param, encode, 10000, 10000);
			System.out.println(result);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}

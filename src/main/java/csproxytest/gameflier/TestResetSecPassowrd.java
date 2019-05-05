package csproxytest.gameflier;

import java.util.SortedMap;
import java.util.TreeMap;

import utils.Byte2CharUtil;
import utils.HttpClientUtil;
import utils.JsonUtil;
import utils.MD5Util;

public class TestResetSecPassowrd {

	public static void main(String[] args){
		try{
//			String url = "http://localhost:9080/gameflier/reset_sec_password";
			String url = "http://210.242.231.1:8080/gameflier/reset_sec_password";
			

			String str_privatekey = "8CEE3E6C88C4F5BC0CFED86DD3C1E573";
			String userid = "491";
			int serverid  = 4611;
			long timestamp = System.currentTimeMillis();
			
			String str_sign = userid + serverid + timestamp + str_privatekey;
			String sign = new String(Byte2CharUtil.encodeHex(MD5Util.getMD5(str_sign.getBytes("UTF-8"))));
			
			SortedMap<String, Object> param =  new TreeMap<String, Object>();
			param.put("userid", userid);
			param.put("server", serverid);
			param.put("timestamp", timestamp);
			param.put("sign", sign);
			
			String encode = "UTF-8";
			String result = HttpClientUtil.invokePost(url, param, encode, 10000, 10000);
			System.out.println(result);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}

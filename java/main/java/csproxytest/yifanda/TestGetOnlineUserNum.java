package csproxytest.yifanda;

import java.util.SortedMap;
import java.util.TreeMap;

import utils.Byte2CharUtil;
import utils.HttpClientUtil;
import utils.MD5Util;

public class TestGetOnlineUserNum {

	public static void main(String[] args){
		try{
//			String url = "http://localhost:80/evatar/online_user_num";
//			String url = "http://10.236.100.28:19080/evatar/online_user_num";
//			String url = "http://218.32.221.67:8080/evatar/online_user_num";
			String url = "http://218.32.4.103:10080/evatar/online_user_num";
				
			String str_privatekey = "8CEE3E6C88C4F5BC0CFED86DD3C1E573";
			String gameCode = "208";	
			String type = "1";
			String serverCodes  = "2999";
			String str_sign =  gameCode + type + str_privatekey;
			String md5Str = new String(Byte2CharUtil.encodeHex(MD5Util.getMD5(str_sign.getBytes("UTF-8"))));
			
			
			SortedMap<String, Object> param =  new TreeMap<String, Object>();
			param.put("gameCode", gameCode);
			param.put("type", type);
			param.put("serverCodes", serverCodes);
			param.put("md5Str", md5Str);
			
			String encode = "UTF-8";
			String result = HttpClientUtil.invokePost(url, param, encode, 10000, 10000);
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}

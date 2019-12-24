package csproxytest.yifanda;

import java.util.SortedMap;
import java.util.TreeMap;

import utils.Byte2CharUtil;
import utils.HttpClientUtil;
import utils.MD5Util;

public class TestGetRolelist {

	public static void main(String[] args){
		try{
//			String url = "http://localhost:80/evatar/get_role_info_list";
			String url = "http://10.236.100.28:19080/evatar/get_role_info_list";
//			String url = "http://218.32.221.67:8080/evatar/get_role_info_list";

			String str_privatekey = "8CEE3E6C88C4F5BC0CFED86DD3C1E573";
			String gameCode = "208";
			String userId = "1587461";
//			String userId = "guoyi";
			int serverCode  = 2999;
//			int serverCode  = 8888;
			String str_sign = str_privatekey + userId + serverCode;
			String sign = new String(Byte2CharUtil.encodeHex(MD5Util.getMD5(str_sign.getBytes("UTF-8"))));
			
			
			SortedMap<String, Object> param =  new TreeMap<String, Object>();
			param.put("gameCode", gameCode);
			param.put("userId", userId);
			param.put("serverCode", serverCode);
			param.put("sign", sign);
			
			String encode = "UTF-8";
			String result = HttpClientUtil.invokePost(url, param, encode, 10000, 10000);
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}

package csproxytest.efun;

import java.util.SortedMap;
import java.util.TreeMap;

import utils.Byte2CharUtil;
import utils.HttpClientUtil;
import utils.MD5Util;

public class TestGetRolelist {

	public static void main(String[] args){
		try{
//			String url = "http://localhost:9080/efun/get_role_info_list";
//			String url = "http://10.236.100.28:19080/efun/get_role_info_list";
//			String url = "http://58.229.185.87:8080/efun/get_role_info_list";
//			String url = "http://113.196.235.168:10080/efun/get_role_info_list";
			String url = "http://119.28.162.174:10080/efun/get_role_info_list";
			
			
			String str_privatekey = "480936FC9CE8ED75764E992896917BE2";
			String gameCode = "krqqh";
			String userId = "4205592422";
//			String userId = "guoyi";
			int serverCode  = 5910;
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

package csproxytest.gamemy;

import java.util.SortedMap;
import java.util.TreeMap;

import utils.Byte2CharUtil;
import utils.HttpClientUtil;
import utils.MD5Util;

public class TestGetRolelist {

	public static void main(String[] args){
		try{
//			String url = "http://localhost:80/evatar/get_role_info_list";
//			String url = "http://10.236.100.28:19080/evatar/get_role_info_list";
			String url = "http://52.74.215.203:8080/gamemy/get_role_info_list";

			String str_privatekey = "52584CCF41A575684590D4B0A316705E";
			String userid = "1587461";
//			String userId = "guoyi";
			int serverid  = 1;
//			int serverCode  = 8888;
			String str_sign = str_privatekey + userid + serverid;
			String sign = new String(Byte2CharUtil.encodeHex(MD5Util.getMD5(str_sign.getBytes("UTF-8"))));
			
			
			SortedMap<String, Object> param =  new TreeMap<String, Object>();
			param.put("userid", userid);
			param.put("serverid", serverid);
			param.put("sign", sign);
			
			String encode = "UTF-8";
			String result = HttpClientUtil.invokePost(url, param, encode, 10000, 10000);
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}

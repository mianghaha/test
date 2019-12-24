package rsa;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import utils.Byte2CharUtil;
import utils.HttpClientUtil;
import utils.JsonUtil;
import utils.MD5Util;
import utils.RSAUtil;


public class md5rsa {

	public static void main(String[] args){
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> param = new HashMap<>();
		param.put("account", "20186$gz37wan");
		param.put("roleid", 0);
		param.put("serverid", 5905);
		

		
		String str_privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMNnvzgjnOCnIPrhALAP+gThkH1to/UOx024d7zg+QBR6ZM9yXBbSh2V+lMvv6A91Jbn1wUFp/atUBSPkT5LF1udn2LIgFmHmAPXpP01wW1wAVVx3PfkIdM5pvAynQzpZ7mXajr1dttqc5s8TKnDLTprhW1C6QNbhkgI7NKFZzStAgMBAAECgYAVkB1J1KQ7JjY9eRbaVukAIOQQ80hkgnz71p9f+HOB1Ygw92CDRY+oNCSriVlcFXagMHVJ2JXwYq+zlSQx+5dYE1RZnNGXop4FIPoNyVwokOzhmSB47EtLUj/xXnLtgy6iEyIaakwQI6GhSxl9MdNmSkEaHHTyjVeEr3l9G57XAQJBAO7ybQZIWdg1NmeS9OPv9CACcsyJsS3iFQ58plvMjsAROpqtkkpgHFYQOlRcG6lGzElWZAA9RTVojy3Oc3jMQAMCQQDRWc/FOGhQmghgPa/YQz8UVXNsnWX6EHbkiU+PTKvSQMmvkACVVmCBoqNJilCIUlHl4vzi/MTdQ5jkPsaDr9GPAkBv+5lwylOg6j9XBx5R/NDSd5NJnHY25K+tnGlyuElmZiyq9RGMXcKp6u0LQCeOi6mN7Tjsi8p1n00K0Kh5Qm/1AkBuxm0wWHaqH7dK5ZlEf+DC66xVzruFJG0k5JtaoiFQ8cNGyJXmb3xFXD+2zB9s4tI/KGrT2Rh6VCh0rdrxF3l1AkEAkvN3/QhIDrdBjZlhsm9wuH4Q+FMYwHc7CktY7uPRHLtARzJeJwjkOJ5YJhqptJ2es5s4GH2ZI2fnNnLFb14rUw==";
		String str_publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDZ784I5zgpyD64QCwD/oE4ZB9baP1DsdNuHe84PkAUemTPclwW0odlfpTL7+gPdSW59cFBaf2rVAUj5E+SxdbnZ9iyIBZh5gD16T9NcFtcAFVcdz35CHTOabwMp0M6We5l2o69XbbanObPEypwy06a4VtQukDW4ZICOzShWc0rQIDAQAB";
		
		try {
			PrivateKey privatekey = RSAUtil.getPrivateKey(str_privatekey);
			
			String json = JsonUtil.TransToJson(param);
			System.out.println("json:" + json);
			String md5 = new String(Byte2CharUtil.encodeHex(MD5Util.getMD5(json.getBytes("UTF-8"))));
			System.out.println("md5:" + md5);
			String sign = RSAUtil.sign(md5.getBytes("UTF-8"), privatekey);
			System.out.println("sign:" + sign);
			data.put("param", param);
			data.put("gameid", 203);
			data.put("sign", sign);
			String content = JsonUtil.TransToJson(data);
			

			String result = HttpClientUtil.invokePost("http://52.77.37.10:7080/service/getroleinfobyaccount", content, "UTF-8", 10000, 0);
			System.out.println(result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}

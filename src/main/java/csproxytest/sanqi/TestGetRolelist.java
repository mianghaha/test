package csproxytest.sanqi;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.SortedMap;
import java.util.TreeMap;

import utils.Byte2CharUtil;
import utils.HttpClientUtil;
import utils.JsonUtil;
import utils.MD5Util;
import utils.RSAUtil;
import utils.RSAUtils;
import utils.Util;

public class TestGetRolelist {

	public static void main(String[] args){
		try{
			String encode = "UTF-8";
//			String url = "http://localhost:80/37union/get_role_info_list";
//			String url = "http://10.236.100.28:190106.75.193i80/37union/get_role_info_list";
//			String url = "http://52.74.215.203:8080/37union/get_role_info_list";
//			String url = "http://52.220.114.107:8080/37union/get_role_info_list";
			String url = "http://52.220.197.102:8080/37union/get_role_info_list";

			String str_privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANnI2onrd4eU4zzXo9sY3qpU+7GgqSMhbPoOzxmCu/4beSKFwNDlkwb7Jlk5gHIGNbtSnOk2eWTuXPXgYDOyMJS+XSd9yNN0nOqmq1a03nvgKVqGGaFkJhLP8zDXjP8O/xfVAhE61IIUzf2PbPWTjFWplBeiDro+P+VwrUYBm/rjAgMBAAECgYEAilArqygmc681WnDBmeaDk74BU+BrSupxoaZCFTuYVVvxZOF5gbIr3GUpB8WjM8eJ3HwtN1fnZRiVZXqo3mwuuTz0LCv3Jeis8OqaOuHzt2ef2mPZAB3jVPmt+WWLIR99C4Md5RN9ffIfGyROip0tDwthL8pf4yJgIhjND5S793ECQQDu7UZguOHPYZeGQ1eHLU1LJYgbSbd1yjoyGgxTmHI2rdKdwQsk4DpXYyxaTvwuiLZA0NAeM2mENazYXYbI2ZwNAkEA6VjR67OpRC60ydw/BUEiaHCjXfYPW67M0EPS/5qDgSzBGZ/ELgYukvWajpLBiWDjkUHD6EkbQHjH/xfV8XoGrwJAYKhrDVweNjiEBVQfB9fC6kC/xFJZPvTWAEjbbcJBim9dwmZDbOKtl4bOfaZwjR7PpH8VgvJHoK3aRBnqGj02zQJAW4b1QAiGejH/w7XaGkuEHYcg7TgYqhOUTpRr7MpEjqRpUY2zO4I9PnOLniS+OQ6B9HXhS/lw8QbvG4XT6xW8DQJAAuDD15xetTOzJgPwdKSVgePRi9XKJ4oZ1NdZIoWkDUGfq+0AVIrNi+yyLDEnqepMNu9ffP6EmDH8EzWf/XSGAg==";
			String str_publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZyNqJ63eHlOM816PbGN6qVPuxoKkjIWz6Ds8Zgrv+G3kihcDQ5ZMG+yZZOYByBjW7UpzpNnlk7lz14GAzsjCUvl0nfcjTdJzqpqtWtN574ClahhmhZCYSz/Mw14z/Dv8X1QIROtSCFM39j2z1k4xVqZQXog66Pj/lcK1GAZv64wIDAQAB";
			String userid = "10000392";
			int serverid  = 4817;
			String cmd = "";
			
			SortedMap<String, Object> param =  new TreeMap<String, Object>();
			param.put("userID", userid);
			param.put("serverID", serverid);
			param.put("cmd",cmd);
//			String verifyStr = "";
//			for(Object value : param.values()){
//				verifyStr = verifyStr + String.valueOf(value);
//			}
//			
//			PrivateKey privateKey = RSAUtil.getPrivateKey(str_privatekey);
//			PublicKey publickey = RSAUtil.getPublicKey(str_publickey);
//			String sha1Str = Util.sha1sum(verifyStr);
//			String sign = RSAUtils.encryptToStr(publickey,sha1Str);
			String sign = "oN1HFVTPGb1eBlt02V2\\/oUNNgyzlc8DqVO041NHRkZrIqapXXQRBYmB0UCWZ4td4LelJ6Ov1FalFdMgW2kaDDJk4lxcYnbEoKb4yGk7IAzm\\/08jR7GnzEoexqTbLHpAdi3y9ZGYQ64WUbB2inOem5FPj+TwFfV2bYr6mUrGyrNAc3mnGqjM6ZTCBItiIU2RnRvaEbADQHB0ug2e8KjeqDq0Vzl5KTMcllBhFZbL5ha8J2TqX08nmHMPetZ3pz79SdepqkEbrrbC6jnizfF2fhs9auvcR8nRnHGlBaDM\\/kUFx\\/CsFuhKieB7PeTrlXYl18ZKJVIsp2J8K5+Nt9HnBWQ==";
			param.put("sign", sign);
			String str_param = JsonUtil.TransToJson(param);

			System.out.println(sign);

			String result = HttpClientUtil.invokePost(url, str_param, encode, 10000, 10000);
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}

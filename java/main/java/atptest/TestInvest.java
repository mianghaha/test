package atptest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import utils.Byte2CharUtil;
import utils.DateUtil;
import utils.HttpClientUtil;
import utils.MD5Util;

public class TestInvest {

	public static void main(String[] args){
		try{
			String url = "http://atp-test.zulong.com:3080/h5/investigation";
//			String url = "http://localhost:8080/actplatform/h5/investigation";
//			String url = "http://atp-lyb.zulong.com/h5/investigation";
//			String url = "http://atp-ql.zulong.com/h5/investigation";
//			String url = "http://atp-ql.zloong.com/h5/investigation";
				
//			String str_privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANnI2onrd4eU4zzXo9sY3qpU+7GgqSMhbPoOzxmCu/4beSKFwNDlkwb7Jlk5gHIGNbtSnOk2eWTuXPXgYDOyMJS+XSd9yNN0nOqmq1a03nvgKVqGGaFkJhLP8zDXjP8O/xfVAhE61IIUzf2PbPWTjFWplBeiDro+P+VwrUYBm/rjAgMBAAECgYEAilArqygmc681WnDBmeaDk74BU+BrSupxoaZCFTuYVVvxZOF5gbIr3GUpB8WjM8eJ3HwtN1fnZRiVZXqo3mwuuTz0LCv3Jeis8OqaOuHzt2ef2mPZAB3jVPmt+WWLIR99C4Md5RN9ffIfGyROip0tDwthL8pf4yJgIhjND5S793ECQQDu7UZguOHPYZeGQ1eHLU1LJYgbSbd1yjoyGgxTmHI2rdKdwQsk4DpXYyxaTvwuiLZA0NAeM2mENazYXYbI2ZwNAkEA6VjR67OpRC60ydw/BUEiaHCjXfYPW67M0EPS/5qDgSzBGZ/ELgYukvWajpLBiWDjkUHD6EkbQHjH/xfV8XoGrwJAYKhrDVweNjiEBVQfB9fC6kC/xFJZPvTWAEjbbcJBim9dwmZDbOKtl4bOfaZwjR7PpH8VgvJHoK3aRBnqGj02zQJAW4b1QAiGejH/w7XaGkuEHYcg7TgYqhOUTpRr7MpEjqRpUY2zO4I9PnOLniS+OQ6B9HXhS/lw8QbvG4XT6xW8DQJAAuDD15xetTOzJgPwdKSVgePRi9XKJ4oZ1NdZIoWkDUGfq+0AVIrNi+yyLDEnqepMNu9ffP6EmDH8EzWf/XSGAg==";
//			String gameid = "209";	
//			String userid = "265$zulong";
//			String serverid  = "2001";
//			String roleid = "541132753";
			
//			String str_privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANnI2onrd4eU4zzXo9sY3qpU+7GgqSMhbPoOzxmCu/4beSKFwNDlkwb7Jlk5gHIGNbtSnOk2eWTuXPXgYDOyMJS+XSd9yNN0nOqmq1a03nvgKVqGGaFkJhLP8zDXjP8O/xfVAhE61IIUzf2PbPWTjFWplBeiDro+P+VwrUYBm/rjAgMBAAECgYEAilArqygmc681WnDBmeaDk74BU+BrSupxoaZCFTuYVVvxZOF5gbIr3GUpB8WjM8eJ3HwtN1fnZRiVZXqo3mwuuTz0LCv3Jeis8OqaOuHzt2ef2mPZAB3jVPmt+WWLIR99C4Md5RN9ffIfGyROip0tDwthL8pf4yJgIhjND5S793ECQQDu7UZguOHPYZeGQ1eHLU1LJYgbSbd1yjoyGgxTmHI2rdKdwQsk4DpXYyxaTvwuiLZA0NAeM2mENazYXYbI2ZwNAkEA6VjR67OpRC60ydw/BUEiaHCjXfYPW67M0EPS/5qDgSzBGZ/ELgYukvWajpLBiWDjkUHD6EkbQHjH/xfV8XoGrwJAYKhrDVweNjiEBVQfB9fC6kC/xFJZPvTWAEjbbcJBim9dwmZDbOKtl4bOfaZwjR7PpH8VgvJHoK3aRBnqGj02zQJAW4b1QAiGejH/w7XaGkuEHYcg7TgYqhOUTpRr7MpEjqRpUY2zO4I9PnOLniS+OQ6B9HXhS/lw8QbvG4XT6xW8DQJAAuDD15xetTOzJgPwdKSVgePRi9XKJ4oZ1NdZIoWkDUGfq+0AVIrNi+yyLDEnqepMNu9ffP6EmDH8EzWf/XSGAg==";
//			String gameid = "215";	
//			String userid = "265$zulong";
//			String serverid  = "2001";
//			String roleid = "541132753";
			
			String str_privatekey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMA2u0eP1ywSwDqqq8fQwuFLqO2gLJ/3w69Vbe3GAtozdUT+6tlGPUMX7X4gcVVAtSDo90OoQbCakCdKfP7+bhTQyl3/vkkDok21eoxMsgxFg66vXAX7CsGCDqC8kO8FprQmRNGK0IL0selyyt7CNUvNMYqDcSqp8vX+DBL8V5CFAgMBAAECgYAaDNDg5/uve3MWAF97my1uP0TLv7OMkpWww5E8cmOckvDHwwytGbnfx/Rl7ZuuhDCIH0qznrAYcTosOQorvOvKJgLZ554LsO6basYHFtYIjhhVoRysxnN+ctCJfszY9GXKvbZqzQ4g2Ll+AAlx4yZXUNbYukWNzd6nQ/CRQzXKAQJBAPQVKRZx3qiLOXXn2hKpFmFHbT16YndWOn6yNM4rXoARNbqt6UmoCSJ0AqlQlH+FO/C8Bqaswsnd2Us+0rogB0ECQQDJmUBefdY9DHZgnpMwYECJpmMu1PXMcev/aavDU4dgyP7PKoU8dkyLrMn6bxv0ZlItGWlQR24PVoGPPWSUypxFAkEAhzdZxbdbVB+cu3av1nqZjVaWa+JqvhWk3h83Jafc+DGQhFRn0ogmV1HRT572RRa4OArR3HAEkPQ3tnmdH8MygQJAcpjhFFWn4RAigo3h0ZAFX7nri4WXK95ebtQcxW3H+OaIoi7jihkdLhDMx8U9ONKpR+W4DeBs2d8sysX1VPdPmQJBANd+jve8vg3EdjS/IfZDDaM5cFcZgQ4FkBeEKIvfR1Jjs5SbeJfoAgUGXxC5vpJb/hrx7+2i7+w/ifCc/718aJo=";
			String gameid = "219";	
			String userid = "265$zulong";
			String serverid  = "2001";
			String roleid = "541132753";
			String channelid = "202";
			
//			String str_date = "2017-03-31 05:29:00";
//			long st = DateUtil.ParseString(str_date).getTime();
//			String timestamp = String.valueOf(st/1000);
			String timestamp = System.currentTimeMillis()/1000 + "";

			String str_sign =  channelid + gameid + roleid + serverid + timestamp + userid + str_privatekey;
			String sign = new String(Byte2CharUtil.encodeHex(MD5Util.getMD5(str_sign.getBytes("UTF-8"))));
			
			SortedMap<String, String> param =  new TreeMap<String, String>();
			param.put("channelid", channelid);
			param.put("gameid", gameid);
			param.put("userid", userid);
			param.put("serverid", serverid);
			param.put("roleid", roleid);
			param.put("timestamp", timestamp);
			param.put("sign", sign);
			
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Accept-Language", "zh-TW,zh");
			
			String encode = "UTF-8";
			String result = HttpClientUtil.invokeGet(url, param, headers, encode, 10000, 10000);
//			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}

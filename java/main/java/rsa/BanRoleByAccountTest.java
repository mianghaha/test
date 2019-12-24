package rsa;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import utils.Byte2CharUtil;
import utils.HttpClientUtil;
import utils.JsonUtil;
import utils.MD5Util;
import utils.RSAUtil;

public class BanRoleByAccountTest {
public static void main(String[] args){
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> param = new HashMap<>();
		param.put("account", "20186$zilong");
		param.put("serverid", 5905);
		param.put("forbid_type", 100);
		param.put("forbid_time", 12);
		param.put("reason", "test");
		

		
//		String str_privatekey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANLIMmV6XBeklNiNWcwjZO/xH379q5j4nGsbn7oqNiIpgmt/RKhn908sNWakH9djV0hnUEIJRt15SlnW7yWtpFRgI23gNur5767nBaqTZpiJ+hViwBFwzsBma8SPeCz0JmOin6nXv4wyrzFcZdVYCAFBSh9VeAQFhxA3q4HF2qZ/AgMBAAECgYBHDmAW34neGohm/DeyXpgvXvy8Ja3MUrsATH6rFoHLQG0L6gpuLyYq1X9nC5sEVCPfb5yZtmDiVnofERrre77yaeC0uUdVL5HLyfzynHWlsImAR1CZWhsUipbc8gtbSnR2mgM0REK0vypY9ilCT1uNt+QS+9qnzY6fQtb6+qrDyQJBAPVMbsKFLXkMgI5WeVV8rFyT3KlBK9vleZ9KOJnKrkeC6aqqktVt9JhHOw0O5HFI0yJXknxPyk+7dwhet4lqOLUCQQDb+kXtOT/Q99BjCPD2QSChm2ACCLuI1QmTv3cFuQU3tpJgr7aG8rzhmQ5wxzqXBvaN4y/QY8D3mFl41Kd0cabjAkEAp7F36s1m1+EfJa+iRTn1D1kaOi9Oj4biTW5NIX45BLRPkBgKR5Ri6ZOQoA21SGjTxh9+CjP2B+oNpxAxguS2HQJBAJ8HkRQN5k/ZSCLArQsqTD/pBwCRdZpXgWP58D8H4njDmZEils96nwhtUV5A+d+DXi13h/Kcmz2kO1K6UhFBtMcCQQDuHiFCojd02TaOcNWHx/3Wl8tLOFzHgX42j7m5ccdNN9hIwld48ak050nQcwsB6gRAo/k/15MxhKqadgN3DNnI";
		
		
		String str_privatekey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANLIMmV6XBeklNiNWcwjZO/xH379q5j4nGsbn7oqNiIpgmt/RKhn908sNWakH9djV0hnUEIJRt15SlnW7yWtpFRgI23gNur5767nBaqTZpiJ+hViwBFwzsBma8SPeCz0JmOin6nXv4wyrzFcZdVYCAFBSh9VeAQFhxA3q4HF2qZ/AgMBAAECgYBHDmAW34neGohm/DeyXpgvXvy8Ja3MUrsATH6rFoHLQG0L6gpuLyYq1X9nC5sEVCPfb5yZtmDiVnofERrre77yaeC0uUdVL5HLyfzynHWlsImAR1CZWhsUipbc8gtbSnR2mgM0REK0vypY9ilCT1uNt+QS+9qnzY6fQtb6+qrDyQJBAPVMbsKFLXkMgI5WeVV8rFyT3KlBK9vleZ9KOJnKrkeC6aqqktVt9JhHOw0O5HFI0yJXknxPyk+7dwhet4lqOLUCQQDb+kXtOT/Q99BjCPD2QSChm2ACCLuI1QmTv3cFuQU3tpJgr7aG8rzhmQ5wxzqXBvaN4y/QY8D3mFl41Kd0cabjAkEAp7F36s1m1+EfJa+iRTn1D1kaOi9Oj4biTW5NIX45BLRPkBgKR5Ri6ZOQoA21SGjTxh9+CjP2B+oNpxAxguS2HQJBAJ8HkRQN5k/ZSCLArQsqTD/pBwCRdZpXgWP58D8H4njDmZEils96nwhtUV5A+d+DXi13h/Kcmz2kO1K6UhFBtMcCQQDuHiFCojd02TaOcNWHx/3Wl8tLOFzHgX42j7m5ccdNN9hIwld48ak050nQcwsB6gRAo/k/15MxhKqadgN3DNnI";
		
		try {
			PrivateKey privatekey = RSAUtil.getPrivateKey(str_privatekey);
			
			String json = JsonUtil.TransToJson(param);
			System.out.println("json:" + json);
			String md5 = new String(Byte2CharUtil.encodeHex(MD5Util.getMD5(json.getBytes("UTF-8"))));
			System.out.println("md5:" + md5);
			String sign = RSAUtil.sign(md5.getBytes("UTF-8"), privatekey);
			System.out.println("sign:" + sign);
			data.put("param", param);
			data.put("gameid", 206);
			data.put("sign", sign);
			String content = JsonUtil.TransToJson(data);
			

			String result = HttpClientUtil.invokePost("http://gmt.oa.zulong.com/service/banrolebyaccount", content, "UTF-8", 10000, 0);
			System.out.println(result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}

package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * 说明：
 * @author xiayisan 
 * @version 创建时间: 2015年1月26日 下午4:56:38
 *
 */
public class MD5Util
{
	/**
     * 用key对信息做MD5签名 ，然后做Base64编码
     */  
    public static String getMD5Sign(byte[] data, String key)
    {  
        try
        {
        	MessageDigest md = MessageDigest.getInstance("MD5");
        	md.update(data);
        	md.update(key.getBytes());
        	return Base64Util.encode(md.digest());
        }
        catch(Exception e)
        {
        	e.printStackTrace(System.out);
        }
        return null;
    } 
    
    /**
     * 对信息做MD5摘要(UTF-8) ，然后做Base64编码
     * @param data
     * @return
     */
    public static String getMD5Digest(String data){
    	try {
    		
        	MessageDigest md = MessageDigest.getInstance("MD5");
        	md.update(data.getBytes("UTF-8"));
			return new String(Byte2CharUtil.encodeHex(md.digest()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
	/**
     * 对信息做MD5摘要 ，然后做Base64编码
     */  
    public static String getMD5Digest(byte[] data)
    {  
        try
        {
        	MessageDigest md = MessageDigest.getInstance("MD5");
        	md.update(data);
        	return Base64Util.encode(md.digest());
        }
        catch(Exception e)
        {
        	e.printStackTrace(System.out);
        }
        return null;
    }  
    
    /**
     * 对信息做MD5摘要 
     */  
    public static byte[] getMD5(byte[] data)
    {  
        try
        {
        	MessageDigest md = MessageDigest.getInstance("MD5");
        	md.update(data);
        	return md.digest();
        }
        catch(Exception e)
        {
        	e.printStackTrace(System.out);
        }
        return null;
    }  
}

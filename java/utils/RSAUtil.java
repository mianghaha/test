package utils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;


/**
 * 说明：
 * @author xiayisan 
 * @version 创建时间: 2015年1月26日 下午4:40:39
 *
 */
public class RSAUtil
{
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";

	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	 /**
     * RSA最大加密明文大小 
     */  
    private static final int MAX_ENCRYPT_BLOCK = 117;  
      
    /**
     * RSA最大解密密文大小 
     */  
    private static final int MAX_DECRYPT_BLOCK = 128;  
    
	/**
	 * 生成密钥对(公钥和私钥)
	 */
	public static Map<String, Object> genKeyPair() throws Exception
	{
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	public static String getPublicKey(Map<String, Object> keyMap) throws Exception
	{
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		byte[] publicKey = key.getEncoded();
		return Base64Util.encode(publicKey);
	}

	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception
	{
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		byte[] privateKey = key.getEncoded();
		return Base64Util.encode(privateKey);
	}

	
	public static PublicKey getPublicKey(String publicKey) throws Exception
	{
		byte[] keyBytes = Base64Util.decode(publicKey);  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        return publicK;
	}
	
	public static PrivateKey getPrivateKey(String privateKey) throws Exception
	{
        byte[] keyBytes = Base64Util.decode(privateKey);  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
        return privateK;
	}
	
	
	
    /**
     * <p> 
     * 用私钥对信息生成数字签名 
     * </p> 
     *  
     * @param data 已加密数据 
     * @param privateKey 私钥(BASE64编码) 
     *  
     * @return 
     * @throws Exception 
     */  
    public static String sign(byte[] data, PrivateKey privateKey) throws Exception 
    {  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        signature.initSign(privateKey);  
        signature.update(data);  
        return Base64Util.encode(signature.sign());  
    }  
  
    /**
     * <p> 
     * 校验数字签名 
     * </p> 
     *  
     * @param data 已加密数据 
     * @param publicKey 公钥(BASE64编码) 
     * @param sign 数字签名 
     *  
     * @return 
     * @throws Exception 
     *  
     */  
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception
	{
		if(sign.length() < 1)
			return false;
		PublicKey publicK = getPublicKey(publicKey);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(Base64Util.decode(sign));
	}
    
    /**
     * <p> 
     * 公钥加密 
     * </p> 
     *  
     * @param data 源数据 
     * @param publicKey 公钥(BASE64编码) 
     * @return 
     * @throws Exception 
     */  
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception
	{
		PublicKey publicK = getPublicKey(publicKey);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0)
		{
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK)
			{
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else
			{
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}
  
    /** 
     * <p> 
     * 私钥加密 
     * </p> 
     *  
     * @param data 源数据 
     * @param privateKey 私钥(BASE64编码) 
     * @return 
     * @throws Exception 
     */  
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception
	{
		PrivateKey privateK = getPrivateKey(privateKey);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0)
		{
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK)
			{
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else
			{
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}  
    
    
    /** 
     * <P> 
     * 私钥解密 
     * </p> 
     *  
     * @param encryptedData 已加密数据 
     * @param privateKey 私钥(BASE64编码) 
     * @return 
     * @throws Exception 
     */  
	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception
	{
		PrivateKey privateK = getPrivateKey(privateKey);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0)
		{
			if (inputLen - offSet > MAX_DECRYPT_BLOCK)
			{
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else
			{
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}
  
    /** 
     * <p> 
     * 公钥解密 
     * </p> 
     *  
     * @param encryptedData 已加密数据 
     * @param publicKey 公钥(BASE64编码) 
     * @return 
     * @throws Exception 
     */  
	public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception
	{
		PublicKey publicK = getPublicKey(publicKey);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0)
		{
			if (inputLen - offSet > MAX_DECRYPT_BLOCK)
			{
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else
			{
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}
    
	static String publicKey = null;
	static String privateKey = null;
	
	static String publicKey2 = null;
	static String privateKey2 = null;
	
	public static void main(String args[])
	{
		try
		{
			Map<String, Object> map = genKeyPair();
			publicKey = getPublicKey(map);
			privateKey = getPrivateKey(map);
			System.out.println("publickey:size=" + publicKey.length() + " " + publicKey);
			System.out.println("privatekey:size=" + privateKey.length() + " " + privateKey);
			
			map = genKeyPair();
			publicKey2 = getPublicKey(map);
			privateKey2 = getPrivateKey(map);
			
			test();
			testSign();

		} catch (Exception e)
		{
			e.printStackTrace(System.out);
		}
	}
	
	static void test() throws Exception {  
        System.out.println("公钥加密——私钥解密---------------------");  
        String source = "测试RSA加解密，祖龙娱乐AAAAAAAAAAAAAA";  
        System.out.println("原文字：" + source);  
        byte[] data = source.getBytes();  
        byte[] encodedData = RSAUtil.encryptByPublicKey(data, publicKey);  
        System.out.println("加密后：" + Base64Util.encode(encodedData));  
        byte[] decodedData = RSAUtil.decryptByPrivateKey(encodedData, privateKey);  
        System.out.println("解密后: " + new String(decodedData));  
    }  
  
    static void testSign() throws Exception {  
        System.out.println("\n私钥加密——公钥解密---------------------");  
        String source = "测试RSA加解密，祖龙娱乐BBBBBBBBBBBBBB";  
        System.out.println("原文字：" + source);  
        byte[] data = source.getBytes();  
        byte[] encodedData = RSAUtil.encryptByPrivateKey(data, privateKey);  
        System.out.println("加密后：" + Base64Util.encode(encodedData));  
        byte[] decodedData = RSAUtil.decryptByPublicKey(encodedData, publicKey);  
        System.out.println("解密后: " + new String(decodedData));    
        
        
        System.out.println("\n私钥签名——公钥验证签名---------------------");  
        String signsource = "123233";
        String sign = RSAUtil.sign(signsource.getBytes(), getPrivateKey(privateKey2));  
        System.out.println("签名：" + sign + "    " + sign.length());  
        boolean status = RSAUtil.verify(signsource.getBytes(), publicKey2, sign);  
        System.err.println("验证结果：" + status);  
    }  

}


/**
 * 
 */
package csproxytest.tencent;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * HMAC加密算法
 * 
 */
public class HMAC
{
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final String HMAC_MD5 = "HmacMD5";
    private static final String OCTETS_CHARSET_UTF8 = "UTF-8";

    /**
     * 生成HmacSHA1签名数据
     * 
     * @param data
     *            待加密的数据
     * @param key
     *            加密使用的key
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] hmacSha1(final String data, final String key) throws Exception
    {
        final byte[] dataBytes = data.getBytes(OCTETS_CHARSET_UTF8);
        final byte[] keyBytes = key.getBytes(OCTETS_CHARSET_UTF8);

        return hmacSha1(dataBytes, keyBytes);
    }

    /**
     * 生成HmacSHA1签名数据
     * 
     * @param data
     *            待加密的数据
     * @param key
     *            加密使用的key
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] hmacSha1(final byte[] data, final byte[] key) throws Exception
    {
        // 根据给定的字节数组构造一个密钥, 第二参数指定一个密钥算法的名
        final SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);

        // 生成一个指定 Mac 算法 的 Mac 对象
        final Mac mac = Mac.getInstance(HMAC_SHA1);

        // 用给定密钥初始化 Mac 对象
        mac.init(signingKey);

        // 完成 Mac 操作
        byte[] rawHmac = mac.doFinal(data);

        return rawHmac;
    }

    /**
     * 生成HmacSHA256签名数据
     * 
     * @param data
     *            待加密的数据
     * @param key
     *            加密使用的key
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] hmacSha256(final String data, final String key) throws Exception
    {
        final byte[] dataBytes = data.getBytes(OCTETS_CHARSET_UTF8);
        final byte[] keyBytes = key.getBytes(OCTETS_CHARSET_UTF8);

        return hmacSha256(dataBytes, keyBytes);
    }

    /**
     * 生成HmacSHA256签名数据
     * 
     * @param data
     *            待加密的数据
     * @param key
     *            加密使用的key
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] hmacSha256(final byte[] data, final byte[] key) throws Exception
    {
        // 根据给定的字节数组构造一个密钥, 第二参数指定一个密钥算法的名
        final SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA256);

        // 生成一个指定 Mac 算法 的 Mac 对象
        final Mac mac = Mac.getInstance(HMAC_SHA256);

        // 用给定密钥初始化 Mac 对象
        mac.init(signingKey);

        // 完成 Mac 操作
        byte[] rawHmac = mac.doFinal(data);

        return rawHmac;
    }

    /**
     * 生成HmacMD5签名数据
     * 
     * @param data
     *            待加密的数据
     * @param key
     *            加密使用的key
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] hmacMd5(final String data, final String key) throws Exception
    {
        final byte[] dataBytes = data.getBytes(OCTETS_CHARSET_UTF8);
        final byte[] keyBytes = key.getBytes(OCTETS_CHARSET_UTF8);

        return hmacMd5(dataBytes, keyBytes);
    }

    /**
     * 生成HmacMD5签名数据
     * 
     * @param data
     *            待加密的数据
     * @param key
     *            加密使用的key
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] hmacMd5(final byte[] data, final byte[] key) throws Exception
    {
        // 根据给定的字节数组构造一个密钥, 第二参数指定一个密钥算法的名
        final SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_MD5);

        // 生成一个指定 Mac 算法 的 Mac 对象
        final Mac mac = Mac.getInstance(HMAC_MD5);

        // 用给定密钥初始化 Mac 对象
        mac.init(signingKey);

        // 完成 Mac 操作
        byte[] rawHmac = mac.doFinal(data);

        return rawHmac;
    }
}

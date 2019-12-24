package utils;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPrivateKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

public class RSAUtils
{
    public static final String OCTETS_CHARSET_UTF8 = "UTF-8";
    /**
     * 签名算法
     */
    public static enum RSASignatureAlgorithm
    {
        SHA1WithRSA("SHA1WithRSA"), MD5withRSA("MD5withRSA");

        final String name;

        private RSASignatureAlgorithm(final String algorithmName)
        {
            this.name = algorithmName;
        }
    }

    /**
     * 获取公钥
     * 
     * @param rsaPublicKey
     *            Base64编码公钥信息
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(final String rsaPublicKey) throws Exception
    {
        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final byte[] encodedKey = Base64.decode(rsaPublicKey);
        final PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

        return pubKey;
    }

    /**
     * 获取私钥
     * 
     * @param rsaPrivateKey
     *            Base64编码私钥信息
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(final String rsaPrivateKey) throws Exception
    {
        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final byte[] encodedKey = Base64.decode(rsaPrivateKey);
        final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(encodedKey);
        final PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        return priKey;
    }

    /**
     * 获取公钥bits数目
     * 
     * @param pubKey
     *            公钥
     * @return
     */
    public static int getKeyBits(final PublicKey pubKey) throws Exception
    {
        if (pubKey instanceof RSAPublicKeyImpl)
        {
            RSAPublicKeyImpl impl = (RSAPublicKeyImpl) pubKey;
            return impl.getModulus().bitLength();
        }
        else
        {
            throw new IllegalArgumentException("public key format not supported");
        }
    }

    /**
     * 获取私钥bits数目
     * 
     * @param priKey
     * @return
     * @throws Exception
     */
    public static int getKeyBits(final PrivateKey priKey) throws Exception
    {
        if (priKey instanceof RSAPrivateKeyImpl)
        {
            RSAPrivateKeyImpl impl = (RSAPrivateKeyImpl) priKey;
            return impl.getModulus().bitLength();
        }
        else if (priKey instanceof RSAPrivateCrtKeyImpl)
        {
            RSAPrivateCrtKeyImpl impl = (RSAPrivateCrtKeyImpl) priKey;
            return impl.getModulus().bitLength();
        }
        else
        {
            throw new IllegalArgumentException("private key format not supported");
        }
    }

    /**
     * 获取私钥加密数据块最大字节数
     * 
     * @param priKey
     * @return
     * @throws Exception
     */
    public static int getEncryptBlockSize(final PrivateKey priKey) throws Exception
    {
        final int bits = getKeyBits(priKey);
        return (((bits - 1) >> 3) + 1 - 11);
    }

    /**
     * 获取使用公钥加密数据块最大字节数
     * 
     * @param pubKey
     *            公钥
     * @return
     * @throws Exception
     */
    public static int getEncryptBlockSize(final PublicKey pubKey) throws Exception
    {
        final int bits = getKeyBits(pubKey);
        return (((bits - 1) >> 3) + 1 - 11);
    }

    /**
     * 获取使用私钥解密数据块最大字节数
     * 
     * @param priKey
     *            私钥
     * @return
     * @throws Exception
     */
    public static int getDecryptBlockSize(final PrivateKey priKey) throws Exception
    {
        final int bits = getKeyBits(priKey);
        return (((bits - 1) >> 3) + 1);
    }

    /**
     * 获取使用公钥解密数据块最大字节数
     * 
     * @param pubKey
     *            公钥
     * @return
     * @throws Exception
     */
    public static int getDecryptBlockSize(final PublicKey pubKey) throws Exception
    {
        final int bits = getKeyBits(pubKey);
        return (((bits - 1) >> 3) + 1);
    }

    // ===============================================================================================================

    /**
     * 签名
     * 
     * @param privateKey
     *            Base64编码私钥
     * @param content
     *            待签名内容
     * @return
     */
    public static String sign(final String privateKey, final String content) throws Exception
    {
        return sign(privateKey, content, RSASignatureAlgorithm.SHA1WithRSA);
    }

    /**
     * 签名
     * 
     * @param privateKey
     *            Base64编码私钥
     * @param content
     *            待签名内容
     * @param algorithm
     *            签名算法
     * @return
     * @throws
     */
    public static String sign(final String privateKey, final String content, final RSASignatureAlgorithm algorithm)
            throws Exception
    {
        final byte[] bytes = content.getBytes(OCTETS_CHARSET_UTF8);
        return sign(privateKey, bytes, algorithm);
    }

    /**
     * 签名
     * 
     * @param privateKey
     *            Base64编码私钥
     * @param content
     *            待签名内容
     * @return
     */
    public static String sign(final String privateKey, final byte[] content) throws Exception
    {
        return sign(privateKey, content, RSASignatureAlgorithm.SHA1WithRSA);
    }

    /**
     * 签名
     * 
     * @param privateKey
     *            Base64编码私钥
     * @param content
     *            待签名内容
     * @param algorithm
     *            签名算法
     * @return
     */
    public static String sign(final String privateKey, final byte[] content, final RSASignatureAlgorithm algorithm)
            throws Exception
    {
        final java.security.Signature signature = java.security.Signature.getInstance(algorithm.name);
        final PrivateKey priKey = getPrivateKey(privateKey);
        signature.initSign(priKey);
        signature.update(content);

        final byte[] signed = signature.sign();
        return Base64.encode(signed, OCTETS_CHARSET_UTF8);

    }

    /**
     * 签名
     * 
     * @param privateKey
     *            私钥
     * @param content
     *            待签名内容
     * @return
     */
    public static String sign(final PrivateKey privateKey, final String content) throws Exception
    {
        return sign(privateKey, content, RSASignatureAlgorithm.SHA1WithRSA);
    }

    /**
     * 签名
     * 
     * @param privateKey
     *            私钥
     * @param content
     *            待签名内容
     * @param algorithm
     *            签名算法
     * @return
     * @throws
     */
    public static String sign(final PrivateKey privateKey, final String content, final RSASignatureAlgorithm algorithm)
            throws Exception
    {
        final byte[] bytes = content.getBytes(OCTETS_CHARSET_UTF8);

        return sign(privateKey, bytes, algorithm);
    }

    /**
     * 签名
     * 
     * @param privateKey
     *            私钥
     * @param content
     *            待签名内容
     * @return
     */
    public static String sign(final PrivateKey privateKey, final byte[] content) throws Exception
    {
        return sign(privateKey, content, RSASignatureAlgorithm.SHA1WithRSA);
    }

    /**
     * 签名
     * 
     * @param privateKey
     *            私钥
     * @param content
     *            待签名内容
     * @param algorithm
     *            签名算法
     * @return
     */
    public static String sign(final PrivateKey privateKey, final byte[] content, final RSASignatureAlgorithm algorithm)
            throws Exception
    {
        final java.security.Signature signature = java.security.Signature.getInstance(algorithm.name);
        signature.initSign(privateKey);
        signature.update(content);

        final byte[] signed = signature.sign();
        return Base64.encode(signed, OCTETS_CHARSET_UTF8);
    }

    // ===============================================================================================================

    /**
     * 校验
     * 
     * @param rsaPublicKey
     *            Base64编码公钥
     * @param content
     *            待校验内容
     * @param sign
     *            Base64编码签名信息
     * @return
     *         true:校验成功，false:校验失败
     * @throws Exception
     */
    public static boolean verify(final String rsaPublicKey, final String content, final String sign) throws Exception
    {
        return verify(rsaPublicKey, content, sign, RSASignatureAlgorithm.SHA1WithRSA);
    }

    /**
     * 校验
     * 
     * @param rsaPublicKey
     *            Base64编码公钥
     * @param content
     *            待校验内容
     * @param sign
     *            Base64编码签名信息
     * @param algorithm
     *            签名算法
     * @return
     *         true:校验成功，false:校验失败
     * @throws Exception
     */
    public static boolean verify(final String rsaPublicKey, final String content, final String sign,
            final RSASignatureAlgorithm algorithm) throws Exception
    {
        final byte[] bytes = content.getBytes(OCTETS_CHARSET_UTF8);
        return verify(rsaPublicKey, bytes, sign, algorithm);
    }

    /**
     * 校验
     * 
     * @param rsaPublicKey
     *            Base64编码公钥
     * @param content
     *            待校验内容
     * @param sign
     *            Base64编码签名信息
     * @return
     *         true:校验成功，false:校验失败
     * @throws Exception
     */
    public static boolean verify(final String rsaPublicKey, final byte[] content, final String sign) throws Exception
    {
        return verify(rsaPublicKey, content, sign, RSASignatureAlgorithm.SHA1WithRSA);
    }

    /**
     * 校验
     * 
     * @param rsaPublicKey
     *            Base64编码公钥
     * @param content
     *            待校验内容
     * @param sign
     *            Base64编码签名信息
     * @param algorithm
     *            签名算法
     * @return
     *         true:校验成功，false:校验失败
     * @throws Exception
     */
    public static boolean verify(final String rsaPublicKey, final byte[] content, final String sign,
            final RSASignatureAlgorithm algorithm) throws Exception
    {
        final PublicKey pubKey = getPublicKey(rsaPublicKey);
        final java.security.Signature signature = java.security.Signature.getInstance(algorithm.name);
        signature.initVerify(pubKey);
        signature.update(content);

        return signature.verify(Base64.decode(sign));
    }

    /**
     * 校验
     * 
     * @param rsaPublicKey
     *            公钥
     * @param content
     *            待校验内容
     * @param sign
     *            Base64编码签名信息
     * @return
     *         true:校验成功，false:校验失败
     * @throws Exception
     */
    public static boolean verify(final PublicKey rsaPublicKey, final String content, final String sign) throws Exception
    {
        return verify(rsaPublicKey, content, sign, RSASignatureAlgorithm.SHA1WithRSA);
    }

    /**
     * 校验
     * 
     * @param rsaPublicKey
     *            公钥
     * @param content
     *            待校验内容
     * @param sign
     *            Base64编码签名信息
     * @param algorithm
     *            签名算法
     * @return
     *         true:校验成功，false:校验失败
     * @throws Exception
     */
    public static boolean verify(final PublicKey rsaPublicKey, final String content, final String sign,
            final RSASignatureAlgorithm algorithm) throws Exception
    {
        final byte[] bytes = content.getBytes(OCTETS_CHARSET_UTF8);
        return verify(rsaPublicKey, bytes, sign, algorithm);
    }

    /**
     * 校验
     * 
     * @param rsaPublicKey
     *            公钥
     * @param content
     *            待校验内容
     * @param sign
     *            Base64编码签名信息
     * @return
     *         true:校验成功，false:校验失败
     * @throws Exception
     */
    public static boolean verify(final PublicKey rsaPublicKey, final byte[] content, final String sign) throws Exception
    {
        return verify(rsaPublicKey, content, sign, RSASignatureAlgorithm.SHA1WithRSA);
    }

    /**
     * 校验
     * 
     * @param rsaPublicKey
     *            公钥
     * @param content
     *            待校验内容
     * @param sign
     *            Base64编码签名信息
     * @param algorithm
     *            签名算法
     * @return
     *         true:校验成功，false:校验失败
     * @throws Exception
     */
    public static boolean verify(final PublicKey rsaPublicKey, final byte[] content, final String sign,
            final RSASignatureAlgorithm algorithm) throws Exception
    {
        final java.security.Signature signature = java.security.Signature.getInstance(algorithm.name);
        signature.initVerify(rsaPublicKey);
        signature.update(content);

        return signature.verify(Base64.decode(sign));
    }

    // =============================================================================================================

    /**
     * RSA解密
     * 
     * @param rsaPrivateKey
     *            Base64编码私钥
     * @param cipherContent
     *            待解密内容
     * @return
     *         解密后的内容
     * @throws Exception
     */
    public static String decryptToStr(final String rsaPrivateKey, String cipherContent) throws Exception
    {
        final byte[] cipherData = cipherContent.getBytes(OCTETS_CHARSET_UTF8);
        final byte[] output = decrypt(rsaPrivateKey, cipherData);
        if (output == null)
        {
            return null;
        }

        return new String(output, OCTETS_CHARSET_UTF8);
    }

    /**
     * RSA解密
     * 
     * @param rsaPrivateKey
     *            Base64编码私钥
     * @param cipherData
     *            待解密内容
     * @return
     *         解密后的内容
     * @throws Exception
     */
    public static String decryptToStr(final String rsaPrivateKey, final byte[] cipherData) throws Exception
    {
        final byte[] output = decrypt(rsaPrivateKey, cipherData);
        if (output == null)
        {
            return null;
        }

        return new String(output, OCTETS_CHARSET_UTF8);
    }

    /**
     * RSA解密
     * 
     * @param rsaPrivateKey
     *            Base64编码私钥
     * @param cipherData
     *            待解密内容
     * @return
     *         解密后的内容
     */
    public static byte[] decrypt(final String rsaPrivateKey, final byte[] cipherData) throws Exception
    {
        final PrivateKey priKey = getPrivateKey(rsaPrivateKey);
        return decrypt(priKey, cipherData);
    }

    /**
     * RSA解密
     * 
     * @param rsaPrivateKey
     *            私钥
     * @param cipherContent
     *            Base64编码待解密内容
     * @return
     *         解密后的内容
     * @throws Exception
     */
    public static String decryptToStr(final PrivateKey rsaPrivateKey, final String cipherContent) throws Exception
    {
        final byte[] cipherData = Base64.decode(cipherContent);
        return decryptToStr(rsaPrivateKey, cipherData);
    }

    /**
     * RSA解密
     * 
     * @param rsaPrivateKey
     *            私钥
     * @param cipherData
     *            待解密内容
     * @return
     *         解密后的内容
     * @throws Exception
     */
    public static String decryptToStr(final PrivateKey rsaPrivateKey, final byte[] cipherData) throws Exception
    {
        final byte[] output = decrypt(rsaPrivateKey, cipherData);
        if (output == null)
        {
            return null;
        }

        return new String(output, OCTETS_CHARSET_UTF8);
    }

    /**
     * RSA解密
     * 
     * @param rsaPrivateKey
     *            私钥
     * @param cipherData
     *            待解密内容
     * @return
     *         解密后的内容
     */
    public static byte[] decrypt(final PrivateKey rsaPrivateKey, final byte[] cipherData) throws Exception
    {
        final Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        final int blockSize = getDecryptBlockSize(rsaPrivateKey);
        final int len = cipherData.length;
        int offset = 0;
        do
        {
            baos.write(cipher.doFinal(cipherData, offset, Math.min(blockSize, len - offset)));
            offset += blockSize;
        }
        while (offset < len);

        final byte[] bytes = baos.toByteArray();
        baos.close();

        return bytes;
    }

    /**
     * RSA解密
     * 
     * @param rsaPublicKey
     *            公钥
     * @param cipherContent
     *            Base64编码待解密内容
     * @return
     *         解密后的内容
     * @throws Exception
     */
    public static String decryptToStr(final PublicKey rsaPublicKey, final String cipherContent) throws Exception
    {
        final byte[] cipherData = Base64.decode(cipherContent);
        return decryptToStr(rsaPublicKey, cipherData);
    }

    /**
     * RSA解密
     * 
     * @param rsaPublicKey
     *            公钥
     * @param cipherData
     *            待解密内容
     * @return
     *         解密后的内容
     * @throws Exception
     */
    public static String decryptToStr(final PublicKey rsaPublicKey, final byte[] cipherData) throws Exception
    {
        final byte[] output = decrypt(rsaPublicKey, cipherData);
        if (output == null)
        {
            return null;
        }

        return new String(output, OCTETS_CHARSET_UTF8);
    }

    /**
     * RSA解密
     * 
     * @param rsaPublicKey
     *            公钥
     * @param cipherData
     *            待解密内容
     * @return
     *         解密后的内容
     */
    public static byte[] decrypt(final PublicKey rsaPublicKey, final byte[] cipherData) throws Exception
    {
        final Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        final int blockSize = getDecryptBlockSize(rsaPublicKey);
        final int len = cipherData.length;
        int offset = 0;
        do
        {
            baos.write(cipher.doFinal(cipherData, offset, Math.min(blockSize, len - offset)));
            offset += blockSize;
        }
        while (offset < len);

        final byte[] bytes = baos.toByteArray();
        baos.close();
        return bytes;
    }

    /**
     * RSA加密
     * 
     * @param rsaPublicKey
     *            Base64编码公钥
     * @param cipherContent
     *            待加密内容
     * @return
     *         加密后的内容(Base64编码)
     * @throws Exception
     */
    public static String encryptToStr(final String rsaPublicKey, final String cipherContent) throws Exception
    {
        final byte[] cipherData = cipherContent.getBytes(OCTETS_CHARSET_UTF8);
        return encryptToStr(rsaPublicKey, cipherData);
    }

    /**
     * RSA加密
     * 
     * @param rsaPublicKey
     *            Base64编码公钥
     * @param cipherData
     *            待加密内容
     * @return
     *         加密后的内容(Base64编码)
     * @throws Exception
     */
    public static String encryptToStr(final String rsaPublicKey, final byte[] cipherData) throws Exception
    {
        final byte[] output = encrypt(rsaPublicKey, cipherData);
        if (output == null)
        {
            return null;
        }

        return Base64.encode(output, OCTETS_CHARSET_UTF8);
    }

    /**
     * RSA加密
     * 
     * @param rsaPublicKey
     *            Base64编码公钥
     * @param cipherData
     *            待加密内容
     * @return
     */
    public static byte[] encrypt(final String rsaPublicKey, final byte[] cipherData) throws Exception
    {
        final PublicKey pubKey = getPublicKey(rsaPublicKey);
        return encrypt(pubKey, cipherData);
    }

    /**
     * RSA加密
     * 
     * @param rsaPublicKey
     *            公钥
     * @param cipherContent
     *            待加密内容
     * @return
     *         加密后的内容(Base64编码)
     * @throws Exception
     */
    public static String encryptToStr(final PublicKey rsaPublicKey, final String cipherContent) throws Exception
    {
        final byte[] cipherData = cipherContent.getBytes(OCTETS_CHARSET_UTF8);
        return encryptToStr(rsaPublicKey, cipherData);
    }

    /**
     * RSA加密
     * 
     * @param rsaPublicKey
     *            公钥
     * @param cipherData
     *            待加密内容
     * @return
     *         加密后的内容(Base64编码)
     * @throws Exception
     */
    public static String encryptToStr(final PublicKey rsaPublicKey, final byte[] cipherData) throws Exception
    {
        final byte[] output = encrypt(rsaPublicKey, cipherData);
        if (output == null)
        {
            return null;
        }

        return Base64.encode(output, OCTETS_CHARSET_UTF8);
    }

    /**
     * RSA加密
     * 
     * @param rsaPublicKey
     *            公钥
     * @param cipherData
     *            待加密内容
     * @return
     */
    public static byte[] encrypt(final PublicKey rsaPublicKey, final byte[] cipherData) throws Exception
    {
        final Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        final int blockSize = getEncryptBlockSize(rsaPublicKey);
        final int len = cipherData.length;
        int offset = 0;
        do
        {
            baos.write(cipher.doFinal(cipherData, offset, Math.min(blockSize, len - offset)));
            offset += blockSize;
        }
        while (offset < len);

        final byte[] bytes = baos.toByteArray();
        baos.close();

        return bytes;
    }

    /**
     * RSA加密
     * 
     * @param rsaPrivateKey
     *            私钥
     * @param cipherContent
     *            待加密内容
     * @return
     *         加密后的内容(Base64编码)
     * @throws Exception
     */
    public static String encryptToStr(final PrivateKey rsaPrivateKey, final String cipherContent) throws Exception
    {
        final byte[] cipherData = cipherContent.getBytes(OCTETS_CHARSET_UTF8);
        return encryptToStr(rsaPrivateKey, cipherData);
    }

    /**
     * RSA加密
     * 
     * @param rsaPrivateKey
     *            私钥
     * @param cipherData
     *            待加密内容
     * @return
     *         加密后的内容(Base64编码)
     * @throws Exception
     */
    public static String encryptToStr(final PrivateKey rsaPrivateKey, final byte[] cipherData) throws Exception
    {
        final byte[] output = encrypt(rsaPrivateKey, cipherData);
        if (output == null)
        {
            return null;
        }

        return Base64.encode(output, OCTETS_CHARSET_UTF8);
    }

    /**
     * RSA加密
     * 
     * @param rsaPrivateKey
     *            私钥
     * @param cipherData
     *            待加密内容
     * @return
     */
    public static byte[] encrypt(final PrivateKey rsaPrivateKey, final byte[] cipherData) throws Exception
    {
        final Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        final int blockSize = getEncryptBlockSize(rsaPrivateKey);
        final int len = cipherData.length;
        int offset = 0;
        do
        {
            baos.write(cipher.doFinal(cipherData, offset, Math.min(blockSize, len - offset)));
            offset += blockSize;
        }
        while (offset < len);

        final byte[] bytes = baos.toByteArray();
        baos.close();

        return bytes;
    }

    // ===============================================================================================================

    public static void main(String args[]) throws Exception
    {
        final KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        final SecureRandom random = new SecureRandom();
        keyPairGen.initialize(1024, random);
        final KeyPair keyPair = keyPairGen.generateKeyPair();
        final PrivateKey priKey = keyPair.getPrivate();
        final PublicKey pubKey = keyPair.getPublic();
        System.out.printf("prikey:%s\n", Base64.encode(priKey.getEncoded(), OCTETS_CHARSET_UTF8));
        System.out.printf("pubkey:%s\n", Base64.encode(pubKey.getEncoded(), OCTETS_CHARSET_UTF8));
    }
}

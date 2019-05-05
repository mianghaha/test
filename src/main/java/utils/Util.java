package utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * 工具类。
 */
public class Util
{
    public static final String OCTETS_CHARSET_UTF8 = "UTF-8";

    /**
     * 获取MD5消息摘要
     * 
     * @param content
     * 
     * @return
     */
    public static byte[] md5(final String content) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(content.getBytes(OCTETS_CHARSET_UTF8));
        return messageDigest.digest();
    }

    /**
     * 获取MD5消息摘要
     * 
     * @param content
     * 
     * @return
     */
    public static String md5sum(final String content) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(content.getBytes(OCTETS_CHARSET_UTF8));
        final byte[] bytes = messageDigest.digest();
        return bytesToHexString(bytes);
    }

    /**
     * 获取SHA1消息摘要
     * 
     * @param content
     * 
     * @return
     */
    public static byte[] sha1(final String content) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.reset();
        messageDigest.update(content.getBytes(OCTETS_CHARSET_UTF8));
        return messageDigest.digest();
    }

    /**
     * 获取SHA1消息摘要
     * 
     * @param content
     * 
     * @return
     */
    public static String sha1sum(final String content) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.reset();
        messageDigest.update(content.getBytes(OCTETS_CHARSET_UTF8));
        final byte[] bytes = messageDigest.digest();
        return bytesToHexString(bytes);
    }

    private static char[] HEX_CHAR_TABLE = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };

    /**
     * 字节数组转成十六进制字符串
     * 
     * @param bytes
     *            字节数组
     * @return
     * 
     */
    public static String bytesToHexString(final byte[] bytes)
    {
        return bytesToHexString(bytes, 0, bytes.length);
    }

    /**
     * 字节数组转成十六进制字符串
     * 
     * @param bytes
     *            字节数组
     * @param begin
     *            包含
     * @param end
     *            不包含
     * @return
     * 
     */
    public static String bytesToHexString(final byte[] bytes, final int begin, final int end)
    {
        if (bytes == null)
        {
            return null;
        }

        final int len = bytes.length;
        if (begin < 0 || end > len || begin >= end)
        {
            return null;
        }

        final StringBuilder sb = new StringBuilder();
        for (int i = begin; i < end; ++i)
        {
            final byte b = bytes[i];
            sb.append(HEX_CHAR_TABLE[(b >>> 4) & 0x0F]);
            sb.append(HEX_CHAR_TABLE[b & 0x0F]);
        }
        return sb.toString();
    }

    /**
     * 将十六进制字符转成字节
     * 
     * @param c
     * @return
     */
    private static int hexCharToByte(char c)
    {
        if (c >= '0' && c <= '9')
        {
            return (c - '0');
        }
        c = Character.toLowerCase(c);
        if (c >= 'a' && c <= 'f')
        {
            return (c - 'a' + 10);
        }
        throw new RuntimeException("hexCharToByte bad format char " + c);
    }

    /**
     * 将十六进制字符串转成字节数组
     * 
     * @param str
     * @return
     */
    public static byte[] hexStringToBytes(final String str)
    {
        final int length = str.length() / 2;
        final byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++)
        {
            final char c0 = str.charAt(i * 2);
            final char c1 = str.charAt(i * 2 + 1);

            final int b0 = hexCharToByte(c0);
            final int b1 = hexCharToByte(c1);
            bytes[i] = (byte) ((b0 << 4 | b1) & 0x000000FF);
        }

        return bytes;
    }

    /**
     * 将HTTP Query字符串参数列表转成字符串MAP
     * 
     * @param args
     *            Query字符串参数列表
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> argsToStringMap(final String args) throws UnsupportedEncodingException
    {
        return argsToStringMap(args, true);
    }

    /**
     * 将HTTP Query字符串参数列表转成字符串MAP
     * 
     * @param args
     *            Query字符串参数列表
     * @param requireURLDecode
     *            是否需要URLDecode
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> argsToStringMap(final String args, final boolean requireURLDecode)
            throws UnsupportedEncodingException
    {
        return argsToStringMap(args, requireURLDecode, false);
    }

    /**
     * 将HTTP Query字符串参数列表转成字符串MAP
     * 
     * @param args
     *            Query字符串参数列表
     * @param requireURLDecode
     *            是否需要URLDecode
     * @param sorted
     *            key是否排序
     * @return
     * 
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> argsToStringMap(final String args, final boolean requireURLDecode, final boolean sorted)
            throws UnsupportedEncodingException
    {
        return argsToStringMap(args, requireURLDecode, sorted, false);
    }

    /**
     * 将HTTP Query字符串参数列表转成字符串MAP
     * 
     * @param args
     *            Query字符串参数列表
     * @param requireURLDecode
     *            是否需要URLDecode
     * @param sorted
     *            key是否排序
     * @param serial
     *            key按照args中顺序存放
     * @return
     * 
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> argsToStringMap(final String args, final boolean requireURLDecode, final boolean sorted,
            final boolean serial) throws UnsupportedEncodingException
    {
        final Map<String, String> res = (sorted ? new TreeMap<String, String>() : (serial ? new LinkedHashMap<String, String>()
                : new HashMap<String, String>()));
        final String[] kvs = args.split("&");
        for (final String kv : kvs)
        {
            final int index = kv.indexOf('=');
            if (-1 == index)
            {
                continue;
            }

            final String key = kv.substring(0, index);
            String value = kv.substring(index + 1);
            if (requireURLDecode)
            {
                value = URLDecoder.decode(value, OCTETS_CHARSET_UTF8);
            }
            res.put(key, value);
        }
        return res;
    }

    /**
     * 将map转成xml字符串
     * 
     * @param map
     *            字符串MAP
     * @param rootName
     *            转成XML的根节点名称
     * @return
     */
    public static String stringMapToXml(final Map<String, String> map, final String rootName)
    {
        final StringBuilder sb = new StringBuilder();
        final String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        sb.append(header);

        if (rootName != null)
        {
            sb.append("<").append(rootName).append(">");
        }

        if (map != null)
        {
            for (String key : map.keySet())
            {
                String value = map.get(key);
                sb.append("<").append(key).append(">");
                sb.append(value);
                sb.append("</").append(key).append(">");
            }
        }

        if (rootName != null)
        {
            sb.append("</").append(rootName).append(">");
        }

        return sb.toString();
    }

    /**
     * 解析货币值（分，美分）等
     * 
     * @param currencyValue
     *            单位是RMB元或者美元，只会保留2位有效位
     * @return
     */
    public static int parseCurrency(final float currencyValue)
    {
        return parseCurrency(String.format("%.2f", currencyValue));
    }

    /**
     * 解析货币值（分，美分）等
     * 
     * @param currencyValue
     *            单位是RMB元或者美元，只会保留2位有效位
     * @return
     */
    public static int parseCurrency(final double currencyValue)
    {
        return parseCurrency(String.format("%.2f", currencyValue));
    }

    /**
     * 解析货币值（分，美分）等
     * 
     * @param currencyValue
     * @return
     */
    public static int parseCurrency(final String currencyValue)
    {
        final int len = currencyValue.length();
        if (len == 0)
        {
            return 0;
        }

        int i = 0;
        boolean negative = false;
        final char firstChar = currencyValue.charAt(0);
        if (firstChar == '-')
        {
            negative = true;
            ++i;
        }
        else if (firstChar == '+')
        {
            ++i;
        }

        final int lastCharIndex = len - 1;
        int currency = 0;
        int limit = -1;
        int end = len;
        for (; i < end; ++i)
        {
            final char ch = currencyValue.charAt(i);
            if (ch == '.')
            {
                if (i == lastCharIndex || limit != -1)
                {
                    throw new NumberFormatException("For input string: \"" + currencyValue + "\"");
                }

                limit = i + 3;
                end = Math.min(limit, len);

                continue;
            }

            if (ch >= '0' && ch <= '9')
            {
                currency = (currency * 10 + (ch - '0'));
            }
            else
            {
                throw new NumberFormatException("For input string: \"" + currencyValue + "\"");
            }
        }

        if (limit == -1)
        {
            currency *= 100;
        }
        else
        {
            while (i < limit)
            {
                currency *= 10;
                ++i;
            }
        }

        if (negative)
        {
            return -currency;
        }

        return currency;
    }

    public static int convert2IntIP(final String strIp) throws Exception
    {
        if (strIp.indexOf('.') == -1)
        {
            throw new IllegalArgumentException("strIp illegal");
        }

        final String[] comps = strIp.split("\\.");
        if (comps.length != 4)
        {
            throw new IllegalArgumentException("strIp illegal");
        }

        try
        {
            return (((Integer.parseInt(comps[0]) << 24) & 0xFF000000) | ((Integer.parseInt(comps[1]) << 16) & 0xFF0000)
                    | ((Integer.parseInt(comps[2]) << 8) & 0xFF00) | (Integer.parseInt(comps[3]) & 0xFF));
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public static byte[] convert2address(final String strIp) throws Exception
    {
        if (strIp.indexOf('.') == -1)
        {
            throw new IllegalArgumentException("strIp illegal");
        }

        final String[] comps = strIp.split("\\.");
        if (comps.length != 4)
        {
            throw new IllegalArgumentException("strIp illegal");
        }

        try
        {
            final byte[] address = new byte[4];
            address[0] = (byte) (Integer.parseInt(comps[0]) & 0xFF);
            address[1] = (byte) (Integer.parseInt(comps[1]) & 0xFF);
            address[2] = (byte) (Integer.parseInt(comps[2]) & 0xFF);
            address[3] = (byte) (Integer.parseInt(comps[3]) & 0xFF);
            return address;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public static String getHostName() throws Exception
    {
        final String computerName = System.getenv("COMPUTERNAME");
        if (computerName != null)
        {
            return computerName;
        }

        try
        {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (Exception e)
        {
            final String host = e.getMessage();
            if (host != null)
            {
                int colon = host.indexOf(':');
                if (colon > 0)
                {
                    return host.substring(0, colon);
                }
            }

            throw new RuntimeException("UnknownHost");
        }
    }

    public static String convert2StringIP(final int ip)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf((ip >>> 24) & 0xFF)).append(".");
        sb.append(String.valueOf((ip >>> 16) & 0xFF)).append(".");
        sb.append(String.valueOf((ip >>> 8) & 0xFF)).append(".");
        sb.append(String.valueOf(ip & 0xFF));
        return sb.toString();
    }

    private static char[] HEX_UPPPER_CHAR_TABLE = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
            'D', 'E', 'F' };
    private final static String[] ENCODE_INPUT_CHARS = new String[256];
    static
    {
        for (int i = 0; i < 256; ++i)
        {
            if ((i >= '0' && i <= '9') || (i >= 'a' && i <= 'z') || (i >= 'A' && i <= 'Z') || i == '-' || i == '_' || i == '.'
                    || i == '~')
            {
                ENCODE_INPUT_CHARS[i] = String.format("%c", (char) i);
            }
            else
            {
                final StringBuilder sb = new StringBuilder();
                sb.append('%');
                sb.append(HEX_UPPPER_CHAR_TABLE[(i >>> 4) & 0x0F]);
                sb.append(HEX_UPPPER_CHAR_TABLE[i & 0x0F]);
                ENCODE_INPUT_CHARS[i] = sb.toString();
            }
        }
    }

    public static String urlEncode1738(final String content, final String charset) throws UnsupportedEncodingException
    {
        final StringBuilder sb = new StringBuilder();
        final byte[] bytes = content.getBytes(charset);
        final int len = bytes.length;
        for (int i = 0; i < len; ++i)
        {
            final int b = (bytes[i] & 0xFF);
            sb.append(b == '~' ? "%7E" : ENCODE_INPUT_CHARS[b]);
        }
        return sb.toString();
    }

    public static String urlDecode1738(final String content, final String charset) throws UnsupportedEncodingException
    {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final int len = content.length();
        for (int i = 0; i < len; ++i)
        {
            char chr = content.charAt(i);
            if (chr == '%')
            {
                final int b0 = hexCharToByte(content.charAt(++i));
                final int b1 = hexCharToByte(content.charAt(++i));
                baos.write((byte) ((b0 << 4 | b1) & 0xFF));
            }
            else
            {
                baos.write((byte) chr);
            }
        }
        return new String(baos.toByteArray(), charset);
    }

    public static String urlEncode3896(final String content, final String charset) throws UnsupportedEncodingException
    {
        final StringBuilder sb = new StringBuilder();
        final byte[] bytes = content.getBytes(charset);
        final int len = bytes.length;
        for (int i = 0; i < len; ++i)
        {
            final int b = (bytes[i] & 0xFF);
            sb.append(ENCODE_INPUT_CHARS[b]);
        }
        return sb.toString();
    }

    public static String urlDecode3896(final String content, final String charset) throws UnsupportedEncodingException
    {
        return urlDecode1738(content, charset);
    }

    public static String urlEncode(final String content, final String charset) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(content, charset);
    }

    public static String urlDecode(final String content, final String charset) throws UnsupportedEncodingException
    {
        return URLDecoder.decode(content, charset);
    }

    public static String paramEncodeForTencent(final String content, final String charset) throws UnsupportedEncodingException
    {
        final StringBuilder sb = new StringBuilder();
        final byte[] bytes = content.getBytes(charset);
        final int len = bytes.length;
        for (int i = 0; i < len; ++i)
        {
            final int b = (bytes[i] & 0xFF);
            if ((b >= '0' && b <= '9') || (b >= 'a' && b <= 'z') || (b >= 'A' && b <= 'Z') || b == '!' || b == '(' || b == ')'
                    || b == '*')
            {
                sb.append((char) b);
            }
            else
            {
                sb.append('%');
                sb.append(HEX_UPPPER_CHAR_TABLE[(b >>> 4) & 0x0F]);
                sb.append(HEX_UPPPER_CHAR_TABLE[b & 0x0F]);
            }
        }
        return sb.toString();
    }

    public static String paramDecodeForTencent(final String content, final String charset) throws UnsupportedEncodingException
    {
        return urlDecode1738(content, charset);
    }

    private final static char[] BASIC_CHARS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    public static String intToX26(int value)
    {
        final int radix = 26;
        final char buf[] = new char[33];
        final boolean negative = (value < 0);

        int charPos = 32;
        if (!negative)
        {
            value = -value;
        }
        while (value <= -radix)
        {
            buf[charPos--] = BASIC_CHARS[-(value % radix)];
            value = value / radix;
        }
        buf[charPos] = BASIC_CHARS[-value];
        if (negative)
        {
            buf[--charPos] = '-';
        }
        return new String(buf, charPos, (33 - charPos));
    }
}

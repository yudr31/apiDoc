package com.spring.boot.apidoc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * Java版3DES加密解密，适用于PHP版3DES加密解密(PHP语言开发的MCRYPT_3DES算法、MCRYPT_MODE_ECB模式、PKCS7填充方式)
 * @author yuderen
 * @version 2018/3/6 16:39
 */
public class DESUtil {

    private static Logger logger = LoggerFactory.getLogger(DESUtil.class);

    private static final String Algorithm = "DESede";

    /**
     * 加密
     *
     * @param message
     * @return
     */
    public static String encryptDes(String message, String keyString, String charset) {
        String result = "";
        try {
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(build3DesKey(keyString, charset), Algorithm));
            byte[] resultBytes = cipher.doFinal(message.getBytes(charset));
            result = new String(Base64Util.encode(resultBytes));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 解密
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String decryptDes(String message, String keyString, String charset) {
        String result = "";
        try {
//            byte[] messageBytes = Base64Util.decodeBase64(message.getBytes(charset));
            byte[] messageBytes = Base64Util.decode2ByteArray(message);
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(build3DesKey(keyString, charset), Algorithm));
            byte[] resultBytes = cipher.doFinal(messageBytes);
            result = new String(resultBytes, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 格式Key
     *
     * @param keyStr
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] build3DesKey(String keyStr, String charset) throws UnsupportedEncodingException {
        // 声明一个24位的字节数组，默认里面都是字符0
        byte[] key = new byte[24];
        for (int i = 0; i < key.length; i++) {
            key[i] = 48;
        }
        byte[] temp = keyStr.getBytes(charset);
        if (key.length > temp.length) {
            // 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            // 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }

}

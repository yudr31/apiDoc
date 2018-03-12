package com.spring.boot.apidoc.util;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.Key;

/**
 * @author yuderen
 * @version 2018/3/6 16:03
 */
public class RSAUtil {

    // 公钥
    private Key publicKey;
    // 私钥
    private Key privateKey;

    // 指定加密算法为DESede
    private String ALGORITHM = "RSA";
    // 指定公钥存放文件
    private String PUBLIC_KEY_FILE = "";
    // 指定私钥存放文件
    private String PRIVATE_KEY_FILE = "";

    public void setPUBLIC_KEY_FILE(String PUBLIC_KEY_FILE) {
        this.PUBLIC_KEY_FILE = PUBLIC_KEY_FILE;
    }

    public void setPRIVATE_KEY_FILE(String PRIVATE_KEY_FILE) {
        this.PRIVATE_KEY_FILE = PRIVATE_KEY_FILE;
    }

    /**
     * 初始化公私钥
     */
    public void init() {
        ObjectInputStream ois1 = null;
        ObjectInputStream ois2 = null;
        try {
            ois1 = new ObjectInputStream(this.getClass().getResourceAsStream(PUBLIC_KEY_FILE));
            ois2 = new ObjectInputStream(this.getClass().getResourceAsStream(PRIVATE_KEY_FILE));

            publicKey = (Key) ois1.readObject();
            privateKey = (Key) ois2.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != ois1){
                try {
                    ois1.close();
                } catch (IOException e) {
                }
            }
            if (null != ois2){
                try {
                    ois2.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * RSA加密
     * @param source        源数据
     * @return              加密后的数据
     */
    public String encrypt(String source) {
        try {
            // 得到Cipher对象来实现对源数据的RSA加密
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] bytes = cipher.doFinal(source.getBytes());
            return Base64Util.encode(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * RSA解密
     * @param cryptograph   密文
     * @return              源文
     */
    public String decrypt(String cryptograph) {
        try {
            // 得到Cipher对象对已用公钥加密的数据进行RSA解密
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] bytes = cipher.doFinal(Base64Util.decode2ByteArray(cryptograph));
            return new String(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

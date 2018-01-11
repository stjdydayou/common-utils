package com.a7space.commons.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SecureRandom;

public class CryptoBase64Util {
    Key key;

    public CryptoBase64Util() {
        // TODO Auto-generated constructor stub
        setKey(Constant.CRYPTOBASE64UTIL_DEFAULT_KEY);
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    /**
     *  Created on 2017年2月23日 
     * <p>Discription:[设置密钥]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param strKey
     */
    public void setKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes("UTF-8"));
            _generator.init(56, secureRandom);
            this.key = _generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
    }

    /**
     *  Created on 2017年2月23日 
     * <p>Discription:[将明文加密成密文]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param strMing
     * @return
     */
    public String encryptStr(String strMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        BASE64Encoder base64en = new BASE64Encoder();
        try {
            byteMing = strMing.getBytes("UTF-8");
            byteMi = this.encryptByte(byteMing);
            strMi = base64en.encode(byteMi);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            base64en = null;
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }

    /**
     *  Created on 2017年2月23日 
     * <p>Discription:[将密文解密成明文]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param strMi
     * @return
     */
    public String decryptStr(String strMi) {
        BASE64Decoder base64De = new BASE64Decoder();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = base64De.decodeBuffer(strMi);
            byteMing = this.decryptByte(byteMi);
            strMing = new String(byteMing, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            base64De = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }
    /**
     *  Created on 2017年2月23日 
     * <p>Discription:[将明文加密成密文]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param byteS
     * @return
     */
    private byte[] encryptByte(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }
    /**
     *  Created on 2017年2月23日 
     * <p>Discription:[将密文解密成明文]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param byteD
     * @return
     */
    private byte[] decryptByte(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     *  Created on 2017年2月23日 
     * <p>Discription:[将明文加密成密文]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param str
     * @return
     */
    public static String encryption(String str) {
        CryptoBase64Util des = new CryptoBase64Util();
        try {
            return (new BASE64Encoder().encode(des.encryptStr(str).getBytes("UTF-8"))).replaceAll("\n", "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Created on 2017年2月23日 
     * <p>Discription:[将密文解密成明文]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param str
     * @return
     */
    public static String decryption(String str) {
        CryptoBase64Util des = new CryptoBase64Util();
        String s = "";
        try {
            s = des.decryptStr(new String(new BASE64Decoder().decodeBuffer(str), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}

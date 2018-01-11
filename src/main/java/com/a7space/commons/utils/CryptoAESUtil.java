package com.a7space.commons.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
AES(Advanced Encryption Standard)：高级加密标准，是DES\3DES下一代的加密算法标准，速度快，安全级别高。
用AES加密2000年10月，NIST（美国国家标准和技术协会）宣布通过从15种候选算法中选出的一项新的密匙加密标准。
Rijndael被选中成为将来的 AES。Rijndael是在1999年下半年，由研究员Joan Daemen 和 Vincent Rijmen 创建的。
AES正日益成为加密各种形式的电子数据的实际标准。美国标准与技术研究院（NIST）于2002年5月26日制定了新的高级加密标准（AES）规范。
*/
public class CryptoAESUtil {

    private static Logger logger = LoggerFactory.getLogger(CryptoAESUtil.class);
    
    private static final String IV_STRING = "16-Bytes--String";

    private static final String defaultKey="10000_pagu_00001";
    
    public static String encryptAES(String content)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, UnsupportedEncodingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException{
        return encryptAES(content, defaultKey);
    }
    public static String encryptAES(String content, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, UnsupportedEncodingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {

        byte[] byteContent = content.getBytes("UTF-8");

        // 注意，为了能与 iOS 统一
        // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
        byte[] enCodeFormat = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");

        byte[] initParam = IV_STRING.getBytes();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

        // 指定加密的算法、工作模式和填充方式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] encryptedBytes = cipher.doFinal(byteContent);
        
        // 同样对加密后数据进行 base64 编码
        //Encoder encoder = Base64.getEncoder();
        //return encoder.encodeToString(encryptedBytes);
        //jdk1.6
        return DatatypeConverter.printBase64Binary(encryptedBytes);
    }
    
    public static String decryptAES(String content)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException {
        return decryptAES(content, defaultKey);
    }
    public static String decryptAES(String content, String key)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException {

        // base64 解码
        //Decoder decoder = Base64.getDecoder();
        //byte[] encryptedBytes = decoder.decode(content);
        //jdk1.6
        byte[] encryptedBytes =DatatypeConverter.parseBase64Binary(content);

        byte[] enCodeFormat = key.getBytes();
        SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, "AES");

        byte[] initParam = IV_STRING.getBytes();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

        byte[] result = cipher.doFinal(encryptedBytes);

        return new String(result, "UTF-8");
    }
    
    /**
     *  Created on 2016年11月3日 
     * <p>Discription:[先用AES加密，然后再urlencode编码]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param content
     * @return
     */
    public static String encryptAESEncodeURL(String content){
        return encryptAESEncodeURL(content,defaultKey);
    }
    public static String encryptAESEncodeURL(String content, String key){
        try{
            return URLEncoder.encode(encryptAES(content,key),"UTF-8");
        }catch (Exception e) {
            // TODO: handle exception
            logger.error("error:{}",e);
            return "";
        }
    }

    /**
     *  Created on 2016年11月3日 
     * <p>Discription:[先urldecode解码，再AES解密]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param content
     * @return
     */
    public static String decryptAESDecodeURL(String content){
        return decryptAESDecodeURL(content,defaultKey);
    }
    public static String decryptAESDecodeURL(String content, String key){
        try{
            String urlDecodeStr=URLDecoder.decode(content,"UTF-8");
            return decryptAES(urlDecodeStr,key);
        }catch (Exception e) {
            // TODO: handle exception
            logger.error("error:{}",e);
            return "";
        }
        
    }
}

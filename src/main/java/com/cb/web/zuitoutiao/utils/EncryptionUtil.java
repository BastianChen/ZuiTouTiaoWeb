package com.cb.web.zuitoutiao.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * @Description: 提供多种加密方法
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-08-30 10:58
 **/
public class EncryptionUtil {
    /**
     * Base64 encode
     * */
    public static String base64Encode(String data){
        return Base64.encodeBase64String(data.getBytes());
    }

    /**
     * Base64 decode
     * @throws UnsupportedEncodingException
     * */
    public static String base64Decode(String data) throws UnsupportedEncodingException{
        return new String(Base64.decodeBase64(data.getBytes()),"utf-8");
    }

    /**
     * md5
     * */
    public static String md5Hex(String data){
        return DigestUtils.md5Hex(data);
    }

    /**
     * sha1
     * */
    public static String shaHex(String data){
        return DigestUtils.shaHex(data);
    }

    /**
     * sha256
     * */
    public static String sha256Hex(String data){
        return DigestUtils.sha256Hex(data);
    }

}

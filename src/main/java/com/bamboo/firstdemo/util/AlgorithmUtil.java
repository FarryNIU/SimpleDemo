package com.bamboo.firstdemo.util;

/**
 * @author FarryNiu 2025/11/30
 */

import java.security.MessageDigest;

/**
 * 加密算法工具类
 */
public class AlgorithmUtil {
    public static String getSHACal(String src){
        byte[] srcBytes = src.getBytes();
        try{
            MessageDigest sha = MessageDigest.getInstance("SHA");
            sha.update(srcBytes);
            return new String(sha.digest());
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}

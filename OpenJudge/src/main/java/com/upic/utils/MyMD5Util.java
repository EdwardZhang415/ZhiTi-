package com.upic.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by song on 2018/5/21.
 */
public class MyMD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d5e6f";

    public static String getDbPass(String src){
        return md5(src+salt);
    }

    public static boolean isRight(String input,String db){
        return db.equals(getDbPass(input));
    }

    public static void main(String[] args) {
        String dbPass = getDbPass("123456");//44561e98e575c2b6663cbd781bf05c46
        System.out.println(dbPass);
    }


}

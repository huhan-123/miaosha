package cn.edu.wust.miaosha.common.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author: huhan
 * @Date 2020/6/23 10:06 上午
 * @Description
 * @Verion 1.0
 */
public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    public static final String salt = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        String str;
        System.out.println(str = inputPassToFormPass("15671601816"));
        System.out.println(formPassToDBPass(str, "2f4h5a6d"));
    }
}

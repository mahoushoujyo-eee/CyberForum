package org.example.cyberforum.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5
{
    public static String hash(String password)
    {
        // 获取MD5 MessageDigest实例
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }

        // 将密码转换为字节数组
        byte[] messageDigest = md.digest(password.getBytes());

        // 将字节数组转换为十六进制字符串
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        // 返回最终的哈希字符串
        return hexString.toString();
    }
}

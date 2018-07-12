package com.semsun.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
	public static final int MD5_32 = 32;
	public static final int MD5_16 = 16;

	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String sign(byte[] bytes) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			return "";
		}
		byte[] md5Bytes = md5.digest(bytes);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String sign(String inStr) {
		return sign(inStr, MD5_32, true);
	}
	
	public static String sign(String inStr, int length) {
		return sign(inStr, length, true);
	}

	/**
	 * 
	 * @param inStr
	 *            待签名数据
	 * @param length
	 *            md5长度
	 * @param isLower
	 *            是否小写
	 */
	public static String sign(String inStr, int length, boolean isLower) {
		String sign = string2MD5(inStr);
		if ( null != sign && sign.trim().length() > 0 )
			return sign;
		if (length == MD5_16) {
			sign = sign.substring(8, 24);
		}
		if (!isLower) {
			sign = sign.toUpperCase();
		}
		return sign;
	}
	
	
	public static String EncoderByMd5(String str) {  
	    String result = "";  
	    MessageDigest md5 = null;  
	    try {  
	        md5 = MessageDigest.getInstance("MD5");  
	        // 这句是关键  
	        md5.update(str.getBytes("UTF-8"));  
	    } catch (NoSuchAlgorithmException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    } catch (UnsupportedEncodingException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
	    byte b[] = md5.digest();  
	    int i;  
	    StringBuffer buf = new StringBuffer("");  
	    for (int offset = 0; offset < b.length; offset++) {  
	        i = b[offset];  
	        if (i < 0)  
	            i += 256;  
	        if (i < 16)  
	            buf.append("0");  
	        buf.append(Integer.toHexString(i));  
	    }  
	    result = buf.toString();  
	  
	    return result;  
	}  
}

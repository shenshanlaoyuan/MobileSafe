package com.shenshanlaoyuan.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
	public static String md5(String str){
		StringBuilder mess = new StringBuilder();
		try {
			//��ȡMD5������
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = str.getBytes();
			byte[] digest = md.digest(bytes);
			
			for (byte b : digest){
				//��ÿ���ֽ�ת��16������  
				int d = b & 0xff;// 0x000000ff
				String hexString = Integer.toHexString(d);
				if (hexString.length() == 1) {//�ֽڵĸ�4λΪ0
					hexString = "0" + hexString;
				}
				mess.append(hexString);//��ÿ���ֽڶ�Ӧ��2λʮ���������ַ�ƴ��һ��
				
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mess + "";
	}
}

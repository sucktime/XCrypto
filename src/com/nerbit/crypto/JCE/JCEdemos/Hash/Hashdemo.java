package com.nerbit.crypto.JCE.JCEdemos.Hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

public class Hashdemo {
	
	static String msgCharset = "UTF-8";
	
	public static void demo_digests() throws Exception {
		String msg = "我是一个粉刷家，abc.....。。。。。";
		byte[] msgbytes = msg.getBytes(msgCharset);
		
		//commons-codec SHA1：
		MessageDigest messageDigest =  DigestUtils.getSha1Digest();
		byte[] hash = messageDigest.digest(msgbytes);
		System.out.println("hash len:"+hash.length);
		for (int i = 0; i < hash.length; i++) {
			System.out.print(hash[i]+",");
		}
		System.out.println();
		
		
		//JCE degist
		//MD5 SHA=SHA-1 SHA-256
		MessageDigest messageDigest2 = MessageDigest.getInstance("SHA");
		byte[] hash2 = messageDigest2.digest(msgbytes);
		System.out.println("hash2 len:"+hash.length);
		for (int i = 0; i < hash2.length; i++) {
			System.out.print(hash2[i]+",");
		}
		System.out.println();
	}
}

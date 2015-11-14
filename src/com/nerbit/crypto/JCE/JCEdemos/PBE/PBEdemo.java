package com.nerbit.crypto.JCE.JCEdemos.PBE;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class PBEdemo {
	
	public static void demo_pbe() throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		 /** 
	     * 支持以下任意一种算法 
	     * <pre> 
	     * PBEWithMD5AndDES  
	     * PBEWithMD5AndTripleDES  
	     * PBEWithSHA1AndDESede 
	     * PBEWithSHA1AndRC2_40 
	     * </pre> 
	     * */
		String pbeAgl = "PBEWithMD5AndDES"; //AES=AES_128 (also 192,256 key len)
		int pbeIterationCount = 10;
		int DESKeyLength = 8;
		String keyCharset = "UTF-8";
		
		String passwordstr = Base64.encodeBase64String("djka忽悠j+=8".getBytes(keyCharset));
		char[] password = passwordstr.toCharArray();
		byte[] salt = new byte[DESKeyLength];//required
		//PBEKeySpec keySpec = new PBEKeySpec(password, salt, pbeIterationCount);//
		PBEKeySpec keySpec = new PBEKeySpec(password);
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(pbeAgl);
		SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
		System.out.println("secret key format:"+secretKey.getFormat());
		System.out.println("secret key len:"+secretKey.getEncoded().length);
		
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, pbeIterationCount);//用来验证Keyspec中指定的参数,加密时可有可无，单解密时必须提供
		Cipher cipher1 = Cipher.getInstance(pbeAgl);
		cipher1.init(Cipher.ENCRYPT_MODE, secretKey,parameterSpec);
		
		String msg = "akjdka78";
		byte[] msgbytes = msg.getBytes(keyCharset);
		System.out.println("msg len:"+msgbytes.length);
		byte[] cpr = cipher1.doFinal(msgbytes);
		System.out.println("cipher len:"+cpr.length);
		
		Cipher cipher2 = Cipher.getInstance(pbeAgl);
		cipher2.init(Cipher.DECRYPT_MODE, secretKey,parameterSpec);
		byte[] plain = cipher2.doFinal(cpr);
		System.out.println("plain len:"+plain.length);
		String plainmsg = new String(plain,keyCharset);
		System.out.println("msg:"+plainmsg);
	}
	}
	

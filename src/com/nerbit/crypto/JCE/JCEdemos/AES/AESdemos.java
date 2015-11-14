package com.nerbit.crypto.JCE.JCEdemos.AES;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESdemos {
	
	public static void demo_encrypt_decrypt() {
		
		String msgCharset = "UTF-8";
		
		String generateKeyAlg = "AES";
		
		String secretKeyFactoryAlg = "AES";
		
		String encryptionAlg = "AES/CTR/PKCS5Padding";
		
		try {
			//密钥产生：
			KeyGenerator keyGenerator = KeyGenerator.getInstance(generateKeyAlg);
			System.out.println("keygenerator provider:"+keyGenerator.getProvider().getName());
			
			SecretKey secretKey = keyGenerator.generateKey();
			System.out.println("secret key format:"+secretKey.getFormat());
			System.out.println("secret key len:"+secretKey.getEncoded().length);
			
			
			//加密：
			Cipher cipher = Cipher.getInstance(encryptionAlg);
			System.out.println("encryttionAlg provider:"+cipher.getProvider().getName());
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] ivbytes = cipher.getIV();
			System.out.println("iv len:"+ivbytes.length);
			for (int i = 0; i < ivbytes.length; i++) {
				System.out.print(ivbytes[i]);
			}
			System.out.println();
			
			
			String msg = "我是=I am,哈哈哈哈。。。。。。。。。。";
			byte[] msgbytes = msg.getBytes(msgCharset);
			System.out.println("msg len:"+msgbytes.length);
			byte[] cpr = cipher.doFinal(msgbytes);// reset to init state
			System.out.println("cipher len:"+cpr.length);
			
			
			//解密：
			byte[] rawsecretKey = secretKey.getEncoded();
			/*
			 * SecretKey直接用子类SecretKeySpec
			 */
			SecretKeySpec secretKeySpec = new SecretKeySpec(rawsecretKey,secretKeyFactoryAlg);
			//SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(secretKeyFactoryAlg);
			//SecretKey secretKey2 = (SecretKey)secretKeySpec;
			
			Cipher cipher2 = Cipher.getInstance(encryptionAlg);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(ivbytes);
			cipher2.init(Cipher.DECRYPT_MODE, secretKeySpec,ivParameterSpec);
			byte[] plain = cipher2.doFinal(cpr);
			String text = new String(plain, msgCharset);
			System.out.println("msg decrypted:"+text);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

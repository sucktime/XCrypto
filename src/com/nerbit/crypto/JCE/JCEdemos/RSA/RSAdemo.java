package com.nerbit.crypto.JCE.JCEdemos.RSA;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAdemo {
	
	public static void demo_encypt_decrypt() {
		
		/* Look up:
		 * Java Cryptography Architecture Standard Algorithm Name Documentation for JDK 8
		 */
		Provider srProvider = Security.getProvider("SUN");
		String srAlg = "SHA1PRNG";
		
		Provider encryptionProvider = Security.getProvider("SunJCE");
		String encryptionAlg = "RSA/ECB/PKCS1PADDING";
		
		Provider keypairgeneratorProvider = Security.getProvider("SunJSSE");
		String keyPairGeneratorAlg = "RSA";
		
		//String signAlg = "SHA1withRSA";
		
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyPairGeneratorAlg, keypairgeneratorProvider);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)keyPair.getPrivate();
			RSAPublicKey rsaPublicKey   = (RSAPublicKey)keyPair.getPublic();
			
			Cipher cipher = Cipher.getInstance(encryptionAlg, encryptionProvider);
			//SecureRandom secureRandom = SecureRandom.getInstance(srAlg, srProvider);
			//cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey,secureRandom);
			cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
			String orinText = "wtf, main";
			System.out.println("original text:"+orinText);
			byte[] origin = orinText.getBytes("UTF-8");
			System.out.println("origin len:"+origin.length);
			for (int i = 0; i < origin.length; i++) {
				System.out.print(origin[i]+",");
			}
			System.out.println();
			byte[] cip =  cipher.doFinal(origin);
			System.out.println("cipher len:"+cip.length);
			
			cipher = Cipher.getInstance(encryptionAlg, encryptionProvider);
			cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
			byte[] plain = cipher.doFinal(cip);
			System.out.println("plain len:"+plain.length);
			for (int i = 0; i < plain.length; i++) {
				System.out.print(plain[i]+",");
			}
			System.out.println();
			String text = new String(plain,"UTF-8");
			System.out.println("text:"+text);
			
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void demo_sign_verify() {
		String keygeneratorAlg = "RSA";
		Provider keygeneratorProvider = null;
		
		String signatureAlg   = "SHA1withRSA";
		Provider signatureProvider  = null;
		
		String publickeyFormat = null;
		String privatekeyFormat = null;
		
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keygeneratorProvider = keyPairGenerator.getProvider();
			System.out.println("keyfenerator provider:"+keygeneratorProvider.getName());
			KeyPair keyPair = keyPairGenerator.getInstance("RSA").generateKeyPair();
			
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)keyPair.getPrivate();
			RSAPublicKey rsaPublicKey = (RSAPublicKey)keyPair.getPublic();
			
			privatekeyFormat = rsaPrivateKey.getFormat();
			publickeyFormat = rsaPublicKey.getFormat();
			System.out.println("public key format:" + publickeyFormat);
			System.out.println("private key format:" + privatekeyFormat);
			
			//签名：
			Signature signature = Signature.getInstance(signatureAlg);
			signatureProvider = signature.getProvider();
			System.out.println("signature provider:"+signatureProvider.getName());
			signature.initSign(rsaPrivateKey);
			String msg = "我的，mine.";
			System.out.println("msg:"+msg);
			byte[] msgbytes = msg.getBytes("UTF-8");
			signature.update(msgbytes);//update will reset signature object to initsign state
			byte[] sigbytes = signature.sign();
			System.out.println("signature len:"+sigbytes.length);
			
			//验签：
			signature = Signature.getInstance(signatureAlg);
			signature.initVerify(rsaPublicKey);
			signature.update(msgbytes);
			boolean re = signature.verify(sigbytes);
			System.out.println("verify:"+re);
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException | SignatureException | InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

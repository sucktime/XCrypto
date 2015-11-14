package com.nerbit.crypto.JCE.JCEdemos.RSA.RSAUtil;

import org.apache.commons.codec.binary.Base64;

public class MainTest {  
	  
    public static void main(String[] args) throws Exception {  
        String filepath="D:";  
  
        RSAUtils.genKeyPair(filepath);  
          
          
        System.out.println("--------------公钥加密私钥解密过程-------------------");  
        String plainText="ihep_公钥加密私钥解密";  
        //公钥加密过程  
        byte[] cipherData=RSAUtils.encrypt(RSAUtils.loadPublicKeyByStr(RSAUtils.loadPublicKeyByFile(filepath)),plainText.getBytes());  
        String cipher=Base64.encodeBase64String(cipherData);  
        //私钥解密过程  
        byte[] res=RSAUtils.decrypt(RSAUtils.loadPrivateKeyByStr(RSAUtils.loadPrivateKeyByFile(filepath)), Base64.decodeBase64(cipher));  
        String restr=new String(res);  
        System.out.println("原文："+plainText+":"+plainText.getBytes().length);  
        System.out.println("加密："+cipher+":"+cipherData.length);  
        System.out.println("解密："+restr+":"+res.length);  
        System.out.println();  
          
        System.out.println("--------------私钥加密公钥解密过程-------------------");  
        plainText="ihep_私钥加密公钥解密";  
        //私钥加密过程  
        cipherData=RSAUtils.encrypt(RSAUtils.loadPrivateKeyByStr(RSAUtils.loadPrivateKeyByFile(filepath)),plainText.getBytes());  
        cipher=Base64.encodeBase64String(cipherData);  
        //公钥解密过程  
        res=RSAUtils.decrypt(RSAUtils.loadPublicKeyByStr(RSAUtils.loadPublicKeyByFile(filepath)), Base64.decodeBase64(cipher));  
        restr=new String(res);  
        System.out.println("原文："+plainText);  
        System.out.println("加密："+cipher);  
        System.out.println("解密："+restr);  
        System.out.println();  
          
        System.out.println("---------------私钥签名过程------------------");  
        String content="ihep_这是用于签名的原始数据";  
        String signstr=RSAUtils.sign(content,RSAUtils.loadPrivateKeyByFile(filepath));  
        System.out.println("签名原串："+content);  
        System.out.println("签名串："+signstr);  
        System.out.println();  
          
        System.out.println("---------------公钥校验签名------------------");  
        System.out.println("签名原串："+content);  
        System.out.println("签名串："+signstr);  
          
        System.out.println("验签结果："+RSAUtils.doCheck(content, signstr, RSAUtils.loadPublicKeyByFile(filepath)));  
        System.out.println();  
          
    }  
}  
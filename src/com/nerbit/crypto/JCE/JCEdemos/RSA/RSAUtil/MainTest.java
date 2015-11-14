package com.nerbit.crypto.JCE.JCEdemos.RSA.RSAUtil;

import org.apache.commons.codec.binary.Base64;

public class MainTest {  
	  
    public static void main(String[] args) throws Exception {  
        String filepath="D:";  
  
        RSAUtils.genKeyPair(filepath);  
          
          
        System.out.println("--------------��Կ����˽Կ���ܹ���-------------------");  
        String plainText="ihep_��Կ����˽Կ����";  
        //��Կ���ܹ���  
        byte[] cipherData=RSAUtils.encrypt(RSAUtils.loadPublicKeyByStr(RSAUtils.loadPublicKeyByFile(filepath)),plainText.getBytes());  
        String cipher=Base64.encodeBase64String(cipherData);  
        //˽Կ���ܹ���  
        byte[] res=RSAUtils.decrypt(RSAUtils.loadPrivateKeyByStr(RSAUtils.loadPrivateKeyByFile(filepath)), Base64.decodeBase64(cipher));  
        String restr=new String(res);  
        System.out.println("ԭ�ģ�"+plainText+":"+plainText.getBytes().length);  
        System.out.println("���ܣ�"+cipher+":"+cipherData.length);  
        System.out.println("���ܣ�"+restr+":"+res.length);  
        System.out.println();  
          
        System.out.println("--------------˽Կ���ܹ�Կ���ܹ���-------------------");  
        plainText="ihep_˽Կ���ܹ�Կ����";  
        //˽Կ���ܹ���  
        cipherData=RSAUtils.encrypt(RSAUtils.loadPrivateKeyByStr(RSAUtils.loadPrivateKeyByFile(filepath)),plainText.getBytes());  
        cipher=Base64.encodeBase64String(cipherData);  
        //��Կ���ܹ���  
        res=RSAUtils.decrypt(RSAUtils.loadPublicKeyByStr(RSAUtils.loadPublicKeyByFile(filepath)), Base64.decodeBase64(cipher));  
        restr=new String(res);  
        System.out.println("ԭ�ģ�"+plainText);  
        System.out.println("���ܣ�"+cipher);  
        System.out.println("���ܣ�"+restr);  
        System.out.println();  
          
        System.out.println("---------------˽Կǩ������------------------");  
        String content="ihep_��������ǩ����ԭʼ����";  
        String signstr=RSAUtils.sign(content,RSAUtils.loadPrivateKeyByFile(filepath));  
        System.out.println("ǩ��ԭ����"+content);  
        System.out.println("ǩ������"+signstr);  
        System.out.println();  
          
        System.out.println("---------------��ԿУ��ǩ��------------------");  
        System.out.println("ǩ��ԭ����"+content);  
        System.out.println("ǩ������"+signstr);  
          
        System.out.println("��ǩ�����"+RSAUtils.doCheck(content, signstr, RSAUtils.loadPublicKeyByFile(filepath)));  
        System.out.println();  
          
    }  
}  
package com.nerbit.crypto.JCE.JCEdemos.RSA.RSAUtil;

import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.FileReader;  
import java.io.FileWriter;  
import java.io.IOException;  
import java.security.InvalidKeyException;  
import java.security.KeyFactory;  
import java.security.KeyPair;  
import java.security.KeyPairGenerator;  
import java.security.NoSuchAlgorithmException;  
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;  
  
import java.security.interfaces.RSAPrivateKey;  
import java.security.interfaces.RSAPublicKey;  
import java.security.spec.InvalidKeySpecException;  
import java.security.spec.PKCS8EncodedKeySpec;  
import java.security.spec.X509EncodedKeySpec;  
  



import javax.crypto.BadPaddingException;  
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.NoSuchPaddingException;  

import org.apache.commons.codec.binary.Base64;
  
public class RSAUtils {  
    /** 
     * �ֽ�����ת�ַ���ר�ü��� 
     */  
    private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',  
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
  
    /** 
     * ���������Կ�� 
     */  
    public static void genKeyPair(String filePath) {  
        // KeyPairGenerator���������ɹ�Կ��˽Կ�ԣ�����RSA�㷨���ɶ���  
        KeyPairGenerator keyPairGen = null;  
        try {  
            keyPairGen = KeyPairGenerator.getInstance("RSA");  
        } catch (NoSuchAlgorithmException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        // ��ʼ����Կ������������Կ��СΪ96-1024λ  
        keyPairGen.initialize(1024,new SecureRandom());  
        // ����һ����Կ�ԣ�������keyPair��  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        // �õ�˽Կ  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
        // �õ���Կ  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        try {  
            new Base64();
			// �õ���Կ�ַ���  
            String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());  
            System.out.println("public key format:"+publicKey.getFormat());
            System.out.println("public key len:"+publicKey.getEncoded().length);
            // �õ�˽Կ�ַ���  
            String privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());
            System.out.println("private key format:"+privateKey.getFormat());
            System.out.println("private key len:"+privateKey.getEncoded().length);
            // ����Կ��д�뵽�ļ�  
            FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");  
            FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");  
            BufferedWriter pubbw = new BufferedWriter(pubfw);  
            BufferedWriter pribw = new BufferedWriter(prifw);  
            pubbw.write(publicKeyString);  
            pribw.write(privateKeyString);  
            pubbw.flush();  
            pubbw.close();  
            pubfw.close();  
            pribw.flush();  
            pribw.close();  
            prifw.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * ���ļ����������м��ع�Կ 
     *  
     * @param in 
     *            ��Կ������ 
     * @throws Exception 
     *             ���ع�Կʱ�������쳣 
     */  
    public static String loadPublicKeyByFile(String path) throws Exception {  
        try {  
            BufferedReader br = new BufferedReader(new FileReader(path  
                    + "\\publicKey.keystore"));  
            String readLine = null;  
            StringBuilder sb = new StringBuilder();  
            while ((readLine = br.readLine()) != null) {  
                sb.append(readLine);  
            }  
            br.close();  
            return sb.toString();  
        } catch (IOException e) {  
            throw new Exception("��Կ��������ȡ����");  
        } catch (NullPointerException e) {  
            throw new Exception("��Կ������Ϊ��");  
        }  
    }  
  
    /** 
     * ���ַ����м��ع�Կ 
     *  
     * @param publicKeyStr 
     *            ��Կ�����ַ��� 
     * @throws Exception 
     *             ���ع�Կʱ�������쳣 
     */  
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)  
            throws Exception {  
        try {  
            byte[] buffer = Base64.decodeBase64(publicKeyStr);  
            //System.out.println("public key len:"+buffer.length);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("�޴��㷨");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("��Կ�Ƿ�");  
        } catch (NullPointerException e) {  
            throw new Exception("��Կ����Ϊ��");  
        }  
    }  
  
    /** 
     * ���ļ��м���˽Կ 
     *  
     * @param keyFileName 
     *            ˽Կ�ļ��� 
     * @return �Ƿ�ɹ� 
     * @throws Exception 
     */  
    public static String loadPrivateKeyByFile(String path) throws Exception {  
        try {  
            BufferedReader br = new BufferedReader(new FileReader(path  
                    + "/privateKey.keystore"));  
            String readLine = null;  
            StringBuilder sb = new StringBuilder();  
            while ((readLine = br.readLine()) != null) {  
                sb.append(readLine);  
            }  
            br.close();  
            return sb.toString();  
        } catch (IOException e) {  
            throw new Exception("˽Կ���ݶ�ȡ����");  
        } catch (NullPointerException e) {  
            throw new Exception("˽Կ������Ϊ��");  
        }  
    }  
  
    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)  
            throws Exception {  
        try {  
            byte[] buffer = Base64.decodeBase64(privateKeyStr); 
            //System.out.println("private key len:"+buffer.length);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("�޴��㷨");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("˽Կ�Ƿ�");  
        } catch (NullPointerException e) {  
            throw new Exception("˽Կ����Ϊ��");  
        }  
    }  
  
    /** 
     * ��Կ���ܹ��� 
     *  
     * @param publicKey 
     *            ��Կ 
     * @param plainTextData 
     *            �������� 
     * @return 
     * @throws Exception 
     *             ���ܹ����е��쳣��Ϣ 
     */  
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)  
            throws Exception {  
        if (publicKey == null) {  
            throw new Exception("���ܹ�ԿΪ��, ������");  
        }  
        Cipher cipher = null;  
        try {  
            // ʹ��Ĭ��RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] output = cipher.doFinal(plainTextData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("�޴˼����㷨");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("���ܹ�Կ�Ƿ�,����");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("���ĳ��ȷǷ�");  
        } catch (BadPaddingException e) {  
            throw new Exception("������������");  
        }  
    }  
  
    /** 
     * ˽Կ���ܹ��� 
     *  
     * @param privateKey 
     *            ˽Կ 
     * @param plainTextData 
     *            �������� 
     * @return 
     * @throws Exception 
     *             ���ܹ����е��쳣��Ϣ 
     */  
    public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData)  
            throws Exception {  
        if (privateKey == null) {  
            throw new Exception("����˽ԿΪ��, ������");  
        }  
        Cipher cipher = null;  
        try {  
            // ʹ��Ĭ��RSA  
            cipher = Cipher.getInstance("RSA");  
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
            byte[] output = cipher.doFinal(plainTextData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("�޴˼����㷨");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("����˽Կ�Ƿ�,����");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("���ĳ��ȷǷ�");  
        } catch (BadPaddingException e) {  
            throw new Exception("������������");  
        }  
    }  
  
    /** 
     * ˽Կ���ܹ��� 
     *  
     * @param privateKey 
     *            ˽Կ 
     * @param cipherData 
     *            �������� 
     * @return ���� 
     * @throws Exception 
     *             ���ܹ����е��쳣��Ϣ 
     */  
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)  
            throws Exception {  
        if (privateKey == null) {  
            throw new Exception("����˽ԿΪ��, ������");  
        }  
        Cipher cipher = null;  
        try {  
            // ʹ��Ĭ��RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] output = cipher.doFinal(cipherData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("�޴˽����㷨");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("����˽Կ�Ƿ�,����");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("���ĳ��ȷǷ�");  
        } catch (BadPaddingException e) {  
            throw new Exception("������������");  
        }  
    }  
  
    /** 
     * ��Կ���ܹ��� 
     *  
     * @param publicKey 
     *            ��Կ 
     * @param cipherData 
     *            �������� 
     * @return ���� 
     * @throws Exception 
     *             ���ܹ����е��쳣��Ϣ 
     */  
    public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData)  
            throws Exception {  
        if (publicKey == null) {  
            throw new Exception("���ܹ�ԿΪ��, ������");  
        }  
        Cipher cipher = null;  
        try {  
            // ʹ��Ĭ��RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.DECRYPT_MODE, publicKey);  
            byte[] output = cipher.doFinal(cipherData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("�޴˽����㷨");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("���ܹ�Կ�Ƿ�,����");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("���ĳ��ȷǷ�");  
        } catch (BadPaddingException e) {  
            throw new Exception("������������");  
        }  
    }  
  
    /** 
     * �ֽ�����תʮ�������ַ��� 
     *  
     * @param data 
     *            �������� 
     * @return ʮ���������� 
     */  
    public static String byteArrayToString(byte[] data) {  
        StringBuilder stringBuilder = new StringBuilder();  
        for (int i = 0; i < data.length; i++) {  
            // ȡ���ֽڵĸ���λ ��Ϊ�����õ���Ӧ��ʮ�����Ʊ�ʶ�� ע���޷�������  
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);  
            // ȡ���ֽڵĵ���λ ��Ϊ�����õ���Ӧ��ʮ�����Ʊ�ʶ��  
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);  
            if (i < data.length - 1) {  
                stringBuilder.append(' ');  
            }  
        }  
        return stringBuilder.toString();  
    }  
    
    
    /** 
     * ǩ���㷨 
     */  
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";  
  
    /** 
    * RSAǩ�� 
    * @param content ��ǩ������ 
    * @param privateKey �̻�˽Կ 
    * @param encode �ַ������� 
    * @return ǩ��ֵ 
    */  
    public static String sign(String content, String privateKey, String encode)  
    {  
        try   
        {  
            PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( Base64.decodeBase64(privateKey) );   
              
            KeyFactory keyf                 = KeyFactory.getInstance("RSA");  
            PrivateKey priKey               = keyf.generatePrivate(priPKCS8);  
  
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);  
  
            signature.initSign(priKey);  
            signature.update( content.getBytes(encode));  
  
            byte[] signed = signature.sign();  
              
            return Base64.encodeBase64String(signed);  
        }  
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
          
        return null;  
    }  
      
    public static String sign(String content, String privateKey)  
    {  
        try   
        {  
            PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( Base64.decodeBase64(privateKey) );   
            KeyFactory keyf = KeyFactory.getInstance("RSA");  
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);  
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);  
            signature.initSign(priKey);  
            signature.update( content.getBytes());  
            byte[] signed = signature.sign(); 
            System.out.println("signature len:"+signed.length);
            return Base64.encodeBase64String(signed);  
        }  
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    /** 
    * RSA��ǩ����� 
    * @param content ��ǩ������ 
    * @param sign ǩ��ֵ 
    * @param publicKey ����������̹�Կ 
    * @param encode �ַ������� 
    * @return ����ֵ 
    */  
    public static boolean doCheck(String content, String sign, String publicKey,String encode)  
    {  
        try   
        {  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            byte[] encodedKey = Base64.decodeBase64(publicKey);  
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));  
  
          
            java.security.Signature signature = java.security.Signature  
            .getInstance(SIGN_ALGORITHMS);  
          
            signature.initVerify(pubKey);  
            signature.update( content.getBytes(encode) );  
          
            boolean bverify = signature.verify( Base64.decodeBase64(sign) );  
            return bverify;  
              
        }   
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
          
        return false;  
    }  
      
    public static boolean doCheck(String content, String sign, String publicKey)  
    {  
        try   
        {  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            byte[] encodedKey = Base64.decodeBase64(publicKey);  
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));  
  
          
            java.security.Signature signature = java.security.Signature  
            .getInstance(SIGN_ALGORITHMS);  
          
            signature.initVerify(pubKey);  
            signature.update( content.getBytes() );  
          
            boolean bverify = signature.verify( Base64.decodeBase64(sign) );  
            return bverify;  
              
        }   
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
          
        return false;  
    }  
}  
package com.nerbit.crypto.JCE.JCEdemos.SSL.HttpsURLConnection;

public class HTTPStest {
	/** 
     * ���Է���. 
     * @param args 
     * @throws Exception 
     */  
    public static void main(String[] args) throws Exception {  
        // ����  
        String password = "tomcat";
        // ��Կ��  
        String keyStorePath = "client.jks";  
        // ���ο�  
        String trustStorePath = "server.jks";  
        // �������https����  
        String httpsUrl = "https://localhost:8443/";  
        // �����ı�  
        String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fruitShop><fruits><fruit><kind>�ܲ�</kind></fruit><fruit><kind>����</kind></fruit></fruits></fruitShop>";  
        HttpsPost.initHttpsURLConnection(password, keyStorePath, trustStorePath);  
        // ��������  
        HttpsPost.post(httpsUrl, xmlStr);  
    }  
}

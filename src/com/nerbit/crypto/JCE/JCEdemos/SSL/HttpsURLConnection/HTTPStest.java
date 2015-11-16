package com.nerbit.crypto.JCE.JCEdemos.SSL.HttpsURLConnection;

public class HTTPStest {
	/** 
     * 测试方法. 
     * @param args 
     * @throws Exception 
     */  
    public static void main(String[] args) throws Exception {  
        // 密码  
        String password = "tomcat";
        // 密钥库  
        String keyStorePath = "client.jks";  
        // 信任库  
        String trustStorePath = "server.jks";  
        // 本地起的https服务  
        String httpsUrl = "https://localhost:8443/";  
        // 传输文本  
        String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fruitShop><fruits><fruit><kind>萝卜</kind></fruit><fruit><kind>菠萝</kind></fruit></fruits></fruitShop>";  
        HttpsPost.initHttpsURLConnection(password, keyStorePath, trustStorePath);  
        // 发起请求  
        HttpsPost.post(httpsUrl, xmlStr);  
    }  
}

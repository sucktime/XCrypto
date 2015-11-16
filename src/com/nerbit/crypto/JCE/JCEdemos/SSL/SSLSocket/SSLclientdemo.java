package com.nerbit.crypto.JCE.JCEdemos.SSL.SSLSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.UnknownHostException;

import javax.security.cert.X509Certificate;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLclientdemo {

	/*
	 * 默认在第一次对outputStream进行写入时进行初次SSL握手，并建立SSLsession，以后的写入复用这个SSLsession
	 * 可以通过setEnableSessioCreation来设置是否建立会话，如果否则每次发送消息都必须重新进行SSL握手
	 * 可以通过StartHandshake在初次发送消息前就建立会话
	 */
	public static void demo_SSLsocketclient() throws UnknownHostException,IOException {

		System.setProperty("javax.net.ssl.trustStore","trusted.jks");//设置可信任的密钥仓库 
		System.setProperty("javax.net.ssl.trustStorePassword","tomcat"); //设置可信任的密钥仓库的密码 
		SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

		//SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket("baidu.com", 443);
		SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket("localhost", 8443);
		/*
		 * SSLSocketFactory也可以用下面的方式来创建：
		 	SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(keyManagers, trustManagers, new SecureRandom());
			SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
		 */
		
		sslSocket
				.addHandshakeCompletedListener(new HandshakeCompletedListener() {
					@Override
					public void handshakeCompleted(HandshakeCompletedEvent event) {
						try {
							X509Certificate[] certChain = event
									.getPeerCertificateChain();
							for (X509Certificate cert : certChain) {
								System.out.println(cert.toString());
							}
						} catch (SSLPeerUnverifiedException e) {
							e.printStackTrace();
						}
					}
				});
		
		
		try{
			sslSocket.startHandshake();
		}catch(SSLHandshakeException e){
			//当内部出现sun.security.validator.ValidatorException（证书验证失败）时会将其捕获并抛出SSLHandshakeException
			System.out.println("handshalke() falied....");
			e.printStackTrace();
		}
		

		System.out.println("before:");
		// 设置算法套餐：
		String[] supportedSuites = sslSocket.getSupportedCipherSuites();
		List<String> suitesAllowed = new ArrayList<String>(10);
		for (String s : supportedSuites) {
			if (s.matches(".*((ECDH)|(ECDHE)|(DHE)|(RSA)).*((ECDSA)|(RSA)).*AES.*CBC.*SHA"))
				System.out.println(s);
			suitesAllowed.add(s);
		}
		sslSocket.setEnabledCipherSuites(suitesAllowed.toArray(new String[0]));
		System.out.println("after");

		
		//HTTP GET:
		Writer outputwriter = new OutputStreamWriter(
				sslSocket.getOutputStream());
		System.out.println("before writting...");
		//outputwriter.write("GET http://baidu.com/ HTTP/1.1\r\n");
		//outputwriter.write("Host: baidu.com\r\n");
		//outputwriter.write("\r\n");
		outputwriter.write("GET / HTTP/1.1\r\n");
		outputwriter.write("Host: localhost:8443\r\nConnection: Keep-Alive\r\nContent-Length: 0\r\n");
		outputwriter.write("\r\n");
		outputwriter.flush();
		System.out.println("after writting...");

		//HTTP RESPONSE:
		System.out.println("before reading....");
		System.out.println(sslSocket.getInputStream().available());
		BufferedReader br = new BufferedReader(new InputStreamReader(
				sslSocket.getInputStream()));
		String line = null;
		
		System.out.println("read from server:");
		while ((line = br.readLine()) != null)System.out.println(line);
		System.out.println();//Header与Content之间的空行

	
		System.out.println("after reading....");
	}

}

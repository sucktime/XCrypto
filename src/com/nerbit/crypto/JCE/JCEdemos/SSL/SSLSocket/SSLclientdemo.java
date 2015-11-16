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
	 * Ĭ���ڵ�һ�ζ�outputStream����д��ʱ���г���SSL���֣�������SSLsession���Ժ��д�븴�����SSLsession
	 * ����ͨ��setEnableSessioCreation�������Ƿ����Ự���������ÿ�η�����Ϣ���������½���SSL����
	 * ����ͨ��StartHandshake�ڳ��η�����Ϣǰ�ͽ����Ự
	 */
	public static void demo_SSLsocketclient() throws UnknownHostException,IOException {

		System.setProperty("javax.net.ssl.trustStore","trusted.jks");//���ÿ����ε���Կ�ֿ� 
		System.setProperty("javax.net.ssl.trustStorePassword","tomcat"); //���ÿ����ε���Կ�ֿ������ 
		SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

		//SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket("baidu.com", 443);
		SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket("localhost", 8443);
		/*
		 * SSLSocketFactoryҲ����������ķ�ʽ��������
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
			//���ڲ�����sun.security.validator.ValidatorException��֤����֤ʧ�ܣ�ʱ�Ὣ�䲶���׳�SSLHandshakeException
			System.out.println("handshalke() falied....");
			e.printStackTrace();
		}
		

		System.out.println("before:");
		// �����㷨�ײͣ�
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
		System.out.println();//Header��Content֮��Ŀ���

	
		System.out.println("after reading....");
	}

}

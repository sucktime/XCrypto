package com.nerbit.crypto.JCE.JCEdemos.SSL.SSLSocket;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;

public class SSLServerdemo {
		
	public static void demo_SSLServerSocket() {
		try {
			/*
			 * ����Session���ǵ��£�ͬSSLSocketһ���Ļ���
			 */

			//1. �����ͼ���keyStore��
			KeyStore keyStore = KeyStore.getInstance("JKS");
			char[] password = "tomcat".toCharArray();
			keyStore.load(new FileInputStream("server.jks"), password);//�����password������������keystore
			//2. ����KeyManager[]��
			//ֻ֧��X.509��Կ�����ͣ�
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(keyStore, password);//�����password��������keyStore�����˽Կ
			KeyManager[] keyManagers = kmf.getKeyManagers();
			//3. ����TrustManagers��
			TrustManager[] trustManagers = null;
			//4. ����SSLContext��
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(keyManagers, trustManagers, new SecureRandom());
			Arrays.fill(password, '0');
			//5. ����SSLServerSocket��
			SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
			SSLServerSocket sslServerSocket = (SSLServerSocket)sslServerSocketFactory.createServerSocket(8088);
			
			//��һ������sslServerSocket��
			String[] supportedSuites = sslServerSocket.getEnabledCipherSuites();
			List<String> suitesAllowed = new ArrayList<String>(10);
			for (String s : supportedSuites) {
				if (s.matches(".*((ECDH)|(ECDHE)|(DHE)|(RSA)).*((ECDSA)|(RSA)).*AES.*CBC.*SHA"))
					System.out.println(s);
				suitesAllowed.add(s);
			}
			sslServerSocket.setEnabledCipherSuites(suitesAllowed.toArray(new String[0]));
			sslServerSocket.setNeedClientAuth(false);
			
			
			
			
			//��Socket��̣�
			while(true){
				Socket socket = sslServerSocket.accept();
				InputStream in = socket.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String line = null;
				while((line = br.readLine())!= null){
					System.out.print(line);
				}
			}
			
		} catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException | UnrecoverableKeyException | KeyManagementException e) {
			e.printStackTrace();
		}
		
	}
}

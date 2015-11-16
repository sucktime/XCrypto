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
			 * 关于Session的那点事，同SSLSocket一样的机制
			 */

			//1. 创建和加载keyStore：
			KeyStore keyStore = KeyStore.getInstance("JKS");
			char[] password = "tomcat".toCharArray();
			keyStore.load(new FileInputStream("server.jks"), password);//这里的password用来解密整个keystore
			//2. 创建KeyManager[]：
			//只支持X.509密钥库类型：
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(keyStore, password);//这里的password用来解密keyStore里面的私钥
			KeyManager[] keyManagers = kmf.getKeyManagers();
			//3. 创建TrustManagers：
			TrustManager[] trustManagers = null;
			//4. 创建SSLContext：
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(keyManagers, trustManagers, new SecureRandom());
			Arrays.fill(password, '0');
			//5. 创建SSLServerSocket：
			SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
			SSLServerSocket sslServerSocket = (SSLServerSocket)sslServerSocketFactory.createServerSocket(8088);
			
			//进一步配置sslServerSocket：
			String[] supportedSuites = sslServerSocket.getEnabledCipherSuites();
			List<String> suitesAllowed = new ArrayList<String>(10);
			for (String s : supportedSuites) {
				if (s.matches(".*((ECDH)|(ECDHE)|(DHE)|(RSA)).*((ECDSA)|(RSA)).*AES.*CBC.*SHA"))
					System.out.println(s);
				suitesAllowed.add(s);
			}
			sslServerSocket.setEnabledCipherSuites(suitesAllowed.toArray(new String[0]));
			sslServerSocket.setNeedClientAuth(false);
			
			
			
			
			//纯Socket编程：
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

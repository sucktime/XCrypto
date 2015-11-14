package com.nerbit.crypto.JCE.JCEdemos.RSA;



public class Main {
	
	public static void main(String[] args) {
		ProviderInformation.printProviders();
		RSAdemo.demo_encypt_decrypt();
		RSAdemo.demo_sign_verify();
	}
	
}

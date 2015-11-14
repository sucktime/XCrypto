package com.nerbit.crypto.JCE.JCEdemos.PBE;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PBETest {
	public static void main(String[] args) {
		try {
			PBEdemo.demo_pbe();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

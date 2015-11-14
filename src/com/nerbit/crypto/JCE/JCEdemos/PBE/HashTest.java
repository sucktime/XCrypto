package com.nerbit.crypto.JCE.JCEdemos.PBE;

import com.nerbit.crypto.JCE.JCEdemos.Hash.Hashdemo;

public class HashTest {
	public static void main(String[] args) {
		try {
			Hashdemo.demo_digests();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

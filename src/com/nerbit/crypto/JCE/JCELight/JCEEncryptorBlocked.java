package com.nerbit.crypto.JCE.JCELight;

public abstract class JCEEncryptorBlocked {
	
	public abstract byte[] encryptAllBlocks(byte[] plain);
	public abstract byte[] encryptNextBlocks(byte[] plain);
}

package com.nerbit.crypto.JCE.JCELight;

public abstract class JCEDecryptorBlocked {
	
	public abstract byte[] decryptAllBlocks(byte[] cipher);
	public abstract byte[] decryptNextBlocks(byte[] cipher);
}

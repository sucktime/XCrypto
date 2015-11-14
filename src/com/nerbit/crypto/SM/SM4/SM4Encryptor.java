package com.nerbit.crypto.SM.SM4;

public class SM4Encryptor extends SM4Stateful{
	/*
	 * ���ܹ����з����СΪ��16-byte ��ˣ�ÿ�δ����msg���ȱ���Ϊ16�ı���
	 */
	private native byte[] sm4EncryptCBC(byte[] msg, byte[] IV, byte[] key);
	
	private native byte[] sm4EncryptECB(byte[] msg, byte[] key);
	
	public byte[] sm4EncryptCBC(byte[] msg) throws Exception {
		if (msg == null || msg.length % 16 != 0)
			throw new Exception("msg cannot be null AND msg.length%16 must be 0");
		byte[] cipher = sm4EncryptCBC(msg, IV_cache, this.key);
		System.arraycopy(cipher, cipher.length - 16, this.IV_cache, 0, 16);
		return cipher;
	}
	
	public byte[] sm4EncryptECB(byte[] msg) throws Exception {
		if (msg == null || msg.length % 16 != 0)
			throw new Exception("msg cannot be null AND msg.length%16 must be 0");
		return sm4EncryptECB(msg, this.key);
	}
	
	public SM4Encryptor() {
		super();
	}
	
	public SM4Encryptor(byte[] key) throws Exception {
		super(key);
	}
	
	public SM4Encryptor(byte[] key, byte[] initIV) throws Exception{
		super(key, initIV);
	}
}

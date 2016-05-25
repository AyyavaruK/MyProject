package com.telligent.util;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Component;

/**
 * The class is for Encryption and Decryption using Bouncy Castle Encryption API 
 * @author spothu
 * 29 Oct 2014
 */
@Component
public class BouncyCastleEncryptor {

	private static final String salt = "A long, but constant phrase that will be used each time as the salt.";
	private static final int iterations = 2000;
	private static final int keyLength = 256;
	//    private static final SecureRandom random = new SecureRandom();

	public BouncyCastleEncryptor(){
		Security.insertProviderAt(new BouncyCastleProvider(), 1);
	}
	public static void main(String [] args) throws Exception {
		BouncyCastleEncryptor crypt = new BouncyCastleEncryptor();
		String plaintext = "telligent";

		String  ciphertext = crypt.encryptString(plaintext);
		System.out.println(ciphertext);
		String recoveredPlaintext = crypt.decryptString("TbXaC+kquMi6AL8q9/eSjA==");
		System.out.println(recoveredPlaintext);

	}

	public String encryptString(String plaintext) throws Exception {
		SecretKey key = generateKey("The quick brown fox jumped over the lazy brown dog");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);	
		String ciphertext = new String(Base64.encode(cipher.doFinal(plaintext.getBytes())));
		return ciphertext;
	}

	public String decryptString(String ciphertext) throws Exception {
		SecretKey key = generateKey("The quick brown fox jumped over the lazy brown dog");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(Base64.decode(ciphertext.getBytes())));
	}

	private static SecretKey generateKey(String passphrase) throws Exception {
		PBEKeySpec keySpec = new PBEKeySpec(passphrase.toCharArray(), salt.getBytes(), iterations, keyLength);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWITHSHA256AND256BITAES-CBC-BC");
		return keyFactory.generateSecret(keySpec);
	}
}

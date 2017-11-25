package br.com.lucy.security;

import java.math.BigInteger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Security {

	public static String encrypt(String input) {

		long leftLimit = 1000000000000000L;
		long rightLimit = 9999999999999999L;
		long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

		String key = generatedLong + "";
		byte[] crypted = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		String keyHex = new BigInteger(key).toString(16);
		return new String(Base64.encodeBase64(crypted)) + keyHex + getFormatedSizeTime(keyHex);
	}

	public static String decrypt(String input) {

		String sizeKey = input.substring(input.length() - 3, input.length());
		String keyHex = input.substring(input.length() - (3 + Integer.parseInt(sizeKey)), input.length() - 3);
		String cripted = input.substring(0, input.length() - (3 + Integer.parseInt(sizeKey)));

		String key = new BigInteger(keyHex, 16).toString();

		byte[] output = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			output = cipher.doFinal(Base64.decodeBase64(cripted));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return new String(output);
	}

	private static String getFormatedSizeTime(String keyHex) {
		String returned = null;
		Integer length = keyHex.length();
		if (length < 10) {
			returned = "00" + length;
		} else if (length < 100) {
			returned = "0" + length;
		} else if (length < 1000) {
			returned = length.toString();
		} else {
			returned = "000";
		}

		return returned;
	}

	public static void main(String[] args) {
		String key = "1234567891234567";
		String data = "example";
		System.out.println(key.length());
		System.out.println(Security.decrypt("sou9uMu2dEXrr6sTNkEfYQ==fdf37e132831f013"));
		System.out.println(Security.encrypt(data));
	}
}
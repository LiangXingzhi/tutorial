package com.ericsson.ct.cloud.nfvi.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class AESUtil {
	
	public static String decrypt(String es, String seed) {
		return encrypt(es, seed, false);
	}

	private static String encrypt(String s, String seed, boolean isEncrypt) {
		// 加密
		Cipher cipher;
		String result = null;
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(seed.getBytes());
			keyGenerator.init(128, random);
			
	        
			// 使用上面这种初始化方法可以特定种子来生成密钥，这样加密后的密文是唯一固定的。
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();
			// Key转换
			Key key = new SecretKeySpec(keyBytes, "AES");
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			byte[] bytes = null;
			if (isEncrypt) {
				cipher.init(Cipher.ENCRYPT_MODE, key);
				bytes = s.getBytes();
			} else {
				cipher.init(Cipher.DECRYPT_MODE, key);
				bytes = Base64.decodeBase64(s);
			}
			byte[] encodeResult = cipher.doFinal(bytes);
			if (isEncrypt) {
				result = Base64.encodeBase64String(encodeResult);
			} else {
				result = new String(encodeResult);
			}
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// java -jar AESUtil.main "pass" "seed"
	public static void main(String[] args) {
		if (args.length >= 2) {
			String password = args[0];
			String seed = args[1];
			String s = encrypt(password, seed, true);
			String r = decrypt(s, seed);
			System.out.println(s + ":" + r);
		}

	}

}
package com.cxy.blog.common.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

 
public class PBKDF2Utils {
     
    private static final int SALT_SIZE = 16;
     
    private static final int HASH_SIZE = 32;
     
    private static final int PBKDF2_ITERATIONS = 1000;

     
    public static boolean verify(String password, String salt, String key)
            throws NoSuchAlgorithmException, InvalidKeySpecException, DecoderException {
                 String result = getPBKDF2(password, salt);
                 return result.equals(key);
    }

     
    public static String getPBKDF2(String password, String salt) throws NoSuchAlgorithmException,
            InvalidKeySpecException, DecoderException {
                 byte[] bytes = Hex.decodeHex(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), bytes, PBKDF2_ITERATIONS, HASH_SIZE * 4);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = secretKeyFactory.generateSecret(spec).getEncoded();
                 return Hex.encodeHexString(hash);
    }

     
    public static String getSalt() throws NoSuchAlgorithmException{
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] bytes = new byte[SALT_SIZE / 2];
        sr.nextBytes(bytes);
                 return Hex.encodeHexString(bytes);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException, DecoderException {
                 String salt=getSalt();
        System.out.println(salt);
                 System.out.println(getPBKDF2("123456",salt));
    }
}

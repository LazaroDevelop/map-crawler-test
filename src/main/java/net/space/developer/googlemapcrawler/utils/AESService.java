package net.space.developer.googlemapcrawler.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Component
public class AESService {

    @Value("${crypto.secret}")
    private String cryptoKey;

    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;

    public String encrypt(String plainText)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException
    {
        byte[] key = this.cryptoKey.getBytes();
        SecretKey sKey = new SecretKeySpec(key, ENCRYPTION_ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom.getInstanceStrong().nextBytes(iv);

        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, sKey, parameterSpec);

        byte[] encryptedText = cipher.doFinal(plainText.getBytes());
        byte[] encryptedIVAndText = new byte[iv.length + encryptedText.length];

        System.arraycopy(iv, 0, encryptedIVAndText, 0, iv.length);
        System.arraycopy(encryptedText, 0, encryptedIVAndText, iv.length, encryptedText.length);

        return Base64.getEncoder().encodeToString(encryptedIVAndText);
    }

    public String decrypt(String encryptedText)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException
    {
        byte[] key = this.cryptoKey.getBytes();
        SecretKey sKey = new SecretKeySpec(key, ENCRYPTION_ALGORITHM);
        byte[] decodedText = Base64.getDecoder().decode(encryptedText);

        byte[] iv = Arrays.copyOfRange(decodedText, 0, GCM_IV_LENGTH);
        byte[] encryptedTextBytes = Arrays.copyOfRange(decodedText, GCM_IV_LENGTH, decodedText.length);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, sKey, parameterSpec);

        byte[] decryptedText = cipher.doFinal(encryptedTextBytes);
        return new String(decryptedText);
    }
}

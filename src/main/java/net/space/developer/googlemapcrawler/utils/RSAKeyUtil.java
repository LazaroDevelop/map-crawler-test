package net.space.developer.googlemapcrawler.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;


public class RSAKeyUtil {
    /**
     * Capacidad para crear la llave
     */
    private static final int KEY_SIZE = 2048;
    /**
     * Algoritmo
     */
    private static final String ALGORITHM = "RSA";

    /**
     * Generador de pares de llaves
     * @return  un {@link KeyPair} que contiene el par de llaves publicas y privadas
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(KEY_SIZE);

        return keyGen.generateKeyPair();
    }

    /**
     * Provee llaves publicas
     * @param publicKey Llava publica para encriptar
     * @return llave publica como cadena
     */
    public static String getPublicKeyAsString(PublicKey publicKey){
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * Provee llaves privada
     * @param privateKey Llava privada para descencriptar
     * @return llave privada como cadena
     */
    public static String getPrivateKeyAsString(PrivateKey privateKey){
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /**
     * Toma la llave publica desde una cadena
     * @param publicKey cadena que tiene la llave publica
     * @return una instancia de {@link PublicKey}
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKeyFromString(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] byteKey = Base64.getDecoder().decode(publicKey);
        return KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(byteKey));
    }

    /**
     * Toma la llave privada desde una cadena
     * @param privateKey cadena que tiene la llave privada
     * @return una instancia de {@link PrivateKey}
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKeyFromString(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] byteKey = Base64.getDecoder().decode(privateKey);
        return KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(byteKey));
    }

    public static String encrypt(String dataToEncrypt, String publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKeyFromString(publicKey));
        return Base64.getEncoder().encodeToString(cipher.doFinal(dataToEncrypt.getBytes()));
    }

    public static String decrypt(String encryptedData, String privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKeyFromString(privateKey));
        byte[] decodeBytes = Base64.getDecoder().decode(encryptedData.getBytes());
        byte[] decryptBytes = cipher.doFinal(decodeBytes);
        return new String(decryptBytes);
    }
}

package ua.edu.ukma.tkachenko;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Array;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Encyptor {

    private static Cipher cipher;
    private static SecretKey secretKey;

    static {
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        byte[] encryptionKeyBytes = "thisIsA128BitKey".getBytes();
        secretKey = new SecretKeySpec(encryptionKeyBytes, "AES");
    }

    private Encyptor() {
    }

    public static byte[] encrypt(byte[] message) {
        byte[] encodedMessage = new byte[0];
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encodedMessage = cipher.doFinal(message);
        } catch (IllegalBlockSizeException | InvalidKeyException | BadPaddingException e) {
            e.printStackTrace();
        }
        return encodedMessage;
    }


    public static byte[] decrypt(byte[] message) {
        byte[] decodedMessage = new byte[0];
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decodedMessage = cipher.doFinal(message);
        } catch (IllegalBlockSizeException | InvalidKeyException | BadPaddingException e) {
            e.printStackTrace();
        }
        return decodedMessage;
    }

}

package com.example.demo;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class AESEncryption {

//    public static void main(String[] args) throws Exception {
//        // Generate a random AES key
//        SecretKey secretKey = generateAESKey();
//
//        // String to be encrypted
//        String originalString = "password";
//
//        // Encrypt the string
//        String encryptedString = encrypt(originalString, secretKey);
//        System.out.println("Encrypted String: " + encryptedString);
//
//        // Decrypt the string
//        String decryptedString = decrypt(encryptedString, secretKey);
//        System.out.println("Decrypted String: " + decryptedString);
//    }

    private static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // You can change this to 192 or 256 for different key sizes
        return keyGenerator.generateKey();
    }

    private static String encrypt(String plaintext, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        // Convert the byte array to a hex string for easier representation
        StringBuilder hexString = new StringBuilder();
        for (byte b : encryptedBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    private static String decrypt(String encryptedText, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        // Convert the hex string back to a byte array
        int length = encryptedText.length();
        byte[] encryptedBytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            encryptedBytes[i / 2] = (byte) ((Character.digit(encryptedText.charAt(i), 16) << 4)
                    + Character.digit(encryptedText.charAt(i + 1), 16));
        }

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
    public String encryptionDriver(String password,SecretKey passKey) throws Exception {
        SecretKey secretKey = generateAESKey();
        passKey = secretKey;
        password = encrypt(password,passKey);
        return password;
    }
    public String decryptionDriver(String encryptedPass,SecretKey passKey)throws Exception{
        encryptedPass = decrypt(encryptedPass,passKey);
        return encryptedPass;
    }
}

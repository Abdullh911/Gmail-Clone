package com.example.demo;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
    public static byte[] getHashedBytes (String string) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        return messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
    }
    public static String getHashedHex (byte[] hashedString){
        BigInteger bigInteger = new BigInteger(1,hashedString);
        StringBuilder hexString = new StringBuilder(bigInteger.toString());
        while(hexString.length()<64){
            hexString.insert(0,'0');
        }
        return hexString.toString();
    }
}

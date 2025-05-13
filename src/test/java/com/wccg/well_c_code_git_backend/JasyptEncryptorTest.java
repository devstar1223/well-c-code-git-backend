package com.wccg.well_c_code_git_backend;

import org.jasypt.util.text.AES256TextEncryptor;

public class JasyptEncryptorTest {
    public static void main(String[] args) {
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        String secretKey = System.getenv("JASYPT_ENCRYPTOR_PASSWORD");
        encryptor.setPassword(secretKey);

        String encryptedText = encryptor.encrypt("asdffffff");
        System.out.println("AccessSecret: " + encryptedText);

        String decryptedText = encryptor.decrypt(encryptedText);
        System.out.println("AccessSecret: " + decryptedText);
    }
}

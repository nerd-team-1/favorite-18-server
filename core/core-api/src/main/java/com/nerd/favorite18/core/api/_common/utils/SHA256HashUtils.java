package com.nerd.favorite18.core.api._common.utils;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SHA256HashUtils {
    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16; // 솔트 길이 (바이트)

    // 랜덤 솔트를 생성하는 메서드
    private static byte[] generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return salt;
    }

    // 해싱 메서드
    public static String hashToken(String token) {
        try {
            byte[] salt = generateSalt();
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            byte[] hashedToken = md.digest(token.getBytes());
            // 솔트와 해시를 Base64로 인코딩하여 반환
            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hashedToken);
        } catch (NoSuchAlgorithmException e) {
            throw new CoreApiException(ErrorType.DEFAULT_ERROR, "암호화에 실패하였습니다.");
        }
    }

    // 해시 검증 메서드
    public static boolean verifyToken(String token, String storedHash) {
        try {
            String[] parts = storedHash.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] storedHashBytes = Base64.getDecoder().decode(parts[1]);

            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            byte[] hashedToken = md.digest(token.getBytes());

            return MessageDigest.isEqual(storedHashBytes, hashedToken);
        } catch (NoSuchAlgorithmException e) {
            throw new CoreApiException(ErrorType.DEFAULT_ERROR, "검증에 실패하였습니다.");
        }
    }
}

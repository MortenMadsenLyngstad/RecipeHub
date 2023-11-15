package core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * This class is used to hash and verify passwords.
 */
public class PasswordHasher {
    private static final String HASHING_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * This method hashes a password.
     * 
     * @param password password to be hashed
     * @return String value for hashed password
     * @see #generateSalt()
     * @see #hashPasswordWithSalt(String, byte[])
     * @see #bytesToHex(byte[])
     */
    public static String hashPassword(String password) {
        byte[] salt = generateSalt();
        byte[] hashedPassword = hashPasswordWithSalt(password, salt);

        String saltBase64 = bytesToHex(salt);
        String hashedPasswordBase64 = bytesToHex(hashedPassword);

        return saltBase64 + ":" + hashedPasswordBase64;
    }

    /**
     * This method verifies a password.
     * Hashes the input password with the same salt as the stored password, 
     * and checks if they are then equal.
     * 
     * @param inputPassword input password to be verified
     * @param storedPassword - Stored, hashed password used in verification
     * @return Boolean value for whether the password is verified or not
     * @see #hexToBytes(String)
     * @see #hashPasswordWithSalt(String, byte[])
     */
    public static Boolean verifyPassword(String inputPassword, String storedPassword) {
        String[] parts = storedPassword.split(":");
        if (parts.length != 2) {
            return false;
        }
        byte[] salt = hexToBytes(storedPassword.split(":")[0]);
        byte[] storedHashedPassword = hexToBytes(storedPassword.split(":")[1]);

        byte[] hashedInput = hashPasswordWithSalt(inputPassword, salt);

        return MessageDigest.isEqual(storedHashedPassword, hashedInput);
    }

    /**
     * This method generates a salt.
     * 
     * @return Byte array for salt
     */
    private static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        RANDOM.nextBytes(salt);
        return salt;
    }

    /**
     * This method hashes a password with a salt.
     * 
     * @param password password to be hashed
     * @param salt salt used in hashing
     * @return Byte array for hashed password
     * @throws RuntimeException if hashing algorithm is not found
     */
    private static byte[] hashPasswordWithSalt(String password, byte[] salt) {
        try {
            MessageDigest message = MessageDigest.getInstance(HASHING_ALGORITHM);
            message.update(salt);
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            byte[] hashedPassword = message.digest(passwordBytes);
            return hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(
                    "Hashing algorithm \"" + HASHING_ALGORITHM + "\" is not found");
        }
    }

    /**
     * This method converts a byte array to a hex string.
     * 
     * @param bytes byte array to be converted
     * @return String value for hex string
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X", b));
        }
        return hexString.toString();
    }

    /**
     * This method converts a hex string to a byte array.
     * 
     * @param hex hex string to be converted
     * @return Byte array for hex string
     */
    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }
}

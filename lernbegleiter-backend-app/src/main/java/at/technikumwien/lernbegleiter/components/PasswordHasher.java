package at.technikumwien.lernbegleiter.components;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

@Component
public class PasswordHasher {
    protected final SecureRandom secureRandom = new SecureRandom();

    protected final int hashSizeInBytes;

    protected final int saltSizeInBytes;

    protected final String hashAlgorithmName;

    protected final int iterationCount;

    // size of hash and salt together (in bytes)
    protected final int arraySize;

    public PasswordHasher() {
        // The OWASP password storage cheat sheet (2015) recommends 10 000
        // iterations.
        // 64000 should be fine for a few years
        // https://www.owasp.org/index.php/Password_Storage_Cheat_Sheet
        this(64, 32, 64000, "PBKDF2WithHmacSHA512");
    }

    public PasswordHasher(int hashSize, int saltSize, int iterationCount, String hashAlgorithmName) {
        this.hashSizeInBytes = hashSize;
        this.saltSizeInBytes = saltSize;
        this.iterationCount = iterationCount;
        this.hashAlgorithmName = hashAlgorithmName;
        this.arraySize = hashSize + saltSize;
        createSecretKeyFactory(); // Ensure the algorithm is available right
        // away
    }

    public byte[] hashAndSalt(String password) {
        byte[] salt = new byte[saltSizeInBytes];
        secureRandom.nextBytes(salt);

        byte[] result = hashWithSalt(password, salt);

        return result;
    }

    protected byte[] hashWithSalt(String password, byte[] salt) {
        try {
            char[] passwordChars = password.toCharArray();

            PBEKeySpec spec = new PBEKeySpec(passwordChars, salt, iterationCount, hashSizeInBytes * 8);
            SecretKeyFactory skf = createSecretKeyFactory();
            SecretKey secretKey = skf.generateSecret(spec);

            byte[] hashedPassword = secretKey.getEncoded();

            byte[] result = new byte[hashSizeInBytes + saltSizeInBytes];
            System.arraycopy(hashedPassword, 0, result, 0, hashSizeInBytes);
            System.arraycopy(salt, 0, result, hashSizeInBytes, saltSizeInBytes);

            return result;
        } catch(InvalidKeySpecException exception) {
            throw new RuntimeException(exception);
        }
    }

    protected SecretKeyFactory createSecretKeyFactory() {
        try {
            SecretKeyFactory result = SecretKeyFactory.getInstance(hashAlgorithmName);
            return result;
        } catch(NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean checkHashedAndSaltedPassword(byte[] hashedAndSaltedCorrectPassword, String challengingPassword) {
        if(hashedAndSaltedCorrectPassword.length != arraySize) {
            throw new IllegalArgumentException("" + "Given array hashedAndSaltedCorrectPassword is not of length<"
                    + arraySize + "> but<" + hashedAndSaltedCorrectPassword.length + ">.");
        }

        byte[] extractedSalt = new byte[saltSizeInBytes];
        System.arraycopy(hashedAndSaltedCorrectPassword, hashSizeInBytes, extractedSalt, 0, saltSizeInBytes);
        byte[] hashedAndSaltedAttempt = hashWithSalt(challengingPassword, extractedSalt);
        boolean result = Arrays.equals(hashedAndSaltedAttempt, hashedAndSaltedCorrectPassword);

        return result;
    }
}
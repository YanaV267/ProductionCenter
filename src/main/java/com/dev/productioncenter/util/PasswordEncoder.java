package com.dev.productioncenter.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * @project Production Center
 * @author YanaV
 * The type Password encoder.
 */
public class PasswordEncoder {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ENCODING_TYPE = "SHA-1";
    private static final int SIGN_TYPE = 1;
    private static final int NUMERAL_SYSTEM = 16;

    private PasswordEncoder() {
    }

    /**
     * Encode optional.
     *
     * @param password the password
     * @return the optional
     */
    public static Optional<String> encode(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCODING_TYPE);
            messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
            byte[] bytesEncoded = messageDigest.digest();
            BigInteger bigInt = new BigInteger(SIGN_TYPE, bytesEncoded);
            return Optional.of(bigInt.toString(NUMERAL_SYSTEM));
        } catch (NoSuchAlgorithmException exception) {
            LOGGER.error("Error has occurred while encoding password (specified algorithm wasn't defined): " + exception);
            return Optional.empty();
        }
    }
}

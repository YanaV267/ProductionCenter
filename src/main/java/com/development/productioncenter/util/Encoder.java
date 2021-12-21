package com.development.productioncenter.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class Encoder {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Encoder INSTANCE = new Encoder();

    private Encoder() {
    }

    public static Encoder getInstance() {
        return INSTANCE;
    }

    public Optional<String> encode(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
            byte[] bytesEncoded = messageDigest.digest();
            BigInteger bigInt = new BigInteger(1, bytesEncoded);
            return Optional.of(bigInt.toString(16));
        } catch (NoSuchAlgorithmException exception) {
            LOGGER.error("Error has occurred while encoding password (specified algorithm wasn't defined): " + exception);
            return Optional.empty();
        }
    }
}

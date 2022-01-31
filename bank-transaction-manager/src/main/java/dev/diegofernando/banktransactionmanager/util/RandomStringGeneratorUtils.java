package dev.diegofernando.banktransactionmanager.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

public final class RandomStringGeneratorUtils {

    public static final String POSSIBLE_CHARS_RANDOM_PIX_KEY = "0123456789abcdefghijklmnopqrstuvwxyz-";
    public static final int RANDOM_PIX_KEY_LENGTH = 36;


    public static String getHashFromString(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-","");
    }

    public static String getRandomHashToPixKey() {

        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            return secureRandom.ints(RANDOM_PIX_KEY_LENGTH, 0, POSSIBLE_CHARS_RANDOM_PIX_KEY.length()).mapToObj(POSSIBLE_CHARS_RANDOM_PIX_KEY::charAt)
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

}

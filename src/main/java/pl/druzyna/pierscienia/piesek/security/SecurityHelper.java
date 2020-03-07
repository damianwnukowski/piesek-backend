package pl.druzyna.pierscienia.piesek.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class SecurityHelper {

    private static Logger log = LoggerFactory.getLogger(SecurityHelper.class);

    public static byte[] generateRandomSecret(int length) {
        Random random = new Random();
        byte[] result = new byte[length];
        random.nextBytes(result);
        log.info("Generated random secret");
        return result;
    }
}

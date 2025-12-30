package com.innowise.gym.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    private static final int WORK_FACTOR = 12;

    private PasswordHasher() {}

    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(WORK_FACTOR));
    }

    public static boolean check(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}

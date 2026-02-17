package com.idataai.common.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码工具类
 *
 * @author iDataAI
 */
public class PasswordUtil {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 加密密码
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public static String encode(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    /**
     * 验证密码
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }

    /**
     * 验证密码强度
     * 要求: 8-20位, 包含大小写字母和数字
     *
     * @param password 密码
     * @return 是否符合要求
     */
    public static boolean validatePasswordStrength(String password) {
        if (password == null || password.length() < 8 || password.length() > 20) {
            return false;
        }
        // 至少包含一个小写字母
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        // 至少包含一个大写字母
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        // 至少包含一个数字
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);

        return hasLower && hasUpper && hasDigit;
    }

    /**
     * 生成随机密码
     *
     * @param length 长度
     * @return 随机密码
     */
    public static String generateRandomPassword(int length) {
        if (length < 8) {
            length = 8;
        }
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }
}

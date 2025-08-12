package com.alura.forohub;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPasswordMatch {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String nuevaPassword = "123456";
        String hash = encoder.encode(nuevaPassword);

        System.out.println("Hash generado para 123456:");
        System.out.println(hash);
    }
}

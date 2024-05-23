package com.bugops.inicio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Esta clase es una configuración para el codificador BCryptPasswordEncoder.
 */
@Configuration
public class BCryptPasswordEncoderConfig {

    /**
     * Crea una instancia de BCryptPasswordEncoder.
     *
     * @return una instancia de BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

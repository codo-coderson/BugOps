package com.bugops.inicio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración para la seguridad de la aplicación.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * El servicio de detalles del usuario.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * El codificador BCryptPasswordEncoder.
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Configura el AuthenticationManagerBuilder para utilizar el UserDetailsService
     * y el BCryptPasswordEncoder.
     *
     * @param auth el AuthenticationManagerBuilder
     * @throws Exception si ocurre un error al configurar el
     *                   AuthenticationManagerBuilder
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * Configura la cadena de filtros de seguridad para las solicitudes HTTP.
     *
     * @param http el objeto HttpSecurity
     * @return la cadena de filtros de seguridad configurada
     * @throws Exception si ocurre un error al configurar la cadena de filtros de
     *                   seguridad
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/register", "/changePassword", "/styles.css").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/userAdmin").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers("/projectAdmin").hasAuthority("ROLE_GESTOR")
                        .requestMatchers("/archivedBugs").hasAuthority("ROLE_GESTOR")
                        .requestMatchers("/tables/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/", true)
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        return http.build();
    }
}

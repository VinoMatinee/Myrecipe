//package dev.mvc.tool;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig1 {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(authorizeRequests ->
//                authorizeRequests
//                    .requestMatchers("/account/login", "/login/**", "/error").permitAll()
//                    .anyRequest().authenticated()
//            )
//            .oauth2Login(oauth2Login ->
//                oauth2Login
//                    .loginPage("/account/login")
//                    .defaultSuccessUrl("/index")
//                    .failureUrl("/account/login?error=true")
//            );
//
//        return http.build();
//    }
//
//    @Bean(name = "toolPasswordEncoder")
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}

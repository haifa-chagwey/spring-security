package com.haifachagwey.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.haifachagwey.springsecurity.config.Role.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(csrf -> csrf.disable())
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/css/**", "/js/**", "/images/**").permitAll() // Public paths
//                        .requestMatchers("/api/**").hasRole(STUDENT.name())
//                        .requestMatchers(HttpMethod.POST, "management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                        .requestMatchers(HttpMethod.PUT, "management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                        .requestMatchers(HttpMethod.DELETE, "management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                        .requestMatchers("management/api/**").hasAnyRole(ADMIN.name(), ADMIN_TRAINEE.name())
/*
                No need to add get method here because it goes through matchers one by one
                .requestMatchers(HttpMethod.GET, "management/api/**").hasAnyRole(ADMIN.name(), ADMIN_TRAINEE.name())
*/
                        .anyRequest().authenticated()) // Everything else requires authentication)
                .httpBasic(Customizer.withDefaults());
        return http.build();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails anna = User.builder()
                .username("anna")
                .password(passwordEncoder.encode("password")) // {noop} means no password encoder
                .roles(STUDENT.name()) // ROLE_STUDENT
                .authorities(STUDENT.getRoleGrantedAuthorities())
                .build();

        UserDetails linda = User.builder()
                .username("linda")
                .password(passwordEncoder.encode("password")) // {noop} means no password encoder
                .roles(ADMIN.name()) // ROLE_ADMIN
                .authorities(ADMIN.getRoleGrantedAuthorities())
                .build();
        UserDetails tom = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("password")) // {noop} means no password encoder
                .roles(ADMIN_TRAINEE.name()) // ROLE_ADMIN_TRAINEE
                .authorities(ADMIN_TRAINEE.getRoleGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(anna, linda, tom);


    }


}

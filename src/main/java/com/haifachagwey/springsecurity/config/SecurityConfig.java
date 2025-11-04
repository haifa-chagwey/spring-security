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

import java.util.concurrent.TimeUnit;

import static com.haifachagwey.springsecurity.config.Role.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
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
                .formLogin( form -> form
                        .loginPage("/sign-in")
                        .loginProcessingUrl("/login") // Even if the default is /login, we should be explicit because we changed the default login page path
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("mySecretRememberMeKey") // A unique, persistent key for your application
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) // 14 days (default)
                        .rememberMeParameter("remember-me") // The name of the checkbox parameter
//                        If you rename the parameter in your login form, you must also update your Spring Security configuration to match it.
//                        Otherwise, Spring Security won’t recognize the "remember me" value.                )
                )
                .logout(logout -> logout
                                .logoutUrl("/logout")
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID","remember-me")
//                        That tells the browser: “Delete these cookies right now.”
                                .logoutSuccessUrl("/login")
                );
        return http.build();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

//    Fetch user details from database
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

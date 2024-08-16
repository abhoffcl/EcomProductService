package dev.Abhishek.EcomProductService.security;


import dev.Abhishek.EcomProductService.client.UserAuthClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    private UserAuthClient userAuthClient;

    public SpringSecurityConfig(UserAuthClient userAuthClient) {
        this.userAuthClient = userAuthClient;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).
                authorizeHttpRequests(authorize-> authorize
                         .requestMatchers(HttpMethod.GET, "/product/**", "/category/**").permitAll() // Allow public access to GET requests
                        .requestMatchers(HttpMethod.POST, "/product/**", "/category/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.PUT, "/product/**", "/category/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.DELETE, "/product/**", "/category/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.POST, "/order/**").hasAnyAuthority("ROLE_admin","ROLE_customer")
                        .anyRequest().authenticated()
                )
                .addFilterAfter(new SimpleTokenFilter(userAuthClient), BasicAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults());

        return http.build();


    }
}

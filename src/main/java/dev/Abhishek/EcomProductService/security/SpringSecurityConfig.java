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
    private ExceptionHandlingFilter exceptionHandlingFilter;
    private SimpleTokenFilter simpleTokenFilter;

    public SpringSecurityConfig(ExceptionHandlingFilter exceptionHandlingFilter, SimpleTokenFilter simpleTokenFilter) {
        this.exceptionHandlingFilter = exceptionHandlingFilter;
        this.simpleTokenFilter = simpleTokenFilter;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).
                authorizeHttpRequests(authorize-> authorize
                        .requestMatchers(HttpMethod.GET, "/actuator/**" ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/product/**", "/category/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/order/failed").permitAll()
                        .requestMatchers(HttpMethod.POST, "/product/**", "/category/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.PUT, "/product/**", "/category/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.DELETE, "/product/**", "/category/**").hasAuthority("ROLE_admin")
                        .requestMatchers(HttpMethod.POST, "/order/create").hasAnyAuthority("ROLE_admin","ROLE_customer")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(exceptionHandlingFilter , BasicAuthenticationFilter.class)
                .addFilterAfter(simpleTokenFilter, BasicAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults());

        return http.build();

    }
}

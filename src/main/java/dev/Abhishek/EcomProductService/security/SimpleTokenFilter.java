package dev.Abhishek.EcomProductService.security;

import dev.Abhishek.EcomProductService.client.UserAuthClient;
import dev.Abhishek.EcomProductService.exception.ClientException.UserServiceException;
import dev.Abhishek.EcomProductService.exception.UnauthorizedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class SimpleTokenFilter extends OncePerRequestFilter {
    private UserAuthClient userAuthServiceClient;

    public SimpleTokenFilter(UserAuthClient userAuthServiceClient) {
        this.userAuthServiceClient = userAuthServiceClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException , UserServiceException {

        // Skip the filter for the /order/failed endpoint when accessed from localhost
        if (request.getRequestURI().equals("/order/failed") && request.getRemoteAddr().equals("127.0.0.1")){
            chain.doFilter(request, response);
            return ;
        }


        String token = request.getHeader("Authorization");

            if (token == null || token.isEmpty()) {
                throw new UnauthorizedException("Missing or empty Authorization header");
            }
            JwtObject jwtObject = userAuthServiceClient.validateToken(token).get();
                if (jwtObject != null) {
                    CustomUserDetails userDetails = new CustomUserDetails(
                            jwtObject.getName(),
                            "",
                            jwtObject.getRoles().stream()
                                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                                    .collect(Collectors.toList()),
                            jwtObject.getUserId(),
                            jwtObject.getEmail(),
                            jwtObject.getPhoneNumber()
                    );

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        chain.doFilter(request, response);
        }
}

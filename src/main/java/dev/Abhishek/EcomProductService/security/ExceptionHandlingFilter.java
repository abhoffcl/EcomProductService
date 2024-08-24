package dev.Abhishek.EcomProductService.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Abhishek.EcomProductService.dto.ExceptionResponseDto;
import dev.Abhishek.EcomProductService.exception.ClientException.UserServiceException;
import dev.Abhishek.EcomProductService.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ExceptionHandlingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (UnauthorizedException ex) {
            handleException(response, ex, HttpStatus.UNAUTHORIZED);
        } catch (UserServiceException ex) {
            handleException(response, ex, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            handleException(response, ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void handleException(HttpServletResponse response, Exception ex, HttpStatus status) throws IOException {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                ex.getMessage(),
                status.value()
        );
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(convertObjectToJson(exceptionResponseDto));
    }

    private String convertObjectToJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}

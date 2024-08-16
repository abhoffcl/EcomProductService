package dev.Abhishek.EcomProductService.client;

import dev.Abhishek.EcomProductService.exception.ClientException.UserServiceException;
import dev.Abhishek.EcomProductService.security.JwtObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class UserAuthClient {
    private RestTemplateBuilder restTemplateBuilder;
    private String userAuthServiceBaseUrl;
    private String userAuthServiceValidateTokenPath;

    public UserAuthClient(RestTemplateBuilder restTemplateBuilder,
                          @Value("${userService.api.base.url}") String userAuthServiceBaseUrl,
                          @Value("${userService.api.validate.path}")String userAuthServiceValidateTokenPath) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.userAuthServiceBaseUrl = userAuthServiceBaseUrl;
        this.userAuthServiceValidateTokenPath = userAuthServiceValidateTokenPath;
    }

    public Optional<JwtObject> validateToken(String token) {
        String validateTokenUrl = userAuthServiceBaseUrl.concat(userAuthServiceValidateTokenPath).concat(token);
        RestTemplate restTemplate = restTemplateBuilder.build();
        try {
            JwtObject jwtObject = restTemplate.getForObject(validateTokenUrl, JwtObject.class);
            return Optional.ofNullable(jwtObject);
        } catch (ResourceAccessException e) {
            throw new UserServiceException("Failed to authenticate: Timeout or resource access issue.");
        } catch (RestClientException e) {
            throw new UserServiceException("Failed to authenticate: An error occurred during REST call.");
        }
    }


}

package dev.Abhishek.EcomProductService.security;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class JwtObject {
    private String name;
    private String email;
    private String phoneNumber;
    private UUID userId;
    private Instant createdAt;
    private Instant expiryAt;
    private List<RoleResponseDto> roles;
}

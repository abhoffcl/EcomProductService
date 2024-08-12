package dev.Abhishek.EcomProductService.security;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RoleResponseDto {
    private UUID id;
    private String roleName;
    private String description;

}
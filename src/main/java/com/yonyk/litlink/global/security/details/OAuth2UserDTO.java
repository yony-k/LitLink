package com.yonyk.litlink.global.security.details;

public record OAuth2UserDTO(
        String memberName,
        String email,
        String provider
) {
}

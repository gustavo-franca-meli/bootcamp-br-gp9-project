package com.mercadolibre.finalProject.util;

import com.mercadolibre.finalProject.model.enums.RoleType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CreateFakeLogin {

    public static String loginInvalid(String username, RoleType roleType) {
        return createMockToken(username, roleType.getDescription());
    }

    public static String loginValid(String username, RoleType roleType) {
        return createMockToken(username, roleType.getDescription());
    }

    static String createMockToken(String username, String roleTypeDescription) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(roleTypeDescription);
        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

}

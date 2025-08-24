package dio.spring.security.jwt.security;

import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTCreator {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ROLES_AUTHORITIES = "authorities";

    public static String create(String prefix, String key, JWTObject jwtObject) {
        String token = Jwts.builder().setSubject(jwtObject.getSubject()).setIssuedAt(jwtObject.getIssuedAt())
                .setExpiration(jwtObject.getExpiration())
                .claim(ROLES_AUTHORITIES, checkRoles(jwtObject.getRoles())).signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return prefix + " " + token;
    }

    public static JWTObject create(String token, String prefix, String key)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException {
        String cleanToken = token.replace(prefix, "").trim();
        var claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(cleanToken)
                .getBody();

        JWTObject jwtObject = new JWTObject();
        jwtObject.setSubject(claims.getSubject());
        jwtObject.setIssuedAt(claims.getIssuedAt());
        jwtObject.setExpiration(claims.getExpiration());
        Object roles = claims.get(ROLES_AUTHORITIES);
        if (roles instanceof List<?>) {
            jwtObject.setRoles(((List<?>) roles).stream().map(Object::toString).collect(Collectors.toList()));
        }
        return jwtObject;
    }

    private static Object checkRoles(List<String> roles) {
        return roles.stream().map(s -> "ROLE_".concat(s.replaceAll("ROLE_", ""))).collect(Collectors.toList());
    }
}

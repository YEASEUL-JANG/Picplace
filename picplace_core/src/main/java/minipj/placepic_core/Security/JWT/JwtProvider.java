package minipj.placepic_core.Security.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtProvider{
    private final CustomUserDetailsService customUserDetailsService;
    private final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    public static final String AUTH_HEADER = "authorization";
    public static final String AUTH_TOKEN_PREFIX = "Bearer ";

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in.ms}")
    private Long JWT_EXPIRATION_IN_MS;

    /**
     * @param auth
     * @return jwt 인증토큰 발행
     */
    public String generateToken(UserPrincipal auth){
        logger.info("[createToken] 토큰 생성 시작");
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        String token =  Jwts.builder()
                .setSubject(auth.getUsername())
                .claim("roles",authorities)
                .claim("userId",auth.getId())
                .setExpiration(new Date(System.currentTimeMillis()+JWT_EXPIRATION_IN_MS))
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
        logger.info("[createToken] 토큰 생성 완료");
        return token;
    }

    /**
     * @param request
     * @return Spring Security-Context에 담을 인증객체 반환
     */
    public Authentication getAuthentication(HttpServletRequest request){
        Claims claims = extractClaims(request);
        if(claims == null){
            return null;
        }
        String username = claims.getSubject();
        //UserDetails 객체로 재생성.
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if(username == null){
            return null;
        }//UsernamePasswordAuthenticationToken의 조상이 authentication 객체
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

    /**
     * @param request
     * @return 회원정보 담은 Claim 객체 추출
     */
    private Claims extractClaims(HttpServletRequest request) {
        logger.info("[extractClaims] 토큰기반 회원정보 추출");
        String bearerToken = request.getHeader(AUTH_HEADER);
        String token = null;
        if(StringUtils.hasLength(bearerToken)&& bearerToken.startsWith(AUTH_TOKEN_PREFIX)){
            token= bearerToken.substring(7);
        }
        if(token == null){
            return null;
        }
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        logger.info("[extractClaims] 토큰기반 회원정보 추출완료 , sub: {}",claims.getSubject());
        return claims;
    }

    /**
     * @param request
     * @return 토큰 유효기간 검증
     */
    public boolean isTokenValid(HttpServletRequest request){
        Claims claims = extractClaims(request);
        if(claims==null){
            return false;
        }
        if(claims.getExpiration().before(new Date())){
            return false;
        }
        return true;
    }
}

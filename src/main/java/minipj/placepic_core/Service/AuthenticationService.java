package minipj.placepic_core.Service;

import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Entity.User;
import minipj.placepic_core.Repository.UserRepository;
import minipj.placepic_core.Security.JWT.JwtProvider;
import minipj.placepic_core.Security.JWT.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User signInAndReturnJWT(User loginuser) throws RuntimeException{
        logger.info("[getSignInResult] 로그인 아이디로 회원정보 요청");
        Optional<User> user = userRepository.findByUsername(loginuser.getUsername());
        logger.info("[getSignInResult] 패스워드 비교");
        if(!passwordEncoder.matches(loginuser.getPassword(),user.get().getPassword())){
            logger.info("[getSignInResult] 패스워드 불일치");
            throw new RuntimeException();
        }
        logger.info("[getSignInResult] 패스워드 일치");
        logger.info("[getSignInResult] 인증정보 생성");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginuser.getUsername(),loginuser.getPassword())
        );
        logger.info("[getSignInResult] 로그인 인증객체 생성");
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        logger.info("[getSignInResult] jwt 토큰 생성");
        String jwt = jwtProvider.generateToken(userPrincipal);
        logger.info("[getSignInResult] user객체에 토큰 저장하여 반환");
        User signInUser = userPrincipal.getUser();
        signInUser.setToken(jwt);
        return signInUser;
    }
}

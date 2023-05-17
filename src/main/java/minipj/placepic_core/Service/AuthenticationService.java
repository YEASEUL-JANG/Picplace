package minipj.placepic_core.Service;

import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Entity.User;
import minipj.placepic_core.Security.JWT.JwtProvider;
import minipj.placepic_core.Security.JWT.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public User signInAndReturnJWT(User signInRequest){
        //로그인 아이디와 비밀번호를 토대로 authenticationManager의 인증객체를 생성
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),signInRequest.getPassword())
        );
        //로그인시에만 사용되는 userdetails 객체로 변환
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        //userdetails 객체를 통해 jwt 토큰 발급
        String jwt = jwtProvider.generateToken(userPrincipal);
        //로그인 유저 객체에 토큰과 함께 정보 저장하여 반환.
        User signInUser = userPrincipal.getUser();
        signInUser.setToken(jwt);
        return signInUser;
    }
}

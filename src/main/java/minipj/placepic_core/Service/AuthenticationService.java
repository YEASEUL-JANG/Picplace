package minipj.placepic_core.Service;

import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Security.JWT.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
}

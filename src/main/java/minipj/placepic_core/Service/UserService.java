package minipj.placepic_core.Service;

import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Entity.Role;
import minipj.placepic_core.Entity.User;
import minipj.placepic_core.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    public User saveUser(User user){
        logger.info("[saveUser] 비밀번호 암호화 진행");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("[saveUser] role 저장");
        if(user.getUsername().equals("admin")){
            user.setRole(Role.ADMIN);
        }else {
            user.setRole(Role.USER);
        }
        logger.info("[saveUser] joinDate 설정");
        user.setJoinDate(LocalDateTime.now());
        logger.info("[saveUser] joinUser 저장진행");
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username){
        logger.info("[findByUsername] 아이디 검색중");
        return userRepository.findByUsername(username);
    }
}

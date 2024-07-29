package minipj.placepic_core.Service;

import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Entity.Address;
import minipj.placepic_core.Entity.Form.JoinForm;
import minipj.placepic_core.Entity.Role;
import minipj.placepic_core.Entity.User;
import minipj.placepic_core.Entity.UserDto;
import minipj.placepic_core.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    @Transactional
    public User saveUser(JoinForm joinuser){
        logger.info("[saveUser] 유저객체 생성");
        User user = new User();
        logger.info("[saveUser] joinform 정보 저장");
        String safeUsername = HtmlUtils.htmlEscape(joinuser.getUsername());
        String safePassword = HtmlUtils.htmlEscape(joinuser.getPassword());

        Address address = new Address(HtmlUtils.htmlEscape(joinuser.getAddress()),
                HtmlUtils.htmlEscape(joinuser.getDetailAddress()),
                HtmlUtils.htmlEscape(joinuser.getZipcode()));
        user.setAddress(address);
        user.setUsername(safeUsername);
        logger.info("[saveUser] 비밀번호 암호화 진행");
        user.setPassword(passwordEncoder.encode(safePassword));
        logger.info("[saveUser] role 저장");
        if(joinuser.getUsername().equals("admin")){
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

    @Transactional
    public void deleteUser(Long userId) {
        logger.info("[deleteUser] 유저 삭제 진행");
        userRepository.deleteById(userId);
    }

    public Optional<User> findUser(Long userId) {
        logger.info("[findUser] 유저 조회 진행");
        return userRepository.findById(userId);
    }

    @Transactional
    public void editUser(UserDto.UserInfo userDto, Long userId) {
        logger.info("[findUser] 유저 정보수정 진행");
        Optional<User> finduser = userRepository.findById(userId);
        User user = finduser.get();
        String safeUsername = HtmlUtils.htmlEscape(userDto.getUsername());
        user.setUsername(safeUsername);
        Address newaddress = new Address(HtmlUtils.htmlEscape(userDto.getAddress()),
                HtmlUtils.htmlEscape(userDto.getDetailAddress()),
                HtmlUtils.htmlEscape(userDto.getZipcode()) );
        user.setAddress(newaddress);
    }
}

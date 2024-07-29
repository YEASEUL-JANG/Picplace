package minipj.placepic_core.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Entity.Form.JoinForm;
import minipj.placepic_core.Entity.User;
import minipj.placepic_core.Service.AuthenticationService;
import minipj.placepic_core.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(value = "로그인/회원가입 테스트")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    //회원가입
    @ApiOperation(value="회원가입",notes = "@RequestBody를 활용한 회원가입 Post Method")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@ApiParam(value = "User object") @RequestBody JoinForm user){
        logger.info("[sign-up] 회원가입 진행, joinuser : {}",user.toString());
        logger.info("[sign-up] 아이디 중복확인");
        if(userService.findByUsername(user.getUsername()).isPresent()){
            logger.info("[sign-up] 아이디 중복에러");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("[sign-up] 신규회원등록 가능");
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }
    @ApiOperation(value="로그인요청",notes = "@RequestBody를 활용한 로그인요청 Post Method")
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@ApiParam(value = "UserId") @RequestBody User user){
        logger.info("[sign-in] 로그인 진행, loginuser : {}",user.toString());
        try {
            User loginuser = authenticationService.signInAndReturnJWT(user);
            logger.info("[sign-in] 로그인 완료");
            return new ResponseEntity<>(loginuser, HttpStatus.OK);
        }catch (RuntimeException e){
            logger.info("[sign-in] 로그인 실패");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
